package com.example.mtgcardsearch.ui.cardslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtgcardsearch.R;
import com.example.mtgcardsearch.model.Card;

import java.util.List;

public class CardslistAdapter extends RecyclerView.Adapter<CardslistAdapter.CardslistViewHolder> {

    Context mCtx;
    List<Card> cardsList;

    public CardslistAdapter(Context mCtx, List<Card> cardsList) {
        this.mCtx = mCtx;
        this.cardsList = cardsList;
    }

    @NonNull
    @Override
    public CardslistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_cardslist, parent, false);

        return new CardslistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardslistViewHolder holder, int position) {
        Card card = cardsList.get(position);
        holder.text_cardname.setText(card.getName());
    }

    @Override
    public int getItemCount() {
        return cardsList.size();
    }

    public class CardslistViewHolder extends RecyclerView.ViewHolder {

        private TextView text_cardname;

        public CardslistViewHolder(@NonNull View itemView) {
            super(itemView);
            text_cardname = itemView.findViewById(R.id.text_cardname);
        }
    }
}
