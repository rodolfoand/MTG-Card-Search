package com.example.mtgcardsearch.ui.deck;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.mtgcardsearch.R;
import com.example.mtgcardsearch.databinding.FragmentDeckBinding;
import com.example.mtgcardsearch.model.CardSearchResult;
import com.example.mtgcardsearch.model.Deck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class DeckFragment extends Fragment {

    private FragmentDeckBinding binding;
    private DeckViewModel deckViewModel;
    private Deck deck;
    private String parm_query;
    private CardSearchResult dataList;
    private List<String> mainResultList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        DeckViewModelFactory factory = new DeckViewModelFactory(getActivity().getApplication());
        deckViewModel = new ViewModelProvider(this, factory).get(DeckViewModel.class);

        binding = FragmentDeckBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.setSpinner();

        if (getArguments() != null) {
            if (getArguments().containsKey("id")) {
                this.getDeck();
            }
        }

        binding.btDeckCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deck = new Deck(binding.etDeckName.getText().toString()
                        , getResources().getStringArray(R.array.array_formats)[binding.spDeckFormat.getSelectedItemPosition()]
                        , binding.etDeckNotes.getText().toString()
                        , binding.etDeckMaindeck.getText().toString()
                        , binding.etDeckSideboard.getText().toString()
                        , binding.etDeckMaybeboard.getText().toString()
                        , new Date()
                        , new Date());

                deckViewModel.insert(deck);

                Toast.makeText(getContext(), deck.getName() + " created", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });

        binding.btDeckSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s_maindeck = binding.etDeckMaindeck.getText().toString().replaceAll("(?m)(^\\s*$\\r?\\n)+", "");

                Log.d("DeckFragment", "s_maindeck: " + s_maindeck);
                binding.etDeckMaindeck.setText(s_maindeck);

                deck.setName(binding.etDeckName.getText().toString());
                deck.setFormat(getResources().getStringArray(R.array.array_formats)[binding.spDeckFormat.getSelectedItemPosition()]);
                deck.setNotes(binding.etDeckNotes.getText().toString());
                deck.setMaindeck(binding.etDeckMaindeck.getText().toString());
                deck.setSideboard(binding.etDeckSideboard.getText().toString());
                deck.setMaybeboard(binding.etDeckMaybeboard.getText().toString());

                deckViewModel.update(deck);

                List<String> mainList = new ArrayList<String>(Arrays.asList(s_maindeck.split("\n")));
                Log.d("DeckFragment", "mainList: " + mainList);

                parm_query = mainList.stream()
                        .map(e -> "!\"" + e.toString()
                                .substring((Character.isDigit(e.charAt(0))) ? e.indexOf(" ") : 0, e.indexOf("(") > 0 ? e.indexOf("(") : e.length())
                                .trim() + "\" or ")
                        .reduce("", String::concat);
                Log.d("DeckFragment", "parm_query: " + parm_query);

                List<String> mainCardList = mainList.stream()
                        .map(e -> e.toString()
                                .substring((Character.isDigit(e.charAt(0))) ? e.indexOf(" ") : 0, e.indexOf("(") > 0 ? e.indexOf("(") : e.length())
                                .trim())
                        .collect(Collectors.toList());
                Log.d("DeckFragment", "mainCardList: " + mainCardList.size() + ": " + mainCardList);

                deckViewModel.getCards("false"
                        , "true"
                        , "name"
                        , "1"
                        , "cards"
                        , "auto"
                        , parm_query).observe(getViewLifecycleOwner(), new Observer<CardSearchResult>() {
                    @Override
                    public void onChanged(CardSearchResult cardSearchResult) {

                        mainResultList = new ArrayList<>();

                        if (cardSearchResult.getObject().equals("list")) {
                            dataList = cardSearchResult;
                            Log.d("DeckFragment", "datalist: " + dataList.getData().toString());

                            mainResultList =
                                    dataList.getData()
                                            .stream()
                                            .map(e -> TextUtils.isEmpty(e.getPrinted_name())
                                                    ? e.getName().toUpperCase()
                                                    : e.getPrinted_name().toUpperCase())
                                            .collect(Collectors.toList());
                        }

                        Log.d("DeckFragment", "mainResultList: " + mainResultList);

                        if (!mainResultList.containsAll(mainCardList)) {

                            List<Integer> mainErrorList =
                                    mainCardList.stream()
                                            .filter(e -> mainResultList.indexOf(e.toUpperCase()) < 0)
                                            .map(e -> mainCardList.indexOf(e))
                                            .collect(Collectors.toList());
                            Log.d("DeckFragment", "mainErrorList: " + mainErrorList);

                            AtomicInteger index = new AtomicInteger();

                            String mainLines =
                                    mainCardList.stream()
                                            .map(e -> (mainErrorList.indexOf(index.getAndIncrement()) < 0)
                                                    ? index.get() + "\n"
                                                    : "-\n")
                                            .reduce("", String::concat);
                            Log.d("DeckFragment", "mainLines: " + mainLines);

                            binding.tvDeckMaindeckLine.setText(mainLines);

                            String mainStringError = mainErrorList
                                    .stream()
                                    .map(e -> (e + 1) + ", ")
                                    .reduce("", String::concat);

                            if (mainErrorList.size() > 0) {
                                binding.tvDeckMaindeck.setError("Linha(s) " + mainStringError);
                                binding.etDeckMaindeck.setError("Linha(s) " + mainStringError);
                            }
                        }
                    }
                });

                Toast.makeText(getContext(), deck.getName() + " saved", Toast.LENGTH_SHORT).show();
