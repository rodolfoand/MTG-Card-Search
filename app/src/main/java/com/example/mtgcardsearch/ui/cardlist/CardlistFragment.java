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
import android.widget.Button;
import android.widget.TextView;

import com.example.mtgcardsearch.databinding.FragmentCardlistBinding;
import com.example.mtgcardsearch.model.ListSearchResult;


public class CardlistFragment extends Fragment {

    private FragmentCardlistBinding binding;

    private RecyclerView rv_cardlist;
    private CardlistAdapter adapter_cardlist;
    private Context mCtx;
    private TextView tx_search_result;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CardlistViewModel cardlistViewModel =
                new ViewModelProvider(this).get(CardlistViewModel.class);

        binding = FragmentCardlistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mCtx = container.getContext();

        tx_search_result = binding.txSearchResult;

        rv_cardlist = binding.rvCardlist;
        rv_cardlist.setHasFixedSize(true);
        rv_cardlist.setLayoutManager(new LinearLayoutManager(getContext()));

        String query = getArguments().getString("query");

        cardlistViewModel.getListSearchResult(query).observe(getViewLifecycleOwner(), new Observer<ListSearchResult>() {
            @Override
            public void onChanged(ListSearchResult listSearchResult) {
                tx_search_result.setText("Result: " + listSearchResult.getData().size() +" of "+ listSearchResult.getTotal_cards());

                adapter_cardlist = new CardlistAdapter(mCtx, listSearchResult);
                rv_cardlist.setAdapter(adapter_cardlist);
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