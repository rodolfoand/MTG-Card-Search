package com.example.mtgcardsearch.ui.decklist;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtgcardsearch.R;
import com.example.mtgcardsearch.model.Card;
import com.example.mtgcardsearch.model.Deck;
import com.example.mtgcardsearch.model.OnActiveActionModeListener;
import com.example.mtgcardsearch.ui.cardlist.CardlistAdapter;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class DecklistAdapter extends RecyclerView.Adapter<DecklistAdapter.DecklistViewHolder> {


    Context mCtx;
    List<Deck> deckList;
    List<Deck> selectedItemPositionsSet = new ArrayList<>();
    MutableLiveData<Integer> selectedSetSize;
    Boolean isAlwaysSelectable;
    Boolean isSelectableMode;
    OnActiveActionModeListener onActiveActionModeListener;

    public DecklistAdapter(Context mCtx) {
        this.mCtx = mCtx;
        this.selectedSetSize = new MutableLiveData<>();
        this.isAlwaysSelectable = false;
        this.isSelectableMode = false;
    }

    public void setDeckList(List<Deck> deckList) {
        this.deckList = deckList;
    }

    public void setOnActiveActionMode(OnActiveActionModeListener onActiveActionModeListener) {
        this.onActiveActionModeListener = onActiveActionModeListener;
    }

    @NonNull
    @Override
    public DecklistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_decklist, parent, false);
        return new DecklistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DecklistViewHolder holder, int position) {
        Deck deck = deckList.get(position);

        holder.tv_deckname.setText(deck.getName());

        if (!TextUtils.isEmpty(deck.getFormat())) {
            holder.tv_format.setText(deck.getFormat());
            holder.tv_format.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(deck.getNotes())) {
            holder.tv_notes.setText(deck.getNotes());
            holder.tv_notes.setVisibility(View.VISIBLE);
        }
        if(isSelectedItem(position)){
            holder.deck_list_decklist.setChecked(true);
        }
        else {
            holder.deck_list_decklist.setChecked(false);
        }

        holder.deck_list_decklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isSelectableMode && !isAlwaysSelectable) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", deck.getDeck_id() + "");
                    Navigation.findNavController(view).navigate(R.id.nav_deck, bundle);
                } else {
                    if (isSelectedItem(holder.getAdapterPosition())){
                        removeSelectedItem(holder.getAdapterPosition());
                    } else {
                        addSelectedItem(holder.getAdapterPosition());
                    }
                    holder.deck_list_decklist.setChecked(!holder.deck_list_decklist.isChecked());
                }
            }
        });
        
        holder.deck_list_decklist.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (isSelectedItem(holder.getAdapterPosition())) {
                    removeSelectedItem(holder.getAdapterPosition());
                } else {
                    addSelectedItem(holder.getAdapterPosition());
                }
                holder.deck_list_decklist.setChecked(!holder.deck_list_decklist.isChecked());

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return (deckList == null) ? 0 : deckList.size();
    }
    private Boolean isSelectedItem(int position){
        return selectedItemPositionsSet.contains(deckList.get(position));
    }

    private void addSelectedItem(int position){
        if(selectedItemPositionsSet.isEmpty() && !isAlwaysSelectable) {
            isSelectableMode = true;
            onActiveActionModeListener.onActiveActionMode(true);
        }
        selectedItemPositionsSet.add(deckList.get(position));
        selectedSetSize.setValue(selectedItemPositionsSet.size());
    }

    private void removeSelectedItem(int position){
        selectedItemPositionsSet.remove(deckList.get(position));
        selectedSetSize.setValue(selectedItemPositionsSet.size());

        if(selectedItemPositionsSet.isEmpty() && !isAlwaysSelectable){
            isSelectableMode = false;
            onActiveActionModeListener.onActiveActionMode(false);
        }
    }


    public class DecklistViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_deckname;
        private MaterialCardView deck_list_decklist;
        private TextView tv_format;
        private TextView tv_notes;
        public DecklistViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_deckname = itemView.findViewById(R.id.tv_item_decklist_name);
            deck_list_decklist = itemView.findViewById(R.id.deck_item_decklist);
            tv_format = itemView.findViewById(R.id.tv_item_decklist_format);
            tv_notes = itemView.findViewById(R.id.tv_item_decklist_notes);
        }
    }
}