//                            getActivity().onBackPressed();
//                            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
//                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        binding.mbDeckDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), R.string.delete, Toast.LENGTH_SHORT).show();

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle(R.string.delete);
                alert.setMessage(R.string.confirmation_delete);
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "Not deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), deck.getName() + " deleted", Toast.LENGTH_SHORT).show();
                        deckViewModel.delete(deck);
                        getActivity().onBackPressed();
                    }
                });
                alert.show();
            }
        });

        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void getDeck(){
        deckViewModel.getDeck(getArguments().getString("id")).observe(getViewLifecycleOwner(), new Observer<Deck>() {
            @Override
            public void onChanged(Deck d) {

                deck = d;

                binding.btDeckSave.setVisibility(View.VISIBLE);
                binding.mbDeckDelete.setVisibility(View.VISIBLE);
                binding.btDeckCreate.setVisibility(View.GONE);

                if (!TextUtils.isEmpty(deck.getName()))
                    binding.etDeckName.setText(deck.getName());
                List<String> format_list = Arrays.asList(getResources().getStringArray(R.array.array_formats));
                if (!TextUtils.isEmpty(deck.getFormat()))
                    binding.spDeckFormat.setSelection(format_list.indexOf(deck.getFormat()));
                if (!TextUtils.isEmpty(deck.getNotes()))
                    binding.etDeckNotes.setText(deck.getNotes());
                if (!TextUtils.isEmpty(deck.getMaindeck()))
                    binding.etDeckMaindeck.setText(deck.getMaindeck());
                if (!TextUtils.isEmpty(deck.getSideboard()))
                    binding.etDeckSideboard.setText(deck.getSideboard());
                if (!TextUtils.isEmpty(deck.getMaybeboard()))
                    binding.etDeckMaybeboard.setText(deck.getMaybeboard());
            }
        });
    }

    private void setSpinner(){

        ArrayAdapter<CharSequence> adapter_sp_deck_format_value = ArrayAdapter.createFromResource(getContext(),
                R.array.array_formats, android.R.layout.simple_spinner_item);
        adapter_sp_deck_format_value.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spDeckFormat.setAdapter(adapter_sp_deck_format_value);
    }
    private static boolean isInteger(String str){
        return str != null && str.matches("[0-9.]+");
    }
}