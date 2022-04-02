package com.example.mtgcardsearch.ui.advsearch;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

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
    private List<String> arraySetNames;
    private List<String> arraySetIds;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        advsearchViewModel =
                new ViewModelProvider(this).get(AdvsearchViewModel.class);

        binding = FragmentAdvsearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mCtx = container.getContext();

        if (arraySetNames == null) arraySetNames = new ArrayList<>();
        if (arraySetNames == null) arraySetIds = new ArrayList<>();

        this.setSpinner();
        this.setMultiAutoComplete();

        List<String> langList= Arrays.stream(getResources().getStringArray(R.array.array_language)).collect(Collectors.toList());
        int langPos = langList.indexOf(advsearchViewModel.getPrefUserLang());

        if (langPos >= 0) binding.spAdvLang.setSelection(langPos);

        binding.btAdvSearch.setOnClickListener(new View.OnClickListener() {
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

        ArrayAdapter<CharSequence> adapter_sp_adv_lang = ArrayAdapter.createFromResource(mCtx,
                R.array.array_language, android.R.layout.simple_spinner_item);
        adapter_sp_adv_lang.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spAdvLang.setAdapter(adapter_sp_adv_lang);

        ArrayAdapter<CharSequence> adapter_sp_adv_add_symbol = ArrayAdapter.createFromResource(mCtx,
                R.array.array_add_symbol_values, android.R.layout.simple_spinner_item);
        adapter_sp_adv_add_symbol.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spAdvAddSymbol.setAdapter(adapter_sp_adv_add_symbol);
        binding.spAdvAddSymbol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    String mana = binding.etAdvMana.getText().toString();
                    mana += getResources().getStringArray(R.array.array_add_symbol_values)[i];
                    binding.etAdvMana.setText(mana);
                    binding.spAdvAddSymbol.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapter_sp_adv_stats_att = ArrayAdapter.createFromResource(mCtx,
                R.array.array_stats_att, android.R.layout.simple_spinner_item);
        adapter_sp_adv_stats_att.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spAdvStatsAtt.setAdapter(adapter_sp_adv_stats_att);

        ArrayAdapter<CharSequence> adapter_sp_adv_stats_cond = ArrayAdapter.createFromResource(mCtx,
                R.array.array_stats_cond, android.R.layout.simple_spinner_item);
        adapter_sp_adv_stats_cond.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spAdvStatsCond.setAdapter(adapter_sp_adv_stats_cond);

        ArrayAdapter<CharSequence> adapter_sp_adv_format_status = ArrayAdapter.createFromResource(mCtx,
                R.array.array_format_status, android.R.layout.simple_spinner_item);
        adapter_sp_adv_format_status.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spAdvFormatStatus.setAdapter(adapter_sp_adv_format_status);

        ArrayAdapter<CharSequence> adapter_sp_adv_format_value = ArrayAdapter.createFromResource(mCtx,
                R.array.array_formats, android.R.layout.simple_spinner_item);
        adapter_sp_adv_format_value.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spAdvFormatValue.setAdapter(adapter_sp_adv_format_value);

        ArrayAdapter<CharSequence> adapter_sp_adv_prices_curr = ArrayAdapter.createFromResource(mCtx,
                R.array.array_prices_curr, android.R.layout.simple_spinner_item);
        adapter_sp_adv_prices_curr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spAdvPricesCurr.setAdapter(adapter_sp_adv_prices_curr);

        ArrayAdapter<CharSequence> adapter_sp_adv_prices_cond = ArrayAdapter.createFromResource(mCtx,
                R.array.array_prices_cond, android.R.layout.simple_spinner_item);
        adapter_sp_adv_prices_cond.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spAdvPricesCond.setAdapter(adapter_sp_adv_prices_cond);
    }

    private void setMultiAutoComplete(){

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mCtx,
                R.array.array_type_line, android.R.layout.simple_dropdown_item_1line);
        binding.mactvAdvType.setAdapter(adapter);
        binding.mactvAdvType.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        ArrayAdapter<CharSequence> adapter_adv_criteria = ArrayAdapter.createFromResource(mCtx,
                R.array.array_criteria, android.R.layout.simple_dropdown_item_1line);
        binding.mactvAdvCriteria.setAdapter(adapter_adv_criteria);
        binding.mactvAdvCriteria.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        binding.mactvAdvSetsIn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (arraySetNames.isEmpty()) {
                    binding.piAdvSetsIn.setVisibility(View.VISIBLE);
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
                binding.piAdvSetsIn.setVisibility(View.GONE);
                arraySetNames = setSearchResult.getData().stream()
                        .map(set -> set.getName())//typecast into String List
                        .collect(Collectors.toList());

                arraySetIds = setSearchResult.getData().stream()
                        .map(set -> set.getCode())//typecast into String List
                        .collect(Collectors.toList());

                ArrayAdapter<String> adapterSetNames = new ArrayAdapter<String>(mCtx,
                        android.R.layout.simple_dropdown_item_1line, arraySetNames.toArray(new String[0]));
                binding.mactvAdvSetsIn.setAdapter(adapterSetNames);
                binding.mactvAdvSetsIn.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
            }
        });
    }

    private String getQuery(){
        String q = "";

        if (!binding.etAdvName.getText().toString().isEmpty())
            q += binding.etAdvName.getText().toString();

        if (binding.spAdvLang.getSelectedItemPosition() > 0){
            q += " lang:" + getResources().getStringArray(R.array.array_language)[binding.spAdvLang.getSelectedItemPosition()];
        }

        if (!binding.etAdvText.getText().toString().isEmpty())
            q += " oracle:" + binding.etAdvText.getText().toString();

        List<String> types = Arrays.stream(binding.mactvAdvType.getText().toString()
                        .replaceAll("\\s", "")
                        .split(","))
                .collect(Collectors.toList());
        for (String type : types) {
            if (!type.isEmpty()) q += " type:" + type.toLowerCase();
        }

        String color = "";
        if (binding.cbAdvColorWhite.isChecked()) color += "W";
        if (binding.cbAdvColorBlue.isChecked()) color += "U";
        if (binding.cbAdvColorBlack.isChecked()) color += "B";
        if (binding.cbAdvColorRed.isChecked()) color += "R";
        if (binding.cbAdvColorGreen.isChecked()) color += "G";
        if (!color.isEmpty()) q += " color=" + color;

        color = "";
        if (binding.cbAdvCommWhite.isChecked()) color += "W";
        if (binding.cbAdvCommBlue.isChecked()) color += "U";
        if (binding.cbAdvCommBlack.isChecked()) color += "B";
        if (binding.cbAdvCommRed.isChecked()) color += "R";
        if (binding.cbAdvCommGreen.isChecked()) color += "G";
        if (!color.isEmpty()) q += " commander:" + color;

        if (!binding.etAdvMana.getText().toString().isEmpty())
            q += " mana:" + binding.etAdvMana.getText().toString();

        if (!binding.tvAdvStatsValue.getText().toString().isEmpty()) {
            q += " "
                    + getResources().getStringArray(R.array.array_stats_att)[binding.spAdvStatsAtt.getSelectedItemPosition()]
                    + getResources().getStringArray(R.array.array_stats_cond)[binding.spAdvStatsCond.getSelectedItemPosition()]
                    + binding.tvAdvStatsValue.getText().toString();
        }

        if (binding.spAdvFormatValue.getSelectedItemPosition() != 0){
            q += " " + getResources().getStringArray(R.array.array_format_status)[binding.spAdvFormatStatus.getSelectedItemPosition()]
                    + ":"
                    + getResources().getStringArray(R.array.array_formats)[binding.spAdvFormatValue.getSelectedItemPosition()];
        }

        if (!binding.mactvAdvSetsIn.getText().toString().isEmpty()) {

            List<String> sets = Arrays.stream(binding.mactvAdvSetsIn.getText().toString()
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
                            binding.mactvAdvSetsIn.setError(getString(R.string.valid_set));
                            binding.mactvAdvSetsIn.setFocusable(true);
                            return "";
                        }
                    if (sets.indexOf(set) == sets.size() - 1) q += ")";
                }
            }
        }

        List<String> rares = new ArrayList<>();
        if (binding.cbAdvRarCommon.isChecked()) rares.add("c");
        if (binding.cbAdvRarUncommon.isChecked()) rares.add("u");
        if (binding.cbAdvRarRare.isChecked()) rares.add("r");
        if (binding.cbAdvRarMyth.isChecked()) rares.add("m");

        if (rares.size() > 0) {
            for (String rare : rares) {
                if (rares.indexOf(rare) == 0) q += " (";
                if (rares.indexOf(rare) > 0) q += " OR ";
                if (!rare.trim().isEmpty()) q += " rarity:" + rare;
                if (rares.indexOf(rare) == rares.size() - 1) q += ")";
            }
        }

        if (!binding.etAdvPricesValue.getText().toString().isEmpty()){
            q += " "
                    + getResources().getStringArray(R.array.array_prices_curr)[binding.spAdvPricesCurr.getSelectedItemPosition()]
                    + getResources().getStringArray(R.array.array_prices_cond)[binding.spAdvPricesCond.getSelectedItemPosition()]
                    + binding.etAdvPricesValue.getText().toString();
        }

        if (!binding.etAdvArtist.getText().toString().isEmpty())
            q += " artist:" + binding.etAdvArtist.getText().toString();

        return q;
    }
}