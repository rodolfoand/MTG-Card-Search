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
import com.example.mtgcardsearch.model.Deck;

import java.util.Arrays;
import java.util.List;

public class DeckFragment extends Fragment {

    private FragmentDeckBinding binding;
    private DeckViewModel deckViewModel;


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

                            Toast.makeText(getContext(), deck.getName() + " saved", Toast.LENGTH_SHORT).show();
                            getActivity().onBackPressed();
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
}