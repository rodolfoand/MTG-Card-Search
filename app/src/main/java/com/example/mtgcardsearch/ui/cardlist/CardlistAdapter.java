package com.example.mtgcardsearch.ui.cardlist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mtgcardsearch.R;
import com.example.mtgcardsearch.model.Card;
import com.example.mtgcardsearch.model.CardSearchResult;
import com.example.mtgcardsearch.model.OnBottomReachedListener;
import com.example.mtgcardsearch.model.OnSetWishListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CardlistAdapter extends RecyclerView.Adapter<CardlistAdapter.CardlistViewHolder> {

    Context mCtx;
    List<Card> cardList;
    OnBottomReachedListener onBottomReachedListener;
    List<String> wishList;
    OnSetWishListener onSetWishListener;

    public CardlistAdapter(Context mCtx) {
        this.mCtx = mCtx;
        this.wishList = new ArrayList<>();
    }

    public void setCardList(List<Card> list){
        this.cardList = list;
    }

    public void setWishList(List<String> wishList) {
        this.wishList = wishList;
    }

    public void setOnSetWishListener(OnSetWishListener onSetWishListener) {
        this.onSetWishListener = onSetWishListener;
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
        card.initialize();

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

        if (wishList.indexOf(card.getId()) >= 0) card.setWhish(true);
        else card.setWhish(false);


        if (card.isWhish())
            holder.mb_cardlist_wish.setIcon(ContextCompat.getDrawable(mCtx, R.drawable.ic_baseline_favorite_24));
        else
            holder.mb_cardlist_wish.setIcon(ContextCompat.getDrawable(mCtx, R.drawable.ic_baseline_favorite_border_24));


        holder.iv_cardimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("id", card.getId());
                Navigation.findNavController(view).navigate(R.id.nav_card, bundle);
            }
        });

        holder.mb_cardlist_wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSetWishListener.onSetWish(card);
                if (card.isWhish()){
                    card.setWhish(false);
                    holder.mb_cardlist_wish.setIcon(
                            ContextCompat
                                    .getDrawable(mCtx, R.drawable.ic_baseline_favorite_border_24));
                } else {
                    card.setWhish(true);
                    holder.mb_cardlist_wish.setIcon(
                            ContextCompat
                                    .getDrawable(mCtx, R.drawable.ic_baseline_favorite_24));
                }
            }
        });
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
        private MaterialButton mb_cardlist_wish;

        public CardlistViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_cardimage = itemView.findViewById(R.id.iv_item_cardlist_image);
            fab_flip = itemView.findViewById(R.id.fab_item_cardlist_flip);
            mb_cardlist_wish = itemView.findViewById(R.id.mb_cardlist_wish);
        }
    }
}
