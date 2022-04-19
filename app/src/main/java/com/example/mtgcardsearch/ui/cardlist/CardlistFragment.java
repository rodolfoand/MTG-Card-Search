package com.example.mtgcardsearch.ui.cardlist;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.ArraySet;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mtgcardsearch.R;
import com.example.mtgcardsearch.databinding.FragmentCardlistBinding;
import com.example.mtgcardsearch.model.Card;
import com.example.mtgcardsearch.model.CardFace;
import com.example.mtgcardsearch.model.CardSearchResult;
import com.example.mtgcardsearch.model.CardComparator;
import com.example.mtgcardsearch.model.CardWithCardfaces;
import com.example.mtgcardsearch.model.OnActiveActionModeListener;
import com.example.mtgcardsearch.model.OnBottomReachedListener;
import com.example.mtgcardsearch.model.OnSetWishListener;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;


public class CardlistFragment extends Fragment {

    private FragmentCardlistBinding binding;
    private CardlistViewModel cardlistViewModel;

    private Context mCtx;
    private CardSearchResult dataList;
    private CardlistAdapter adapter_cardlist;
    private int count_cards;
    private ActionMode.Callback mCallback;
    private ActionMode mActionMode;

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

        binding = FragmentCardlistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        CardlistViewModelFactory factory = new CardlistViewModelFactory(getActivity().getApplication());
        cardlistViewModel = new ViewModelProvider(this, factory).get(CardlistViewModel.class);

        mCtx = container.getContext();

        this.setParms();
        this.setSpinner();
        this.setListAdapter();

        if (parm_query != null)
            this.setDataList();
        else if (parm_fuzzy != null)
            this.setDataCard();
        else
            this.setDataWish();

        this.setLoading(true);
        this.setLayoutRecycler(cardlistViewModel.getPrefLayout());

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

        adapter_cardlist.setOnBottomReachedListener(new OnBottomReachedListener() {
            @Override
            public void onBottomReached(int position) {
                if (dataList != null && dataList.isHas_more())
                    binding.btSearchMore.setVisibility(View.VISIBLE);
            }
        });

        adapter_cardlist.setOnSetWishListener(new OnSetWishListener() {
            @Override
            public void onSetWish(Card card) {
                if (card.isWhish()){
                    cardlistViewModel.deleteWish(card);
                    adapter_cardlist.notifyItemRemoved(adapter_cardlist.cardList.indexOf(card));
                    adapter_cardlist.cardList.remove(card);

                    Toast.makeText(mCtx, R.string.removedfromwishlist, Toast.LENGTH_SHORT).show();
                } else {
                    cardlistViewModel.insertWish(card);
                    Toast.makeText(mCtx, R.string.addedtowishlist, Toast.LENGTH_SHORT).show();
                }

                count_cards = adapter_cardlist.cardList.size();
                String count = getString(R.string.countlabel) + ": " + count_cards;
                binding.txSearchResult.setText(count);
            }
        });

        mCallback = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater inflater = actionMode.getMenuInflater();
                inflater.inflate( R.menu.actionmode, menu );
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                boolean ret = false;
//                if(menuItem.getItemId() == R.id.actionmode_cancel)
//                {
//                    actionMode.finish();
//                    ret = true;
//                }
                return ret;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                actionMode = null;
                Set<Integer> setlist = new ArraySet<>();
                setlist.addAll(adapter_cardlist.selectedItemPositionsSet);
                adapter_cardlist.selectedItemPositionsSet.clear();

