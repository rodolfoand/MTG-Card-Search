package com.example.mtgcardsearch.ui.deck;

import android.app.Activity;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.mtgcardsearch.R;
import com.example.mtgcardsearch.databinding.FragmentDeckBinding;
import com.example.mtgcardsearch.model.CardSearchResult;
import com.example.mtgcardsearch.model.Deck;

import java.text.Normalizer;
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
    private List<String> mainList;
    private List<String> sideList;
    private List<String> maybeList;
    private List<String> cardList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        DeckViewModelFactory factory = new DeckViewModelFactory(getActivity().getApplication());
        deckViewModel = new ViewModelProvider(this, factory).get(DeckViewModel.class);

        binding = FragmentDeckBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.setSpinner();

        if (getArguments() != null && getArguments().containsKey("id"))
            this.getDeck();

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

                String mainString = binding.etDeckMaindeck.getText().toString().replaceAll("(?m)(^\\s*$\\r?\\n)+", "");
                binding.etDeckMaindeck.setText(mainString);
                Log.d("DeckFragment", "mainString: " + mainString);

                String sideString = binding.etDeckSideboard.getText().toString().replaceAll("(?m)(^\\s*$\\r?\\n)+", "");
                binding.etDeckSideboard.setText(sideString);
                Log.d("DeckFragment", "sideString: " + sideString);

                String maybeString = binding.etDeckMaybeboard.getText().toString().replaceAll("(?m)(^\\s*$\\r?\\n)+", "");
                binding.etDeckMaybeboard.setText(maybeString);
                Log.d("DeckFragment", "maybeString: " + maybeString);

                String cardString = binding.etDeckMaindeck.getText().toString()
                        .concat(binding.etDeckSideboard.getText().toString())
                        .concat(binding.etDeckMaybeboard.getText().toString())
                        .replaceAll("(?m)(^\\s*$\\r?\\n)+", "");
                Log.d("DeckFragment", "maybeString: " + maybeString);

                deck.setName(binding.etDeckName.getText().toString());
                deck.setFormat(getResources().getStringArray(R.array.array_formats)[binding.spDeckFormat.getSelectedItemPosition()]);
                deck.setNotes(binding.etDeckNotes.getText().toString());
                deck.setMaindeck(binding.etDeckMaindeck.getText().toString());
                deck.setSideboard(binding.etDeckSideboard.getText().toString());
                deck.setMaybeboard(binding.etDeckMaybeboard.getText().toString());
                deck.setUpdated_on(new Date());

                mainList = new ArrayList<>();

                if (!TextUtils.isEmpty(mainString))
                    mainList = new ArrayList<String>(Arrays.asList(removeAccents(mainString).split("\n")));
                Log.d("DeckFragment", "mainList: " + mainList + ": " + mainList.size());

                sideList = new ArrayList<>();

                if (!TextUtils.isEmpty(sideString))
                    sideList = new ArrayList<String>(Arrays.asList(removeAccents(sideString).split("\n")));
                Log.d("DeckFragment", "sideList: " + sideList + ": " + sideList.size());

                maybeList = new ArrayList<>();

                if (!TextUtils.isEmpty(maybeString))
                    maybeList = new ArrayList<String>(Arrays.asList(removeAccents(maybeString).split("\n")));
                Log.d("DeckFragment", "maybeString: " + maybeList + ": " + maybeList.size());

                cardList = new ArrayList<>();

                if (!TextUtils.isEmpty(mainString))
                    cardList = new ArrayList<String>(Arrays.asList(removeAccents(cardString).split("\n")));
                Log.d("DeckFragment", "cardList: " + cardList + ": " + cardList.size());

                if (cardList.size() > 0) {
                    String cardQuery = cardList.stream()
                            .map(e -> "!\"" + e.toString()
                                    .substring((Character.isDigit(e.charAt(0))) ? e.indexOf(" ") : 0, e.indexOf("(") > 0 ? e.indexOf("(") : e.length())
                                    .trim() + "\" or ")
                            .reduce("", String::concat);
                    Log.d("DeckFragment", "cardQuery: " + cardQuery);

                    deckViewModel.getCards("false"
                            , "true"
                            , "name"
                            , "1"
                            , "cards"
                            , "auto"
                            , cardQuery).observe(getViewLifecycleOwner(), new Observer<CardSearchResult>() {
                        @Override
                        public void onChanged(CardSearchResult cardSearchResult) {

                            List<String> cardResultList = new ArrayList<>();

                            if (cardSearchResult.getObject().equals("list")) {
                                Log.d("DeckFragment", "cardSearchResult.getData(): " + cardSearchResult.getData().toString());

                                cardResultList =
                                        cardSearchResult.getData()
                                                .stream()
                                                .map(e -> TextUtils.isEmpty(e.getPrinted_name())
                                                        ? removeAccents(e.getName().toUpperCase())
                                                        : removeAccents(e.getPrinted_name().toUpperCase()))
                                                .collect(Collectors.toList());
                            }

                            Log.d("DeckFragment", "cardResultList: " + cardResultList);

                            List<String> mainCardList = mainList.stream()
                                    .map(e -> e.toString()
                                            .substring((Character.isDigit(e.charAt(0))) ? e.indexOf(" ") : 0, e.indexOf("(") > 0 ? e.indexOf("(") : e.length())
                                            .trim())
                                    .collect(Collectors.toList());
                            Log.d("DeckFragment", "mainCardList: " + mainCardList.size() + ": " + mainCardList);

                            List<Integer> mainErrorList = getErrorList(mainCardList, cardResultList);
                            Log.d("DeckFragment", "mainErrorList: " + mainErrorList);

                            deck.setMain_lines(getLines(mainCardList, mainErrorList));
                            deck.setMain_error(getErrorStr(mainErrorList));

//                            deckViewModel.update(deck);

                            binding.tvDeckMaindeckLine.setText(deck.getMain_lines());


                            if (mainErrorList.size() > 0) {
                                binding.tvDeckMaindeck.setError(deck.getMain_error());
                                binding.etDeckMaindeck.setError(deck.getMain_error());
                            } else {
                                binding.tvDeckMaindeck.setError(null);
                                binding.etDeckMaindeck.setError(null);
                            }

                            List<String> sideCardList = sideList.stream()
                                    .map(e -> e.toString()
                                            .substring((Character.isDigit(e.charAt(0))) ? e.indexOf(" ") : 0, e.indexOf("(") > 0 ? e.indexOf("(") : e.length())
                                            .trim())
                                    .collect(Collectors.toList());
                            Log.d("DeckFragment", "sideCardList: " + sideCardList.size() + ": " + sideCardList);

                            List<Integer> sideErrorList = getErrorList(sideCardList, cardResultList);
                            Log.d("DeckFragment", "sideErrorList: " + sideErrorList);

                            deck.setSide_lines(getLines(sideCardList, sideErrorList));
                            deck.setSide_error(getErrorStr(sideErrorList));

//                            deckViewModel.update(deck);

                            binding.tvDeckSideLine.setText(deck.getSide_lines());


                            if (sideErrorList.size() > 0) {
                                binding.tvDeckSideboard.setError(deck.getSide_error());
                                binding.etDeckSideboard.setError(deck.getSide_error());
                            } else {
                                binding.tvDeckSideboard.setError(null);
                                binding.etDeckSideboard.setError(null);
                            }


                            List<String> maybeCardList = maybeList.stream()
                                    .map(e -> e.toString()
                                            .substring((Character.isDigit(e.charAt(0))) ? e.indexOf(" ") : 0, e.indexOf("(") > 0 ? e.indexOf("(") : e.length())
                                            .trim())
                                    .collect(Collectors.toList());
                            Log.d("DeckFragment", "maybeCardList: " + maybeCardList.size() + ": " + maybeCardList);

                            List<Integer> maybeErrorList = getErrorList(maybeCardList, cardResultList);
                            Log.d("DeckFragment", "maybeErrorList: " + maybeErrorList);

                            deck.setMaybe_lines(getLines(maybeCardList, maybeErrorList));
                            deck.setMaybe_error(getErrorStr(maybeErrorList));


                            binding.tvDeckMaybeLine.setText(deck.getMaybe_lines());


                            if (maybeErrorList.size() > 0) {
                                binding.tvDeckMaybeboard.setError(deck.getMaybe_error());
                                binding.etDeckMaybeboard.setError(deck.getMaybe_error());
                            } else {
                                binding.tvDeckMaybeboard.setError(null);
                                binding.etDeckMaybeboard.setError(null);
                            }

                            deckViewModel.update(deck);

                        }
                    });
                }





                Toast.makeText(getContext(), deck.getName() + " saved", Toast.LENGTH_SHORT).show();
