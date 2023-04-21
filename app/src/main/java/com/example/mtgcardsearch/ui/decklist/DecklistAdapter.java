package com.example.mtgcardsearch.ui.decklist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtgcardsearch.R;
import com.example.mtgcardsearch.model.Card;
import com.example.mtgcardsearch.model.Deck;
import com.example.mtgcardsearch.ui.cardlist.CardlistAdapter;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class DecklistAdapter extends RecyclerView.Adapter<DecklistAdapter.DecklistViewHolder> {


    Context mCtx;
    List<Deck> deckList;

    public DecklistAdapter(Context mCtx) {
        this.mCtx = mCtx;
    }

    public void setDeckList(List<Deck> deckList) {
        this.deckList = deckList;
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

        holder.deck_list_decklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mCtx, deck.getName(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("id", deck.getDeck_id() + "");
                Navigation.findNavController(view).navigate(R.id.nav_deck, bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (deckList == null) ? 0 : deckList.size();
    }


    public class DecklistViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_deckname;
        private MaterialCardView deck_list_decklist;
        public DecklistViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_deckname = itemView.findViewById(R.id.tv_item_decklist_name);
            deck_list_decklist = itemView.findViewById(R.id.deck_item_decklist);
        }
    }
}