                for (Integer pos : setlist) {
                    adapter_cardlist.notifyItemChanged(pos);
                }
            }
        };



        adapter_cardlist.setOnActiveActionMode(new OnActiveActionModeListener() {
            @Override
            public void onActiveActionMode(boolean active) {
                if (active){
                    mActionMode = getActivity().startActionMode(mCallback);
                    adapter_cardlist.selectedSetSize.observe(getViewLifecycleOwner(), new Observer<Integer>() {
                        @Override
                        public void onChanged(Integer integer) {
                            mActionMode.setTitle(integer.toString());
                        }
                    });
                } else {
                    mActionMode.finish();
                }
            }
        });





        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setParms(){

        if (getArguments() != null) {
            parm_query = getArguments().getString("query");
            parm_unique = getArguments().getString("unique");
            parm_dir = getArguments().getString("dir");
            parm_fuzzy = getArguments().getString("fuzzy");
        }

        if (parm_order == null) parm_order = "name";
        if (parm_unique == null) parm_unique = "cards";
        if (parm_dir == null) parm_dir = "auto";
        if (parm_page == null) parm_page = "1";
        if (parm_include_multilingual == null) parm_include_multilingual = "true";
        if (parm_include_extras == null) parm_include_extras = "false";
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

        ArrayAdapter<CharSequence> adapter_sp_wish_order = ArrayAdapter.createFromResource(mCtx,
                R.array.array_wish_order, android.R.layout.simple_spinner_item);
        adapter_sp_wish_order.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spWishOrder.setAdapter(adapter_sp_wish_order);

        binding.spWishOrder.setSelection(Arrays.asList(getResources()
                .getStringArray(R.array.array_wish_order))
                .indexOf(cardlistViewModel.getPrefWishlistOrder()));

        binding.spWishOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String new_order = getResources().getStringArray(R.array.array_wish_order)[i];

                List<Card> newList = adapter_cardlist.cardList;
                if (newList.size() > 0) {
                    cardlistViewModel.setPrefWishlistOrder(new_order);
                    Collections.sort(newList, new CardComparator(new_order));
                    adapter_cardlist.setCardList(newList);
                    adapter_cardlist.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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

                                adapter_cardlist.setCardList(dataList.getData());
                                adapter_cardlist.notifyDataSetChanged();

                                adapter_cardlist.setOnSetWishListener(new OnSetWishListener() {
                                    @Override
                                    public void onSetWish(Card card) {

                                        setWish(card);

                                    }
                                });

                                binding.rvCardlist.scrollToPosition(0);

                                binding.txSearchResult.setText(getStringResult(cardSearchResult.getData().size()));

                                setLoading(false);
                                binding.llCardlistSearchSpinner.setVisibility(View.VISIBLE);
                            }
                        }
                        if (cardSearchResult.getObject().equals("error")) {
                            setNotFound();
                        }
                    }
                });

        cardlistViewModel.getAllCardIDs().observe(getViewLifecycleOwner(), new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {
                List<String> wishList = cards.stream().map(card -> card.getId()).collect(Collectors.toList());

                adapter_cardlist.setWishList(wishList);
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

    private void setDataWish(){

        cardlistViewModel.getAllCardWithCardfaces().observe(getViewLifecycleOwner(), new Observer<List<CardWithCardfaces>>() {
            @Override
            public void onChanged(List<CardWithCardfaces> cardWithCardfaces) {

                List<Card> newList = new ArrayList<>();
                for (CardWithCardfaces cardwith : cardWithCardfaces) {
                    Card card = cardwith.card;
                    card.setCard_faces((ArrayList<CardFace>) cardwith.cardfaces);
                    newList.add(card);
                }

                Collections.sort(newList, new CardComparator(cardlistViewModel.getPrefWishlistOrder()));
                adapter_cardlist.setCardList(newList);
                adapter_cardlist.setWishList(newList.stream().map(card -> card.getId()).collect(Collectors.toList()));
                adapter_cardlist.notifyDataSetChanged();

                count_cards = newList.size();
                String count = getString(R.string.countlabel) + ": " + count_cards;
                binding.txSearchResult.setText(count);

                setLoading(false);
                binding.llCardlistWishSpinner.setVisibility(View.VISIBLE);
            }
        });

    }

    private void setLoading(boolean loading){
        if (loading){
            binding.pbCardlist.setVisibility(View.VISIBLE);
            binding.llCardlistSearchSpinner.setVisibility(View.GONE);
            binding.llCardlistWishSpinner.setVisibility(View.GONE);
            binding.rvCardlist.setVisibility(View.GONE);
            binding.imCardlistCol.setVisibility(View.GONE);
            binding.imCardlistRow.setVisibility(View.GONE);
        } else {
            binding.pbCardlist.setVisibility(View.GONE);
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

    private void setWish(Card card){
        if (card.isWhish()){
            cardlistViewModel.deleteWish(card);
            Toast.makeText(mCtx, R.string.removedfromwishlist, Toast.LENGTH_SHORT).show();

        } else {
            cardlistViewModel.insertWish(card);
            Toast.makeText(mCtx, R.string.addedtowishlist, Toast.LENGTH_SHORT).show();
        }
    }
}
