package com.example.mtgcardsearch.ui.decklist;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.mtgcardsearch.R;
import com.example.mtgcardsearch.databinding.FragmentDecklistBinding;

public class DecklistFragment extends Fragment {

    private FragmentDecklistBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DecklistViewModel decklistViewModel =
                new ViewModelProvider(this).get(DecklistViewModel.class);

        binding = FragmentDecklistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textMydecks;
        decklistViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

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