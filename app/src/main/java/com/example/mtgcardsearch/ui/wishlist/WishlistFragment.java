package com.example.mtgcardsearch.ui.wishlist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mtgcardsearch.R;
import com.example.mtgcardsearch.databinding.FragmentWishlistBinding;
import com.example.mtgcardsearch.ui.cardlist.CardlistFragment;

public class WishlistFragment extends Fragment {

    private FragmentWishlistBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWishlistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        CardlistFragment nextFrag= new CardlistFragment();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_wishlist, nextFrag)
                .addToBackStack(null)
                .commit();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}