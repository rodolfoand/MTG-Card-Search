package com.example.mtgcardsearch.ui.decklist;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mtgcardsearch.R;
import com.example.mtgcardsearch.databinding.FragmentDecklistBinding;
import com.example.mtgcardsearch.model.Deck;

import java.util.List;

public class DecklistFragment extends Fragment {

    private FragmentDecklistBinding binding;
    private DecklistViewModel decklistViewModel;

    private DecklistAdapter adapter_decklist;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDecklistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        DecklistViewModelFactory factory = new DecklistViewModelFactory(getActivity().getApplication());
        decklistViewModel = new ViewModelProvider(this, factory).get(DecklistViewModel.class);

        adapter_decklist = new DecklistAdapter(getContext());
        binding.rvDecklist.setAdapter(adapter_decklist);
        binding.rvDecklist.setLayoutManager(new LinearLayoutManager(getContext()));

        Log.d("DecklistFragment", "onCreateView");
        decklistViewModel.getAllDecks().observe(getViewLifecycleOwner(), new Observer<List<Deck>>() {
            @Override
            public void onChanged(List<Deck> decks) {
                adapter_decklist.setDeckList(decks);
                adapter_decklist.notifyDataSetChanged();
                Log.d("DecklistFragment", decks.size() + "");
            }
        });

        binding.btDecklistNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_deck);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}