package com.example.mtgcardsearch.ui.setlist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtgcardsearch.databinding.FragmentSetlistBinding;
import com.example.mtgcardsearch.model.SetSearchResult;

public class SetlistFragment extends Fragment {

    private FragmentSetlistBinding binding;

    private RecyclerView rv_setlist;
    private Context mCtx;
    private SetlistAdapter adapter_setlist;
    private ProgressBar pb_setlist;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SetlistViewModel setlistViewModel =
                new ViewModelProvider(this).get(SetlistViewModel.class);

        binding = FragmentSetlistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mCtx = container.getContext();
        pb_setlist = binding.pbSetlist;

        rv_setlist = binding.rvSetlist;
        rv_setlist.setHasFixedSize(true);
        rv_setlist.setLayoutManager(new LinearLayoutManager(getContext()));


        adapter_setlist = new SetlistAdapter(mCtx);
        rv_setlist.setAdapter(adapter_setlist);

        setlistViewModel.getSets("").observe(getViewLifecycleOwner(), new Observer<SetSearchResult>() {
            @Override
            public void onChanged(SetSearchResult setSearchResult) {
                pb_setlist.setVisibility(View.GONE);

                adapter_setlist.setSetSearchResult(setSearchResult);
                adapter_setlist.notifyDataSetChanged();
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