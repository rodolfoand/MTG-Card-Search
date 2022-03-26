package com.example.mtgcardsearch.ui.advsearch;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;

import com.example.mtgcardsearch.R;
import com.example.mtgcardsearch.databinding.FragmentAdvsearchBinding;
import com.example.mtgcardsearch.databinding.FragmentHomeBinding;
import com.example.mtgcardsearch.ui.home.HomeViewModel;

public class AdvsearchFragment extends Fragment {

    private FragmentAdvsearchBinding binding;

    private Context mCtx;
    private Spinner sp_adv_lang;
    private MultiAutoCompleteTextView mactv_adv_type;
    private Spinner sp_adv_add_symbol;
    private Spinner sp_adv_stats_att;
    private Spinner sp_adv_stats_cond;
    private Spinner sp_adv_format_status;
    private Spinner sp_adv_format_value;
    private Spinner sp_adv_prices_curr;
    private Spinner sp_adv_prices_cond;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AdvsearchViewModel advsearchViewModel =
                new ViewModelProvider(this).get(AdvsearchViewModel.class);

        binding = FragmentAdvsearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mCtx = container.getContext();

        sp_adv_lang = binding.spAdvLang;
        ArrayAdapter<CharSequence> adapter_sp_adv_lang = ArrayAdapter.createFromResource(mCtx,
                R.array.array_language, android.R.layout.simple_spinner_item);
        adapter_sp_adv_lang.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_adv_lang.setAdapter(adapter_sp_adv_lang);

        mactv_adv_type = binding.mactvAdvType;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mCtx,
                R.array.array_type_line, android.R.layout.simple_dropdown_item_1line);
        mactv_adv_type.setAdapter(adapter);
        mactv_adv_type.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        sp_adv_add_symbol = binding.spAdvAddSymbol;
        ArrayAdapter<CharSequence> adapter_sp_adv_add_symbol = ArrayAdapter.createFromResource(mCtx,
                R.array.array_add_symbol, android.R.layout.simple_spinner_item);
        adapter_sp_adv_add_symbol.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_adv_add_symbol.setAdapter(adapter_sp_adv_add_symbol);

        sp_adv_stats_att = binding.spAdvStatsAtt;
        ArrayAdapter<CharSequence> adapter_sp_adv_stats_att = ArrayAdapter.createFromResource(mCtx,
                R.array.array_stats_att, android.R.layout.simple_spinner_item);
        adapter_sp_adv_stats_att.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_adv_stats_att.setAdapter(adapter_sp_adv_stats_att);

        sp_adv_stats_cond = binding.spAdvStatsCond;
        ArrayAdapter<CharSequence> adapter_sp_adv_stats_cond = ArrayAdapter.createFromResource(mCtx,
                R.array.array_stats_cond, android.R.layout.simple_spinner_item);
        adapter_sp_adv_stats_cond.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_adv_stats_cond.setAdapter(adapter_sp_adv_stats_cond);

        sp_adv_format_status = binding.spAdvFormatStatus;
        ArrayAdapter<CharSequence> adapter_sp_adv_format_status = ArrayAdapter.createFromResource(mCtx,
                R.array.array_format_status, android.R.layout.simple_spinner_item);
        adapter_sp_adv_format_status.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_adv_format_status.setAdapter(adapter_sp_adv_format_status);

        sp_adv_format_value = binding.spAdvFormatValue;
        ArrayAdapter<CharSequence> adapter_sp_adv_format_value = ArrayAdapter.createFromResource(mCtx,
                R.array.array_formats, android.R.layout.simple_spinner_item);
        adapter_sp_adv_format_value.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_adv_format_value.setAdapter(adapter_sp_adv_format_value);

        sp_adv_prices_curr = binding.spAdvPricesCurr;
        ArrayAdapter<CharSequence> adapter_sp_adv_prices_curr = ArrayAdapter.createFromResource(mCtx,
                R.array.array_prices_curr, android.R.layout.simple_spinner_item);
        adapter_sp_adv_prices_curr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_adv_prices_curr.setAdapter(adapter_sp_adv_prices_curr);

        sp_adv_prices_cond = binding.spAdvPricesCond;
        ArrayAdapter<CharSequence> adapter_sp_adv_prices_cond = ArrayAdapter.createFromResource(mCtx,
                R.array.array_prices_curr, android.R.layout.simple_spinner_item);
        adapter_sp_adv_prices_cond.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_adv_prices_cond.setAdapter(adapter_sp_adv_prices_cond);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}