//                            getActivity().onBackPressed();
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

    private void setSpinner(){

        ArrayAdapter<CharSequence> adapter_sp_deck_format_value = ArrayAdapter.createFromResource(getContext(),
                R.array.array_formats, android.R.layout.simple_spinner_item);
        adapter_sp_deck_format_value.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spDeckFormat.setAdapter(adapter_sp_deck_format_value);
    }
    private static boolean isInteger(String str){
        return str != null && str.matches("[0-9.]+");
    }

    public static String removeAccents(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    private List<Integer> getErrorList(List<String> cardList, List<String> resultList){

        List<Integer> errorList = new ArrayList<>();
        for (int i = 0; i < cardList.size(); i++){
            if (resultList.indexOf(cardList.get(i).toUpperCase()) < 0) errorList.add(i);
        }
        return errorList;
    }

    private String getLines(List<String> cardList, List<Integer> errorList){

        AtomicInteger index = new AtomicInteger();

        String lines =
                cardList.stream()
                        .map(e -> (errorList.indexOf(index.getAndIncrement()) < 0)
                                ? index.get() + "\n"
                                : "-\n")
                        .reduce("", String::concat);
        return lines;
    }

    private String getErrorStr(List<Integer> errorList){

        if (errorList.size() > 0) {
            String errorStr = errorList
                    .stream()
                    .map(e -> (e + 1) + ", ")
                    .reduce("", String::concat);

            return "Line(s): " + errorStr.substring(0, (errorStr.lastIndexOf(",") > 0) ? errorStr.lastIndexOf(",") : errorStr.length());
        }
        else return "";
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
                if (!TextUtils.isEmpty(deck.getMain_lines()))
                    binding.tvDeckMaindeckLine.setText(deck.getMain_lines());
                if (!TextUtils.isEmpty(deck.getMain_error())) {
                    binding.tvDeckMaindeck.setError(deck.getMain_error());
                    binding.etDeckMaindeck.setError(deck.getMain_error());
                }
                if (!TextUtils.isEmpty(deck.getSide_lines()))
                    binding.tvDeckSideLine.setText(deck.getSide_lines());
                if (!TextUtils.isEmpty(deck.getSide_error())) {
                    binding.tvDeckSideboard.setError(deck.getSide_error());
                    binding.etDeckSideboard.setError(deck.getSide_error());
                }
                if (!TextUtils.isEmpty(deck.getMaybe_lines()))
                    binding.tvDeckMaybeLine.setText(deck.getMaybe_lines());
                if (!TextUtils.isEmpty(deck.getMaybe_error())) {
                    binding.tvDeckMaybeboard.setError(deck.getMaybe_error());
                    binding.etDeckMaybeboard.setError(deck.getMaybe_error());
                }
            }
        });
    }
}