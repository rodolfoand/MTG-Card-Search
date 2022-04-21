package com.example.mtgcardsearch.ui.wishlist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mtgcardsearch.R;
import com.example.mtgcardsearch.databinding.FragmentWishlistBinding;
import com.example.mtgcardsearch.ui.cardlist.CardlistFragment;

public class WishlistFragment extends Fragment {

    private FragmentWishlistBinding binding;
    private CardlistFragment cardlistFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWishlistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        cardlistFragment= new CardlistFragment();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_wishlist, cardlistFragment)
//                .addToBackStack(null)
                .commit();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        getActivity().getSupportFragmentManager().beginTransaction().remove(cardlistFragment).commit();
    }
}