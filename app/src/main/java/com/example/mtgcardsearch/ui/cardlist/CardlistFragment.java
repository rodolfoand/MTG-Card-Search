package com.example.mtgcardsearch.ui.cardlist;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.mtgcardsearch.R;
import com.example.mtgcardsearch.databinding.FragmentCardlistBinding;
import com.example.mtgcardsearch.model.CardSearchResult;
import com.example.mtgcardsearch.model.OnBottomReachedListener;


import java.util.Locale;



public class CardlistFragment extends Fragment {

    private FragmentCardlistBinding binding;
    private CardlistViewModel cardlistViewModel;

    private Context mCtx;
    private CardSearchResult dataList;
    private CardlistAdapter adapter_cardlist;
    private int count_cards;

    private String parm_unique;
    private String parm_order;
    private String parm_dir;
    private String parm_query;
    private String parm_page;
    private String parm_include_multilingual;
    private String parm_include_extras;

    private final int LAYOUT_LINEAR = 0;
    private final int LAYOUT_GRID = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cardlistViewModel =
                new ViewModelProvider(this).get(CardlistViewModel.class);

        binding = FragmentCardlistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mCtx = container.getContext();

        parm_query = getArguments().getString("query");

        this.setSpinner();
        this.setParms();
        this.setLayoutRecycler(cardlistViewModel.getPrefLayout());
        this.setListAdapter();
        this.setDataList();
        this.setLoading(true);

        binding.btSearchMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri_next_page = Uri.parse(dataList.getNext_page());
                parm_include_extras = uri_next_page.getQueryParameter("include_extras");
                parm_include_multilingual = uri_next_page.getQueryParameter("include_multilingual");
                parm_order = uri_next_page.getQueryParameter("order");
                parm_page = uri_next_page.getQueryParameter("page");
                parm_unique = uri_next_page.getQueryParameter("unique");
                parm_dir = uri_next_page.getQueryParameter("dir");
                parm_query = uri_next_page.getQueryParameter("q");

