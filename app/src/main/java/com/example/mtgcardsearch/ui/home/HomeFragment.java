package com.example.mtgcardsearch.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.mtgcardsearch.R;
import com.example.mtgcardsearch.databinding.FragmentHomeBinding;
import com.example.mtgcardsearch.model.Card;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        HomeViewModelFactory factory = new HomeViewModelFactory(getActivity().getApplication());
        homeViewModel =
                new ViewModelProvider(this, factory).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.homeCardAdvsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_advsearch);
            }
        });

        homeViewModel.getCardIDs().observe(getViewLifecycleOwner(), new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {
                String s_wishlist;
                if (cards.size() > 0)
                    s_wishlist = "You have " + cards.size() + " card(s) on your wishlist.";
                else
                    s_wishlist = "You don't have cards in your wishlist.";
                binding.tvHomeSubWishlist.setText(s_wishlist);
            }
        });

        binding.homeCardWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_wishlist);
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