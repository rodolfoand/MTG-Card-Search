package com.example.mtgcardsearch.ui.cardlist;

import android.content.Context;
import android.net.Uri;
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
import android.widget.Toast;

import com.example.mtgcardsearch.R;
import com.example.mtgcardsearch.databinding.FragmentCardlistBinding;
import com.example.mtgcardsearch.model.ListSearchResult;
import com.example.mtgcardsearch.model.OnBottomReachedListener;



public class CardlistFragment extends Fragment {

    private FragmentCardlistBinding binding;

    private RecyclerView rv_cardlist;
    private CardlistAdapter adapter_cardlist;
    private Context mCtx;
    private ListSearchResult dataList;
    private TextView tx_search_result;
    private Button bt_search_more;
    private int count_cards;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CardlistViewModel cardlistViewModel =
                new ViewModelProvider(this).get(CardlistViewModel.class);

        binding = FragmentCardlistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mCtx = container.getContext();
        tx_search_result = binding.txSearchResult;
        bt_search_more = binding.btSearchMore;

        rv_cardlist = binding.rvCardlist;
        rv_cardlist.setHasFixedSize(true);
        rv_cardlist.setLayoutManager(new LinearLayoutManager(getContext()));

        String query = getArguments().getString("query");

        cardlistViewModel.getListSearchResult(query).observe(getViewLifecycleOwner(), new Observer<ListSearchResult>() {
            @Override
            public void onChanged(ListSearchResult listSearchResult) {
                dataList = listSearchResult;
                count_cards += listSearchResult.getData().size();

                tx_search_result.setText(getStringResult(listSearchResult.getData().size()));

                adapter_cardlist = new CardlistAdapter(mCtx, dataList);
                rv_cardlist.setAdapter(adapter_cardlist);

                adapter_cardlist.setOnBottomReachedListener(new OnBottomReachedListener() {
                    @Override
                    public void onBottomReached(int position) {
                        Toast.makeText(mCtx, "onBottomReached", Toast.LENGTH_SHORT).show();
                        if (dataList.isHas_more())
                            bt_search_more.setVisibility(View.VISIBLE);

                    }
                });
            }
        });

        bt_search_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri_next_page = Uri.parse(dataList.getNext_page());

                cardlistViewModel.getNextListSearchResult(
                        uri_next_page.getQueryParameter("include_extras")
                        , uri_next_page.getQueryParameter("include_multilingual")
                        , uri_next_page.getQueryParameter("order")
                        , uri_next_page.getQueryParameter("page")
                        , uri_next_page.getQueryParameter("unique")
                        , uri_next_page.getQueryParameter("q"))
                        .observe(getViewLifecycleOwner(), new Observer<ListSearchResult>() {
                    @Override
                    public void onChanged(ListSearchResult listSearchResult) {
                        dataList = listSearchResult;
                        bt_search_more.setVisibility(View.GONE);

                        adapter_cardlist.setListSearchResult(dataList);
                        adapter_cardlist.notifyDataSetChanged();
                        rv_cardlist.scrollToPosition(0);

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

    public String getStringResult(int qtd){
        return getString(R.string.result) + ": "
                + (count_cards - ((qtd < 175) ? --qtd : 174))
                + " - "
                + count_cards
                + " " + getString(R.string.of) + " "
                + dataList.getTotal_cards();
    }
}