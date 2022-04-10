package com.example.mtgcardsearch.ui.cardlist;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
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
import com.example.mtgcardsearch.model.Card;
import com.example.mtgcardsearch.model.CardSearchResult;
import com.example.mtgcardsearch.model.OnBottomReachedListener;
import com.example.mtgcardsearch.model.OnSetWishListener;


import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


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
    private String parm_fuzzy;

    private final int LAYOUT_LINEAR = 0;
    private final int LAYOUT_GRID = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        cardlistViewModel =
//                new ViewModelProvider(this).get(CardlistViewModel.class);

        CardlistViewModelFactory factory = new CardlistViewModelFactory(getActivity().getApplication());
        cardlistViewModel = new ViewModelProvider(this, factory).get(CardlistViewModel.class);

        binding = FragmentCardlistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mCtx = container.getContext();

        parm_query = getArguments().getString("query");
        parm_unique = getArguments().getString("unique");
        parm_dir = getArguments().getString("dir");
        parm_fuzzy = getArguments().getString("fuzzy");

        this.setSpinner();
        this.setParms();
        this.setListAdapter();

        if (parm_query != null)
            this.setDataList();

        if (parm_fuzzy != null)
            this.setDataCard();

        this.setLoading(true);
        this.setLayoutRecycler(cardlistViewModel.getPrefLayout());

        cardlistViewModel.getAllCardIDs().observe(getViewLifecycleOwner(), new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {
                List<String> wishList = cards.stream().map(card -> card.getId()).collect(Collectors.toList());

                adapter_cardlist.setWishList(wishList);
//                adapter_cardlist.notifyDataSetChanged();
            }
        });

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

            if (binding.imCardlistCol.getVisibility() == View.VISIBLE) {
                binding.imCardlistCol.setVisibility(View.GONE);
                binding.imCardlistRow.setVisibility(View.VISIBLE);
            }
        }
        if (layout == this.LAYOUT_LINEAR) {
            binding.rvCardlist.setLayoutManager(new LinearLayoutManager(getContext()));

            if (binding.imCardlistRow.getVisibility() == View.VISIBLE) {
                binding.imCardlistCol.setVisibility(View.VISIBLE);
                binding.imCardlistRow.setVisibility(View.GONE);
            }
        }
        if (adapter_cardlist != null) binding.rvCardlist.setAdapter(adapter_cardlist);
    }

    private void setListAdapter(){

        adapter_cardlist = new CardlistAdapter(mCtx);
        binding.rvCardlist.setAdapter(adapter_cardlist);

        adapter_cardlist.setOnBottomReachedListener(new OnBottomReachedListener() {
            @Override
            public void onBottomReached(int position) {
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
        if (parm_unique != null)
            binding.spinnerUnique.setSelection(Arrays.asList(getResources().getStringArray(R.array.array_unique)).indexOf(parm_unique));

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
        if (parm_order != null)
            binding.spinnerOrder.setSelection(Arrays.asList(getResources().getStringArray(R.array.array_order)).indexOf(parm_order));

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
        if (parm_dir != null)
            binding.spinnerDir.setSelection(Arrays.asList(getResources().getStringArray(R.array.array_dir)).indexOf(parm_dir));

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
        if (parm_order == null) parm_order = "name";
        if (parm_unique == null) parm_unique = "cards";
        if (parm_dir == null) parm_dir = "auto";
        if (parm_page == null) parm_page = "1";
        if (parm_include_multilingual == null) parm_include_multilingual = "true";
        if (parm_include_extras == null) parm_include_extras = "false";
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
                            if (dataList.getData().size() == 1){
                                toCardNavigation(dataList.getData().get(0).getId());
                            } else {
                                binding.btSearchMore.setVisibility(View.GONE);

                                count_cards = cardSearchResult.getData().size()
                                        + (Integer.parseInt(parm_page) - 1)
                                        * 175;

                                adapter_cardlist.setCardSearchResult(dataList);
                                adapter_cardlist.notifyDataSetChanged();

                                adapter_cardlist.setOnSetWishListener(new OnSetWishListener() {
                                    @Override
                                    public void onSetWish(Card card) {
                                        if (card.isWhish()){
                                            cardlistViewModel.deleteWish(card);
                                            Toast.makeText(mCtx, "Removed from wishlist.", Toast.LENGTH_SHORT).show();

                                        } else {
                                            cardlistViewModel.insertWish(card);
                                            Toast.makeText(mCtx, R.string.addedtowishlist, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                                binding.rvCardlist.scrollToPosition(0);

                                binding.txSearchResult.setText(getStringResult(cardSearchResult.getData().size()));

                                setLoading(false);
                            }
                        }
                        if (cardSearchResult.getObject().equals("error")) {
                                setNotFound();
                        }
                    }
                });
    }

    private void setDataCard() {
        cardlistViewModel.getCardbyName(parm_fuzzy).observe(getViewLifecycleOwner(), new Observer<Card>() {
            @Override
            public void onChanged(Card card) {
                toCardNavigation(card.getId());
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
            binding.imCardlistCol.setVisibility(View.GONE);
            binding.imCardlistRow.setVisibility(View.GONE);
        } else {
            binding.pbCardlist.setVisibility(View.GONE);
            binding.spinnerDir.setVisibility(View.VISIBLE);
            binding.spinnerOrder.setVisibility(View.VISIBLE);
            binding.spinnerUnique.setVisibility(View.VISIBLE);
            binding.rvCardlist.setVisibility(View.VISIBLE);
            if (cardlistViewModel.getPrefLayout() == LAYOUT_GRID){
                binding.imCardlistRow.setVisibility(View.VISIBLE);
            } else {
                binding.imCardlistCol.setVisibility(View.VISIBLE);
            }
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

    private void toCardNavigation(String id){
        Navigation.findNavController(getView()).popBackStack();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        Navigation.findNavController(getView()).navigate(R.id.nav_card, bundle);
    }

}