package com.example.mtgcardsearch.ui.setlist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtgcardsearch.R;
import com.example.mtgcardsearch.databinding.FragmentSetlistBinding;
import com.example.mtgcardsearch.model.Set;
import com.example.mtgcardsearch.model.SetSearchResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SetlistFragment extends Fragment {

    private FragmentSetlistBinding binding;
    private SetlistViewModel setlistViewModel;

    private Context mCtx;
    private SetlistAdapter adapter_setlist;
    private String set_filter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setlistViewModel =
                new ViewModelProvider(this).get(SetlistViewModel.class);

        binding = FragmentSetlistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mCtx = container.getContext();

        binding.rvSetlist.setHasFixedSize(true);
        binding.rvSetlist.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter_setlist = new SetlistAdapter(mCtx);
        binding.rvSetlist.setAdapter(adapter_setlist);

        this.setSpinner();

        return root;
    }

    private void setSpinner(){

        ArrayAdapter<CharSequence> adapter_sp_filter = ArrayAdapter.createFromResource(mCtx,
                R.array.array_set_filter, android.R.layout.simple_spinner_item);
        adapter_sp_filter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spSetFilter.setAdapter(adapter_sp_filter);

        set_filter = setlistViewModel.getPrefSetlistFilter();
        binding.spSetFilter.setSelection(Arrays.asList(getResources().getStringArray(R.array.array_set_filter)).indexOf(set_filter));


        binding.spSetFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String new_set_filter = getResources().getStringArray(R.array.array_set_filter)[i];

                if (set_filter != new_set_filter) {
                    set_filter = new_set_filter;
                    setlistViewModel.setPrefSetlistFilter(set_filter);
                    getSets();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getSets(){

        binding.rvSetlist.setVisibility(View.GONE);
        binding.spSetFilter.setVisibility(View.GONE);

        setlistViewModel.getSets("").observe(getViewLifecycleOwner(), new Observer<SetSearchResult>() {
            @Override
            public void onChanged(SetSearchResult setSearchResult) {
                binding.pbSetlist.setVisibility(View.GONE);
                binding.spSetFilter.setVisibility(View.VISIBLE);
                binding.rvSetlist.setVisibility(View.VISIBLE);

                List<Set> newList = new ArrayList<>();
                if (binding.spSetFilter.getSelectedItemPosition() == 0){
                    newList = setSearchResult.getData();
                } else {
                    newList = setSearchResult.getData()
                            .stream()
                            .filter(s -> s.getSet_type().equals(set_filter))
                            .collect(Collectors.toList());
                }
                String s_set_result = getString(R.string.countlabel) + ": " + newList.size();
                binding.txSetResult.setText(s_set_result);

                adapter_setlist.setSetSearchResult(setSearchResult);
                adapter_setlist.setList(newList);
                adapter_setlist.notifyDataSetChanged();
            }
        });
    }
}