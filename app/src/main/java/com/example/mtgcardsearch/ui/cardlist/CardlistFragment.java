package com.example.mtgcardsearch.ui.cardlist;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mtgcardsearch.R;
import com.example.mtgcardsearch.databinding.FragmentCardlistBinding;
import com.example.mtgcardsearch.model.ListSearchResult;
import com.example.mtgcardsearch.model.OnBottomReachedListener;

import java.util.Locale;


public class CardlistFragment extends Fragment {

    private FragmentCardlistBinding binding;

    private RecyclerView rv_cardlist;
    private CardlistAdapter adapter_cardlist;
    private Context mCtx;
    private ListSearchResult dataList;
    private TextView tx_search_result;
    private Button bt_search_more;
    private int count_cards;
    private Spinner spinner_unique;
    private Spinner spinner_order;
    private Spinner spinner_dir;
    private CardlistViewModel cardlistViewModel;
    private String parm_unique;
    private String parm_order;
    private String parm_dir;
    private String parm_query;
    private String parm_page;
    private String parm_include_multilingual;
    private String parm_include_extras;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cardlistViewModel =
                new ViewModelProvider(this).get(CardlistViewModel.class);

        binding = FragmentCardlistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mCtx = container.getContext();
        tx_search_result = binding.txSearchResult;
        bt_search_more = binding.btSearchMore;

        this.setSpinner();
        this.setParms();

        rv_cardlist = binding.rvCardlist;
        rv_cardlist.setHasFixedSize(true);
        rv_cardlist.setLayoutManager(new LinearLayoutManager(getContext()));

        parm_query = getArguments().getString("query");

        this.setListAdapter();
        this.setDataList();

        bt_search_more.setOnClickListener(new View.OnClickListener() {
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

                setDataList();

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

    private void setListAdapter(){

        adapter_cardlist = new CardlistAdapter(mCtx);
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

    private void setSpinner(){
        spinner_unique = binding.spinnerUnique;
        ArrayAdapter<CharSequence> adapter_sp_unique = ArrayAdapter.createFromResource(mCtx,
                R.array.array_unique, android.R.layout.simple_spinner_item);
        adapter_sp_unique.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_unique.setAdapter(adapter_sp_unique);

        spinner_unique.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String new_unique = getResources().getStringArray(R.array.array_unique)[i];

                if (!new_unique.equals(parm_unique)) {
                    parm_unique = getResources().getStringArray(R.array.array_unique)[i];
                    parm_page = "1";
                    setDataList();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_order = binding.spinnerOrder;
        ArrayAdapter<CharSequence> adapter_sp_order = ArrayAdapter.createFromResource(mCtx,
                R.array.array_order, android.R.layout.simple_spinner_item);
        adapter_sp_order.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_order.setAdapter(adapter_sp_order);

        spinner_order.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String new_order = getResources().getStringArray(R.array.array_order)[i];

                if (!new_order.equals(parm_order)) {
                    parm_order = getResources().getStringArray(R.array.array_order)[i];
                    parm_page = "1";
                    setDataList();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_dir = binding.spinnerDir;
        ArrayAdapter<CharSequence> adapter_sp_dir = ArrayAdapter.createFromResource(mCtx,
                R.array.array_dir, android.R.layout.simple_spinner_item);
        adapter_sp_dir.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_dir.setAdapter(adapter_sp_dir);

        spinner_dir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String new_dir = getResources().getStringArray(R.array.array_dir)[i];

                if (!new_dir.equals(parm_dir)) {
                    parm_order = getResources().getStringArray(R.array.array_dir)[i];
                    parm_page = "1";
                    setDataList();
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
        cardlistViewModel.getList(
                parm_include_extras
                , parm_include_multilingual
                , parm_order
                , parm_page
                , parm_unique
                , parm_dir
                , parm_query)
                .observe(getViewLifecycleOwner(), new Observer<ListSearchResult>() {
                    @Override
                    public void onChanged(ListSearchResult listSearchResult) {
                        dataList = listSearchResult;
                        bt_search_more.setVisibility(View.GONE);

                        count_cards = listSearchResult.getData().size()
                                + (Integer.parseInt(parm_page) - 1)
                                * 175;

                        tx_search_result.setText(getStringResult(listSearchResult.getData().size()));

                        adapter_cardlist.setListSearchResult(dataList);
                        adapter_cardlist.notifyDataSetChanged();
                        rv_cardlist.scrollToPosition(0);

                    }
                });
    }
}