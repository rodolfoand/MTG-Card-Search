package com.example.mtgcardsearch.ui.decklist;


import android.content.Context;
import android.os.Bundle;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mtgcardsearch.R;
import com.example.mtgcardsearch.databinding.FragmentDecklistBinding;
import com.example.mtgcardsearch.model.Deck;
import com.example.mtgcardsearch.model.DeckComparator;
import com.example.mtgcardsearch.model.OnActiveActionModeListener;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class DecklistFragment extends Fragment {

    private FragmentDecklistBinding binding;
    private DecklistViewModel decklistViewModel;

    private DecklistAdapter adapter_decklist;
    private ActionMode mActionMode;
    private ActionMode.Callback mCallback;
    private Context mCtx;
    private String parm_deck_sort;
    private String parm_deck_order;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDecklistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        DecklistViewModelFactory factory = new DecklistViewModelFactory(getActivity().getApplication());
        decklistViewModel = new ViewModelProvider(this, factory).get(DecklistViewModel.class);

        mCtx = container.getContext();

        adapter_decklist = new DecklistAdapter(mCtx);
        binding.rvDecklist.setAdapter(adapter_decklist);
        binding.rvDecklist.setLayoutManager(new LinearLayoutManager(getContext()));

        this.setDataList();
        this.setSpinner();



        binding.btDecklistNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_deck);
            }
        });

        mCallback = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater inflater = actionMode.getMenuInflater();
                inflater.inflate( R.menu.actionmode_deck, menu );
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case (R.id.actionmode_delete_deck):
                        decklistViewModel.deleteDecks(adapter_decklist.selectedItemPositionsSet);
                        break;
                    
                    default:
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                actionMode = null;
                Set<Deck> setlist = new ArraySet<>();
                setlist.addAll(adapter_decklist.selectedItemPositionsSet);
                adapter_decklist.selectedItemPositionsSet.clear();

                adapter_decklist.isAlwaysSelectable = false;
                adapter_decklist.isSelectableMode = false;

                for (Deck deck : setlist) {
                    adapter_decklist.notifyItemChanged(adapter_decklist.deckList.indexOf(deck));
                }
            }
        };

        adapter_decklist.setOnActiveActionMode(new OnActiveActionModeListener() {
            @Override
            public void onActiveActionMode(boolean active) {
                if (active){
                    mActionMode = getActivity().startActionMode(mCallback);

                } else {
                    mActionMode.finish();
                }
            }
        });

        adapter_decklist.selectedSetSize.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mActionMode.setTitle(integer.toString());
            }
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        if (mActionMode != null) mActionMode.finish();
    }
    private void showDeckMessage(int dif){
        if (dif > 0)
            Toast.makeText(mCtx, dif + " " + getString(R.string.cards_added), Toast.LENGTH_SHORT).show();
        if (dif < 0)
            Toast.makeText(mCtx, dif*-1 + " " + getString(R.string.cards_removed), Toast.LENGTH_SHORT).show();
    }

    private void setSpinner(){
        ArrayAdapter<CharSequence> adapter_sp_sort = ArrayAdapter.createFromResource(mCtx,
                R.array.array_deck_sort, android.R.layout.simple_spinner_item);
        adapter_sp_sort.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spDecklistSort.setAdapter(adapter_sp_sort);
        if (parm_deck_sort != null)
            binding.spDecklistSort.setSelection(Arrays.asList(getResources().getStringArray(R.array.array_order)).indexOf(parm_deck_sort));
        else
            parm_deck_sort = getResources().getStringArray(R.array.array_deck_sort)[0];
        binding.spDecklistSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String new_deck_sort = getResources().getStringArray(R.array.array_deck_sort)[i];

                if (!new_deck_sort.equals(parm_deck_sort)) {
                    parm_deck_sort = getResources().getStringArray(R.array.array_deck_sort)[i];
                    setDataList();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ArrayAdapter<CharSequence> adapter_sp_order = ArrayAdapter.createFromResource(mCtx,
                R.array.array_deck_order, android.R.layout.simple_spinner_item);
        adapter_sp_order.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spDecklistOrder.setAdapter(adapter_sp_order);
        if (parm_deck_order != null)
            binding.spDecklistOrder.setSelection(Arrays.asList(getResources().getStringArray(R.array.array_deck_order)).indexOf(parm_deck_order));
        else
            parm_deck_order = getResources().getStringArray(R.array.array_deck_order)[0];
        binding.spDecklistOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String new_deck_order = getResources().getStringArray(R.array.array_deck_order)[i];

                if (!new_deck_order.equals(parm_deck_order)) {
                    parm_deck_order = getResources().getStringArray(R.array.array_deck_order)[i];
                    setDataList();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setDataList(){

        decklistViewModel.getAllDecks().observe(getViewLifecycleOwner(), new Observer<List<Deck>>() {
            @Override
            public void onChanged(List<Deck> decks) {

                DeckComparator comparator = new DeckComparator(parm_deck_sort);
                if (parm_deck_order.equals("desc"))
                    Collections.sort(decks, comparator.reversed());
                else
                    Collections.sort(decks, comparator);

                adapter_decklist.setDeckList(decks);
                adapter_decklist.notifyDataSetChanged();

                binding.tvDecklistCount.setText("Count: " + decks.size());

                if (mActionMode != null)
                    showDeckMessage(decks.size() - adapter_decklist.deckList.size());
                if (mActionMode != null) mActionMode.finish();
            }
        });
    }
}