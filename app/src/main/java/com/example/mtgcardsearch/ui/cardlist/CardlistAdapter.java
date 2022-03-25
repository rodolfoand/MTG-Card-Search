package com.example.mtgcardsearch.ui.cardlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mtgcardsearch.R;
import com.example.mtgcardsearch.model.Card;
import com.example.mtgcardsearch.model.CardSearchResult;
import com.example.mtgcardsearch.model.OnBottomReachedListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CardlistAdapter extends RecyclerView.Adapter<CardlistAdapter.CardlistViewHolder> {

    Context mCtx;
    CardSearchResult cardSearchResult;
    List<Card> cardList;
    OnBottomReachedListener onBottomReachedListener;

    public CardlistAdapter(Context mCtx) {
        this.mCtx = mCtx;
    }

    public void setCardSearchResult(CardSearchResult cardSearchResult){
        this.cardList = cardSearchResult.getData();
        this.cardSearchResult = cardSearchResult;
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){
        this.onBottomReachedListener = onBottomReachedListener;
    }

    @NonNull
    @Override
    public CardlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cardlist, parent, false);

        return new CardlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardlistViewHolder holder, int position) {
        Card card = cardList.get(position);
        card.setImage_url();

        setCardImage(holder.iv_cardimage, card.getImage_url());
        holder.fab_flip.setAlpha(0.60f);
        holder.fab_flip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card.setFace_position();
                card.setImage_url();
                setCardImage(holder.iv_cardimage, card.getImage_url());
            }
        });

        if (!card.hasImageInCardFaces())
            holder.fab_flip.setVisibility(View.INVISIBLE);
        else
            holder.fab_flip.setVisibility(View.VISIBLE);

        if (position == cardList.size() - 1)
            onBottomReachedListener.onBottomReached(position);
    }

    @Override
    public int getItemCount() {
        return (cardList == null) ? 0 : cardList.size();
    }

    public void setCardImage(ImageView iv_cardimage, String url_image){
        Glide.with(iv_cardimage.getContext())
                .load(url_image)
                .into(iv_cardimage);
    }

    public class CardlistViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_cardimage;
        private FloatingActionButton fab_flip;

        public CardlistViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_cardimage = itemView.findViewById(R.id.iv_cardimage);
            fab_flip = itemView.findViewById(R.id.fab_flip);
        }
    }
}
