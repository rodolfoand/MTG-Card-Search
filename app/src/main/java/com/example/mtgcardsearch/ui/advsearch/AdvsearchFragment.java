package com.example.mtgcardsearch.ui.advsearch;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mtgcardsearch.R;
import com.example.mtgcardsearch.databinding.FragmentAdvsearchBinding;
import com.example.mtgcardsearch.model.SetSearchResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AdvsearchFragment extends Fragment {

    private FragmentAdvsearchBinding binding;
    private AdvsearchViewModel advsearchViewModel;

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
    private MultiAutoCompleteTextView mactv_adv_sets_in;
    private List<String> arraySetNames;
    private List<String> arraySetIds;
    private ProgressBar pi_adv_sets_in;
    private MultiAutoCompleteTextView mactv_adv_criteria;
    private Button bt_adv_search;
    private EditText et_adv_name;
    private EditText et_adv_text;
    private CheckBox cb_adv_color_white;
    private CheckBox cb_adv_color_blue;
    private CheckBox cb_adv_color_black;
    private CheckBox cb_adv_color_red;
    private CheckBox cb_adv_color_green;
    private CheckBox cb_adv_comm_white;
    private CheckBox cb_adv_comm_blue;
    private CheckBox cb_adv_comm_black;
    private CheckBox cb_adv_comm_red;
    private CheckBox cb_adv_comm_green;
    private EditText et_adv_mana;
    private EditText tv_adv_stats_value;
    private CheckBox cb_adv_rar_common;
    private CheckBox cb_adv_rar_uncommon;
    private CheckBox cb_adv_rar_rare;
    private CheckBox cb_adv_rar_myth;
    private EditText et_adv_prices_value;
    private EditText et_adv_artist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        advsearchViewModel =
                new ViewModelProvider(this).get(AdvsearchViewModel.class);

        binding = FragmentAdvsearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mCtx = container.getContext();
        bt_adv_search = binding.btAdvSearch;

        if (arraySetNames == null) arraySetNames = new ArrayList<>();
        if (arraySetNames == null) arraySetIds = new ArrayList<>();

        this.setSpinner();
        this.setMultiAutoComplete();

        List<String> langList= Arrays.stream(getResources().getStringArray(R.array.array_language)).collect(Collectors.toList());
        int langPos = langList.indexOf(advsearchViewModel.getPrefUserLang());

        if (langPos >= 0) binding.spAdvLang.setSelection(langPos);

        bt_adv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!getQuery().isEmpty()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("query", getQuery());
                    Navigation.findNavController(view).navigate(R.id.nav_cardlist, bundle);
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

    private void setSpinner(){

        sp_adv_lang = binding.spAdvLang;
        ArrayAdapter<CharSequence> adapter_sp_adv_lang = ArrayAdapter.createFromResource(mCtx,
                R.array.array_language, android.R.layout.simple_spinner_item);
        adapter_sp_adv_lang.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_adv_lang.setAdapter(adapter_sp_adv_lang);

        sp_adv_add_symbol = binding.spAdvAddSymbol;
        et_adv_mana = binding.etAdvMana;
        ArrayAdapter<CharSequence> adapter_sp_adv_add_symbol = ArrayAdapter.createFromResource(mCtx,
                R.array.array_add_symbol_values, android.R.layout.simple_spinner_item);
        adapter_sp_adv_add_symbol.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_adv_add_symbol.setAdapter(adapter_sp_adv_add_symbol);
        sp_adv_add_symbol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    String mana = et_adv_mana.getText().toString();
                    mana += getResources().getStringArray(R.array.array_add_symbol_values)[i];
                    et_adv_mana.setText(mana);
                    sp_adv_add_symbol.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
                R.array.array_prices_cond, android.R.layout.simple_spinner_item);
        adapter_sp_adv_prices_cond.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_adv_prices_cond.setAdapter(adapter_sp_adv_prices_cond);
    }

    private void setMultiAutoComplete(){

        mactv_adv_type = binding.mactvAdvType;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mCtx,
                R.array.array_type_line, android.R.layout.simple_dropdown_item_1line);
        mactv_adv_type.setAdapter(adapter);
        mactv_adv_type.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        mactv_adv_criteria = binding.mactvAdvCriteria;
        ArrayAdapter<CharSequence> adapter_adv_criteria = ArrayAdapter.createFromResource(mCtx,
                R.array.array_criteria, android.R.layout.simple_dropdown_item_1line);
        mactv_adv_criteria.setAdapter(adapter_adv_criteria);
        mactv_adv_criteria.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());


        mactv_adv_sets_in = binding.mactvAdvSetsIn;
        pi_adv_sets_in = binding.piAdvSetsIn;

        mactv_adv_sets_in.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (arraySetNames.isEmpty()) {
                    pi_adv_sets_in.setVisibility(View.VISIBLE);
                    setSetlist();
                }
                return false;
            }
        });
    }

    private void setSetlist(){
        advsearchViewModel.getSets("").observe(getViewLifecycleOwner(), new Observer<SetSearchResult>() {
            @Override
            public void onChanged(SetSearchResult setSearchResult) {
                pi_adv_sets_in.setVisibility(View.GONE);
                arraySetNames = setSearchResult.getData().stream()
                        .map(set -> set.getName())//typecast into String List
                        .collect(Collectors.toList());

                arraySetIds = setSearchResult.getData().stream()
                        .map(set -> set.getCode())//typecast into String List
                        .collect(Collectors.toList());

                ArrayAdapter<String> adapterSetNames = new ArrayAdapter<String>(mCtx,
                        android.R.layout.simple_dropdown_item_1line, arraySetNames.toArray(new String[0]));
                mactv_adv_sets_in.setAdapter(adapterSetNames);
                mactv_adv_sets_in.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
            }
        });
    }

    private String getQuery(){
        String q = "";

        et_adv_name = binding.etAdvName;
        et_adv_text = binding.etAdvText;
        cb_adv_color_white = binding.cbAdvColorWhite;
        cb_adv_color_blue = binding.cbAdvColorBlue;
        cb_adv_color_black = binding.cbAdvColorBlack;
        cb_adv_color_red = binding.cbAdvColorRed;
        cb_adv_color_green = binding.cbAdvColorGreen;
        cb_adv_comm_white = binding.cbAdvCommWhite;
        cb_adv_comm_blue = binding.cbAdvCommBlue;
        cb_adv_comm_black = binding.cbAdvCommBlack;
        cb_adv_comm_red = binding.cbAdvCommRed;
        cb_adv_comm_green = binding.cbAdvCommGreen;
        tv_adv_stats_value = binding.tvAdvStatsValue;
        cb_adv_rar_common = binding.cbAdvRarCommon;
        cb_adv_rar_uncommon = binding.cbAdvRarUncommon;
        cb_adv_rar_rare = binding.cbAdvRarRare;
        cb_adv_rar_myth = binding.cbAdvRarMyth;
        et_adv_prices_value = binding.etAdvPricesValue;
        et_adv_artist = binding.etAdvArtist;

        if (!et_adv_name.getText().toString().isEmpty())
            q += et_adv_name.getText().toString();

        if (sp_adv_lang.getSelectedItemPosition() > 0){
            q += " lang:" + getResources().getStringArray(R.array.array_language)[sp_adv_lang.getSelectedItemPosition()];
        }

        if (!et_adv_text.getText().toString().isEmpty())
            q += " oracle:" + et_adv_text.getText().toString();

        List<String> types = Arrays.stream(mactv_adv_type.getText().toString()
                        .replaceAll("\\s", "")
                        .split(","))
                .collect(Collectors.toList());
        for (String type : types) {
            if (!type.isEmpty()) q += " type:" + type.toLowerCase();
        }

        String color = "";
        if (cb_adv_color_white.isChecked()) color += "W";
        if (cb_adv_color_blue.isChecked()) color += "U";
        if (cb_adv_color_black.isChecked()) color += "B";
        if (cb_adv_color_red.isChecked()) color += "R";
        if (cb_adv_color_green.isChecked()) color += "G";
        if (!color.isEmpty()) q += " color=" + color;

        color = "";
        if (cb_adv_comm_white.isChecked()) color += "W";
        if (cb_adv_comm_blue.isChecked()) color += "U";
        if (cb_adv_comm_black.isChecked()) color += "B";
        if (cb_adv_comm_red.isChecked()) color += "R";
        if (cb_adv_comm_green.isChecked()) color += "G";
        if (!color.isEmpty()) q += " commander:" + color;

        if (!et_adv_mana.getText().toString().isEmpty())
            q += " mana:" + et_adv_mana.getText().toString();

        if (!tv_adv_stats_value.getText().toString().isEmpty()) {
            q += " "
                    + getResources().getStringArray(R.array.array_stats_att)[sp_adv_stats_att.getSelectedItemPosition()]
                    + getResources().getStringArray(R.array.array_stats_cond)[sp_adv_stats_cond.getSelectedItemPosition()]
                    + tv_adv_stats_value.getText().toString();
        }

        if (sp_adv_format_value.getSelectedItemPosition() != 0){
            q += " " + getResources().getStringArray(R.array.array_format_status)[sp_adv_format_status.getSelectedItemPosition()]
                    + ":"
                    + getResources().getStringArray(R.array.array_formats)[sp_adv_format_value.getSelectedItemPosition()];
        }

        if (!mactv_adv_sets_in.getText().toString().isEmpty()) {

            List<String> sets = Arrays.stream(mactv_adv_sets_in.getText().toString()
                    .split(","))
                    .collect(Collectors.toList());
            if (sets.size() > 0 && arraySetNames.size() > 0 && arraySetIds.size() > 0) {
                for (String set : sets) {
                    if (sets.indexOf(set) == 0) q += " (";
                    if (sets.indexOf(set) > 0) q += " OR ";
                    if (!set.trim().isEmpty())
                        if (arraySetNames.indexOf(set.trim()) >= 0) {
                            q += " set:" + arraySetIds.get(arraySetNames.indexOf(set.trim()));
                        } else {
                            mactv_adv_sets_in.setError(getString(R.string.valid_set));
                            mactv_adv_sets_in.setFocusable(true);
                            return "";
                        }
                    if (sets.indexOf(set) == sets.size() - 1) q += ")";
                }
            }
        }

        List<String> rares = new ArrayList<>();
        if (cb_adv_rar_common.isChecked()) rares.add("c");
        if (cb_adv_rar_uncommon.isChecked()) rares.add("u");
        if (cb_adv_rar_rare.isChecked()) rares.add("r");
        if (cb_adv_rar_myth.isChecked()) rares.add("m");

        if (rares.size() > 0) {
            for (String rare : rares) {
                if (rares.indexOf(rare) == 0) q += " (";
                if (rares.indexOf(rare) > 0) q += " OR ";
                if (!rare.trim().isEmpty()) q += " rarity:" + rare;
                if (rares.indexOf(rare) == rares.size() - 1) q += ")";
            }
        }

        if (!et_adv_prices_value.getText().toString().isEmpty()){
            q += " "
                    + getResources().getStringArray(R.array.array_prices_curr)[sp_adv_prices_curr.getSelectedItemPosition()]
                    + getResources().getStringArray(R.array.array_prices_cond)[sp_adv_prices_cond.getSelectedItemPosition()]
                    + et_adv_prices_value.getText().toString();
        }

        if (!et_adv_artist.getText().toString().isEmpty())
            q += " artist:" + et_adv_artist.getText().toString();

        return q;
    }
}