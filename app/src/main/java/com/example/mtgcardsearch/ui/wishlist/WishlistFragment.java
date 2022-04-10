package com.example.mtgcardsearch.ui.wishlist;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mtgcardsearch.databinding.FragmentWishlistBinding;
import com.example.mtgcardsearch.model.Card;
import com.example.mtgcardsearch.model.OnSetWishListener;

import java.util.ArrayList;
import java.util.List;

public class WishlistFragment extends Fragment {

    private FragmentWishlistBinding binding;
    private WishlistViewModel wishlistViewModel;

    private Context mCtx;
    private WishlistAdapter adapterWishlist;
    MutableLiveData<List<Card>> cardList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        WishlistViewModelFactory factory = new WishlistViewModelFactory(getActivity().getApplication());
        wishlistViewModel = new ViewModelProvider(this, factory).get(WishlistViewModel.class);

        binding = FragmentWishlistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mCtx = container.getContext();
        cardList = new MutableLiveData<>();
        cardList.setValue(new ArrayList<>());


        binding.rvWishlist.setHasFixedSize(true);
        binding.rvWishlist.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterWishlist = new WishlistAdapter(mCtx);
        binding.rvWishlist.setAdapter(adapterWishlist);

        wishlistViewModel.getAllCardIDs().observe(getViewLifecycleOwner(), new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {
                Log.d("MagicCard", cards.size() + "");

                for (Card c : cards) {
                    wishlistViewModel.getCard(c.getId()).observe(getViewLifecycleOwner(), new Observer<Card>() {
                        @Override
                        public void onChanged(Card card) {
                            card.setWhish(true);
                            List<Card> newCardList = cardList.getValue();
                            if (newCardList.indexOf(card) < 0)
                                newCardList.add(card);
                            cardList.setValue(newCardList);
                        }
                    });
                }
            }
        });

        cardList.observe(getViewLifecycleOwner(), new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {

                adapterWishlist.setCardList(cards);
                adapterWishlist.notifyDataSetChanged();

//                adapterWishlist.notifyDataSetChanged();

                adapterWishlist.setOnSetWishListener(new OnSetWishListener() {
                    @Override
                    public void onSetWish(Card card) {
                        wishlistViewModel.deleteCard(card);
                        Toast.makeText(mCtx, "Removed from wishlist.", Toast.LENGTH_SHORT).show();
                    }
                });
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