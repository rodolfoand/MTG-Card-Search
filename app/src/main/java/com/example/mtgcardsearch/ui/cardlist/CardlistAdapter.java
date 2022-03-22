package com.example.mtgcardsearch.ui.cardlist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mtgcardsearch.R;
import com.example.mtgcardsearch.model.Card;
import com.example.mtgcardsearch.model.ListSearchResult;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class CardlistAdapter extends RecyclerView.Adapter<CardlistAdapter.CardlistViewHolder> {

    Context mCtx;
    ListSearchResult listSearchResult;
    List<Card> cardList;

    public CardlistAdapter(Context mCtx, ListSearchResult listSearchResult) {
        this.mCtx = mCtx;
        this.cardList = listSearchResult.getData();
        this.listSearchResult = listSearchResult;
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
//        holder.text_cardname.setText(card.getName());


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

//        if (listSearchResult.getTotal_cards() > cardList.size()
//                && position == cardList.size() - 1)
            holder.bt_search_more.setVisibility(View.VISIBLE);

        holder.bt_search_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                URI dec = null;
                try {
                    dec = new URI(listSearchResult.getNext_page());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                Log.d("URL_decode", dec.getQuery().replace("format=", ""));
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public void setCardImage(ImageView iv_cardimage, String url_image){
        Glide.with(iv_cardimage.getContext())
                .load(url_image)
                .into(iv_cardimage);
    }

    public class CardlistViewHolder extends RecyclerView.ViewHolder {

        private TextView text_cardname;
        private ImageView iv_cardimage;
        private FloatingActionButton fab_flip;
        private Button bt_search_more;

        public CardlistViewHolder(@NonNull View itemView) {
            super(itemView);
//            text_cardname = itemView.findViewById(R.id.text_cardname);
            iv_cardimage = itemView.findViewById(R.id.iv_cardimage);
            fab_flip = itemView.findViewById(R.id.fab_flip);
            bt_search_more = itemView.findViewById(R.id.bt_search_more);
        }
    }
}
