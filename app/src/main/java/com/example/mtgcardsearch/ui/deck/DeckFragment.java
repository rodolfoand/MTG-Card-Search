package com.example.mtgcardsearch.ui.deck;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.mtgcardsearch.R;
import com.example.mtgcardsearch.databinding.FragmentDeckBinding;
import com.example.mtgcardsearch.model.Deck;

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

        deckViewModel.getDeck(getArguments().getString("id")).observe(getViewLifecycleOwner(), new Observer<Deck>() {
            @Override
            public void onChanged(Deck deck) {
                if (!deck.getName().isEmpty())
                    binding.etDeckName.setText(deck.getName());
            }
        });

        binding.btDeckCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Deck deck = new Deck();
                deck.setName(binding.etDeckName.getText().toString());
//                deck.setFormat(getResources().getStringArray(R.array.array_formats)[binding.spDeckFormat.getSelectedItemPosition()]);
//                deck.setNotes(binding.etDeckNotes.getText().toString());
//                deck.setMaindeck(binding.etDeckMaindeck.getText().toString());
//                deck.setSideboard(binding.etDeckSideboard.getText().toString());
//                deck.setMaybeboard(binding.etDeckMaybeboard.getText().toString());

                deckViewModel.insert(deck);

//                Toast.makeText(getContext(), deck.toString(), Toast.LENGTH_SHORT).show();
                Log.d("DeckFragment", deck.toString());
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