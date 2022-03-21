package com.example.mtgcardsearch.ui.cardlist;

import android.content.Context;
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

import com.example.mtgcardsearch.databinding.FragmentCardlistBinding;
import com.example.mtgcardsearch.model.Card;

import java.util.List;

public class CardlistFragment extends Fragment {

    private FragmentCardlistBinding binding;

    private RecyclerView rv_cardlist;
    private CardlistAdapter adapter_cards;
    private Context mCtx;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CardlistViewModel cardlistViewModel =
                new ViewModelProvider(this).get(CardlistViewModel.class);

        binding = FragmentCardlistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mCtx = container.getContext();

        rv_cardlist = binding.rvCardlist;
        rv_cardlist.setHasFixedSize(true);
        rv_cardlist.setLayoutManager(new LinearLayoutManager(getContext()));

        String query = getArguments().getString("query");

        cardlistViewModel.getCards(query).observe(getViewLifecycleOwner(), new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {
                adapter_cards = new CardlistAdapter(mCtx, cards);
                rv_cardlist.setAdapter(adapter_cards);
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