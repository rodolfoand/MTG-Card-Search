package com.example.mtgcardsearch.ui.deck;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mtgcardsearch.databinding.FragmentDeckBinding;

public class DeckFragment extends Fragment {

    private FragmentDeckBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDeckBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }
}