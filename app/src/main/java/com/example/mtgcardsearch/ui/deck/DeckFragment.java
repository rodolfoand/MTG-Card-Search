package com.example.mtgcardsearch.ui.deck;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import java.util.stream.Collectors;

public class DeckFragment extends Fragment {

    private FragmentDeckBinding binding;
    private DeckViewModel deckViewModel;
    private String parm_query;
    private CardSearchResult dataList;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        DeckViewModelFactory factory = new DeckViewModelFactory(getActivity().getApplication());
        deckViewModel = new ViewModelProvider(this, factory).get(DeckViewModel.class);

        binding = FragmentDeckBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.setSpinner();

        try {
            deckViewModel.getDeck(getArguments().getString("id")).observe(getViewLifecycleOwner(), new Observer<Deck>() {
                @Override
                public void onChanged(Deck deck) {

                    binding.btDeckSave.setVisibility(View.VISIBLE);
                    binding.mbDeckDelete.setVisibility(View.VISIBLE);
                    binding.btDeckCreate.setVisibility(View.GONE);

                    if (!TextUtils.isEmpty(deck.getName()))
                        binding.etDeckName.setText(deck.getName());
                    List<String> format_list= Arrays.asList(getResources().getStringArray(R.array.array_formats));
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

                    binding.btDeckSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            deck.setName(binding.etDeckName.getText().toString());
                            deck.setFormat(getResources().getStringArray(R.array.array_formats)[binding.spDeckFormat.getSelectedItemPosition()]);
                            deck.setNotes(binding.etDeckNotes.getText().toString());
                            deck.setMaindeck(binding.etDeckMaindeck.getText().toString());
                            deck.setSideboard(binding.etDeckSideboard.getText().toString());
                            deck.setMaybeboard(binding.etDeckMaybeboard.getText().toString());

                            deckViewModel.update(deck);



                            parm_query = "name:\"colheita do altar\" or name:\"Busca pelo Senhor da Tumba\"";

                            Log.d("query_card", "main: " + binding.etDeckMaindeck.getText());

                            String s_maindeck = binding.etDeckMaindeck.getText().toString();

                            List<String> myList = new ArrayList<String>(Arrays.asList(s_maindeck.split("\n")));
                            Log.d("query_card", "myList: " + myList.size());

                            String s = myList.stream()
                                    .map(e -> "name:\"" + e.toString()
                                            .substring(e.indexOf(" "), e.indexOf("(") > 0 ? e.indexOf("(") : e.length())
                                            .trim() + "\" or ")
                                    .reduce("", String::concat);
                            Log.d("query_card", "s: " + s);


                            String s2 = myList.stream()
                                    .map(e -> "!\"" + e.toString()
                                            .substring(e.indexOf(" "), e.indexOf("(") > 0 ? e.indexOf("(") : e.length())
                                            .trim() + "\" or ")
                                    .reduce("", String::concat);
                            Log.d("query_card", "s2: " + s2);


                            List<String> listCards = myList.stream()
                                    .map(e -> e.toString()
                                            .substring(e.indexOf(" "), e.indexOf("(") > 0 ? e.indexOf("(") : e.length())
                                            .trim())
                                    .collect(Collectors.toList());
                            Log.d("query_card", "listCards: " + listCards.size() + ": " + listCards);

                            try {
                                List<Integer> listCount = myList.stream()
                                        .map(e -> Integer.parseInt(e.toString()
                                                .substring(0, e.indexOf(" "))))
                                        .collect(Collectors.toList());
                                Log.d("query_card", "listCount: " + listCount.size() + ": " + listCount);
                            } catch (Exception e){
                                e.printStackTrace();
                            }


                            try {
                                List<Integer> listCount2 = myList.stream()
                                        .map(e -> isInteger(e.toString()
                                                .substring(0, e.indexOf(" "))) ? Integer.parseInt(e.toString()
                                                .substring(0, e.indexOf(" "))) : 0)
                                        .collect(Collectors.toList());
                                Log.d("query_card", "listCount2: " + listCount2.size() + ": " + listCount2);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            parm_query = s2;

                            deckViewModel.getCards("false"
                                    , "true"
                                    , "name"
                                    , "1"
                                    , "cards"
                                    , "auto"
                                    , parm_query).observe(getViewLifecycleOwner(), new Observer<CardSearchResult>() {
                                @Override
                                public void onChanged(CardSearchResult cardSearchResult) {
                                    Toast.makeText(getContext(), cardSearchResult.toString(), Toast.LENGTH_SHORT).show();
                                    Log.d("query_card", "obj: " + cardSearchResult.getObject());
                                    if (cardSearchResult.getObject().equals("list")) {
                                        dataList = cardSearchResult;
                                        Log.d("query_card", "datalist: " + dataList.getData().toString());
                                        Log.d("query_card", "datalist size: " + dataList.getData().size());

                                        List<String> listResultNames =
                                                dataList.getData()
                                                        .stream()
                                                        .map(e -> TextUtils.isEmpty(e.getPrinted_name())
                                                                ? e.getName().toUpperCase()
                                                                : e.getPrinted_name().toUpperCase())
                                                        .collect(Collectors.toList());
                                        Log.d("query_card", "listResultNames: " + listResultNames);
                                        if (!listResultNames.containsAll(listCards)){
                                            Log.d("query_card", "listCards: " + listCards);
                                            List<Integer> listError =
                                                    listCards.stream()
                                                            .filter(e -> listResultNames.indexOf(e.toUpperCase()) < 0)
                                                            .map(e -> listCards.indexOf(e))
                                                            .collect(Collectors.toList());
                                            Log.d("query_card", "listError: " + listError);

                                            String lines = listCards.stream()
                                                    .map(e -> listCards.indexOf(e) + 1 + "\n")
                                                    .reduce("", String::concat);
                                            binding.tvDeckMaindeckLine.setText(lines);
                                            Log.d("query_card", "lines: " + lines);

                                            String s_error = listError
                                                    .stream()
                                                    .map(e -> (e + 1) + ", ")
                                                    .reduce("", String::concat);

                                            if (listError.size() > 0){
                                                binding.tvDeckMaindeck.setError("Linha(s) " + s_error);
                                                binding.etDeckMaindeck.setError("Linha(s) " + s_error);
                                            }

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
                }
            });
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        binding.btDeckCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Deck deck = new Deck();
                deck.setName(binding.etDeckName.getText().toString());
                deck.setFormat(getResources().getStringArray(R.array.array_formats)[binding.spDeckFormat.getSelectedItemPosition()]);
                deck.setNotes(binding.etDeckNotes.getText().toString());
                deck.setMaindeck(binding.etDeckMaindeck.getText().toString());
                deck.setSideboard(binding.etDeckSideboard.getText().toString());
                deck.setMaybeboard(binding.etDeckMaybeboard.getText().toString());
                deck.setCreated_on(new Date());

                deckViewModel.insert(deck);

                Toast.makeText(getContext(), deck.getName() + " created", Toast.LENGTH_SHORT).show();
                Log.d("DeckFragment", deck.toString());
                getActivity().onBackPressed();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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