package com.example.mtgcardsearch.ui.cardslist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mtgcardsearch.R;
import com.example.mtgcardsearch.databinding.FragmentCardslistBinding;
import com.example.mtgcardsearch.databinding.FragmentWishlistBinding;
import com.example.mtgcardsearch.model.Card;
import com.example.mtgcardsearch.ui.wishlist.WishlistViewModel;

import java.util.List;

public class CardslistFragment extends Fragment {

    private FragmentCardslistBinding binding;

    private RecyclerView rv_cardslist;
    private CardslistAdapter adapter_cards;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CardslistViewModel cardslistViewModel =
                new ViewModelProvider(this).get(CardslistViewModel.class);

        binding = FragmentCardslistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        rv_cardslist = binding.rvCardslist;
        rv_cardslist.setHasFixedSize(true);
        rv_cardslist.setLayoutManager(new LinearLayoutManager(getContext()));

        String query = getArguments().getString("query");

        cardslistViewModel.getCards(query).observe(getViewLifecycleOwner(), new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {
                adapter_cards = new CardslistAdapter(getContext(), cards);
                rv_cardslist.setAdapter(adapter_cards);
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