                setLoading(true);
                setDataList();

            }
        });

        binding.imCardlistCol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDataSourceLayout();
            }
        });

        binding.imCardlistRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDataSourceLayout();
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
        return getString(R.string.result).toUpperCase(Locale.ROOT) + ": "
                + (count_cards - ((qtd < 175) ? --qtd : 174))
                + " - "
                + count_cards
                + " " + getString(R.string.of) + " "
                + dataList.getTotal_cards();
    }

    private void setLayoutRecycler(int layout){
        binding.rvCardlist.setHasFixedSize(true);

        if (layout == this.LAYOUT_GRID) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
            binding.rvCardlist.setLayoutManager(gridLayoutManager);

            binding.imCardlistCol.setVisibility(View.GONE);
            binding.imCardlistRow.setVisibility(View.VISIBLE);
        }
        if (layout == this.LAYOUT_LINEAR) {
            binding.rvCardlist.setLayoutManager(new LinearLayoutManager(getContext()));

            binding.imCardlistCol.setVisibility(View.VISIBLE);
            binding.imCardlistRow.setVisibility(View.GONE);
        }
        if (adapter_cardlist != null) binding.rvCardlist.setAdapter(adapter_cardlist);
    }

    private void setListAdapter(){

        adapter_cardlist = new CardlistAdapter(mCtx);
        binding.rvCardlist.setAdapter(adapter_cardlist);

        adapter_cardlist.setOnBottomReachedListener(new OnBottomReachedListener() {
            @Override
            public void onBottomReached(int position) {
                Toast.makeText(mCtx, "onBottomReached", Toast.LENGTH_SHORT).show();
                if (dataList.isHas_more())
                    binding.btSearchMore.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setSpinner(){
        ArrayAdapter<CharSequence> adapter_sp_unique = ArrayAdapter.createFromResource(mCtx,
                R.array.array_unique, android.R.layout.simple_spinner_item);
        adapter_sp_unique.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerUnique.setAdapter(adapter_sp_unique);

        binding.spinnerUnique.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String new_unique = getResources().getStringArray(R.array.array_unique)[i];

                if (!new_unique.equals(parm_unique)) {
                    parm_unique = getResources().getStringArray(R.array.array_unique)[i];
                    parm_page = "1";
                    setDataList();
                    setLoading(true);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapter_sp_order = ArrayAdapter.createFromResource(mCtx,
                R.array.array_order, android.R.layout.simple_spinner_item);
        adapter_sp_order.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerOrder.setAdapter(adapter_sp_order);

        binding.spinnerOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String new_order = getResources().getStringArray(R.array.array_order)[i];

                if (!new_order.equals(parm_order)) {
                    parm_order = getResources().getStringArray(R.array.array_order)[i];
                    parm_page = "1";
                    setDataList();
                    setLoading(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapter_sp_dir = ArrayAdapter.createFromResource(mCtx,
                R.array.array_dir, android.R.layout.simple_spinner_item);
        adapter_sp_dir.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerDir.setAdapter(adapter_sp_dir);

        binding.spinnerDir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String new_dir = getResources().getStringArray(R.array.array_dir)[i];

                if (!new_dir.equals(parm_dir)) {
                    parm_dir = getResources().getStringArray(R.array.array_dir)[i];
                    parm_page = "1";
                    setDataList();
                    setLoading(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setParms(){
        parm_order = "name";
        parm_unique = "cards";
        parm_dir = "auto";
        parm_page = "1";
        parm_include_multilingual = "false";
        parm_include_extras = "false";
    }

    private void setDataList() {
        cardlistViewModel.getCards(
                parm_include_extras
                , parm_include_multilingual
                , parm_order
                , parm_page
                , parm_unique
                , parm_dir
                , parm_query)
                .observe(getViewLifecycleOwner(), new Observer<CardSearchResult>() {
                    @Override
                    public void onChanged(CardSearchResult cardSearchResult) {

                        if (cardSearchResult.getObject().equals("list")) {
                            dataList = cardSearchResult;
                            binding.btSearchMore.setVisibility(View.GONE);

                            count_cards = cardSearchResult.getData().size()
                                    + (Integer.parseInt(parm_page) - 1)
                                    * 175;

                            adapter_cardlist.setCardSearchResult(dataList);
                            adapter_cardlist.notifyDataSetChanged();
                            binding.rvCardlist.scrollToPosition(0);

                            binding.txSearchResult.setText(getStringResult(cardSearchResult.getData().size()));

                            setLoading(false);
                        }
                        if (cardSearchResult.getObject().equals("error")) {
                            setNotFound();
                        }
                    }
                });
    }

    private void setLoading(boolean loading){
        if (loading){
            binding.pbCardlist.setVisibility(View.VISIBLE);
            binding.spinnerDir.setVisibility(View.GONE);
            binding.spinnerOrder.setVisibility(View.GONE);
            binding.spinnerUnique.setVisibility(View.GONE);
            binding.rvCardlist.setVisibility(View.GONE);
        } else {
            binding.pbCardlist.setVisibility(View.GONE);
            binding.spinnerDir.setVisibility(View.VISIBLE);
            binding.spinnerOrder.setVisibility(View.VISIBLE);
            binding.spinnerUnique.setVisibility(View.VISIBLE);
            binding.rvCardlist.setVisibility(View.VISIBLE);
        }
        binding.txSearchNotFound.setVisibility(View.GONE);
    }

    private void setNotFound(){
        binding.pbCardlist.setVisibility(View.GONE);
        binding.txSearchNotFound.setVisibility(View.VISIBLE);
    }

    private void setDataSourceLayout(){
        if (cardlistViewModel.getPrefLayout() == LAYOUT_GRID){
            cardlistViewModel.setPrefLayout(LAYOUT_LINEAR);
            setLayoutRecycler(LAYOUT_LINEAR);
        } else {
            cardlistViewModel.setPrefLayout(LAYOUT_GRID);
            setLayoutRecycler(LAYOUT_GRID);
        }
    }

}