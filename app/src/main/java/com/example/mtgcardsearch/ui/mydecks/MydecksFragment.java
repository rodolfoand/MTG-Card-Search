package com.example.mtgcardsearch.ui.mydecks;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mtgcardsearch.databinding.FragmentMydecksBinding;

public class MydecksFragment extends Fragment {

    private FragmentMydecksBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MydecksViewModel mydecksViewModel =
                new ViewModelProvider(this).get(MydecksViewModel.class);

        binding = FragmentMydecksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textMydecks;
        mydecksViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}