package com.example.mtgcardsearch.ui.cardlist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mtgcardsearch.R;
import com.example.mtgcardsearch.model.Card;
import com.example.mtgcardsearch.model.OnActiveActionModeListener;
import com.example.mtgcardsearch.model.OnBottomReachedListener;
import com.example.mtgcardsearch.model.OnSetWishListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CardlistAdapter extends RecyclerView.Adapter<CardlistAdapter.CardlistViewHolder> {

    Context mCtx;
    List<Card> cardList;
    OnBottomReachedListener onBottomReachedListener;
    List<String> wishList;
    OnSetWishListener onSetWishListener;
    Boolean isSelectableMode;
    Boolean isAlwaysSelectable;
    List<Card> selectedItemPositionsSet = new ArrayList<>();
    MutableLiveData<Integer> selectedSetSize;
    OnActiveActionModeListener onActiveActionModeListener;
    ArrayList<Uri> urisToShare = new ArrayList<>();
    boolean typeOrder;
    int layout;
    private final int LAYOUT_LINEAR = 0;
    private final int LAYOUT_GRID = 1;
    private final int LAYOUT_LINEAR_TEXT = 2;

    public CardlistAdapter(Context mCtx, int layout) {
        this.mCtx = mCtx;
        this.wishList = new ArrayList<>();
        this.isAlwaysSelectable = false;
        this.isSelectableMode = false;
        this.selectedSetSize = new MutableLiveData<>();
        this.layout = layout;
        typeOrder = false;
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

    public void setOnActiveActionMode(OnActiveActionModeListener onActiveActionModeListener) {
        this.onActiveActionModeListener = onActiveActionModeListener;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public void setTypeOrder(boolean typeOrder) {
        this.typeOrder = typeOrder;
    }

    @NonNull
    @Override
    public CardlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (layout == LAYOUT_LINEAR_TEXT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_cardlist_name, parent, false);
        } else{
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_cardlist, parent, false);
        }
        return new CardlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardlistViewHolder holder, int position) {
        Card card = cardList.get(position);
        card.initialize();

        if (wishList.indexOf(card.getId()) >= 0) card.setWhish(true);
        else card.setWhish(false);

        setCardImage(holder.iv_cardimage, card.getImage_url());

        if (layout == LAYOUT_LINEAR_TEXT) {
            String name = (card.getPrinted_name() != null)
                    ? card.getPrinted_name()
                    : card.getName();
            holder.tv_cardlist_name.setText(name);

            if (typeOrder) {
                String type = getCardType(card);
                holder.tv_cardlist_type.setText(type);
                if (position > 0) {
                    String previousType = getCardType(cardList.get(position - 1));
                    if (type.equals(previousType)) holder.tv_cardlist_type.setVisibility(View.GONE);
                    else holder.tv_cardlist_type.setVisibility(View.VISIBLE);
                } else holder.tv_cardlist_type.setVisibility(View.VISIBLE);
            } else holder.tv_cardlist_type.setVisibility(View.GONE);

        } else {

            if (!card.hasImageInCardFaces()) {
                holder.fab_flip.setVisibility(View.INVISIBLE);
            }else {
                holder.fab_flip.setVisibility(View.VISIBLE);
                holder.fab_flip.setAlpha(0.60f);

                holder.fab_flip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        card.setFace_position();
                        card.setImage_url();
                        setCardImage(holder.iv_cardimage, card.getImage_url());
                    }
                });
            }

            if (card.isWhish())
                holder.mb_cardlist_wish.setIcon(ContextCompat
                        .getDrawable(mCtx, R.drawable.ic_baseline_favorite_24));
            else
                holder.mb_cardlist_wish.setIcon(ContextCompat
                        .getDrawable(mCtx, R.drawable.ic_baseline_favorite_border_24));

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


            if(isSelectedItem(position)){
                holder.card_item_cardlist.setChecked(true);
                holder.ll_itemcard_button.setVisibility(View.GONE);
            }
            else {
                holder.card_item_cardlist.setChecked(false);
                holder.ll_itemcard_button.setVisibility(View.VISIBLE);
            }
        }

        holder.card_item_cardlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isSelectableMode && !isAlwaysSelectable){
                    Bundle bundle = new Bundle();
                    bundle.putString("id", card.getId());
                    Navigation.findNavController(view).navigate(R.id.nav_card, bundle);
                }
                else {
                    if(isSelectedItem(holder.getAdapterPosition())) {
                        removeSelectedItem(holder.getAdapterPosition());
                        holder.ll_itemcard_button.setVisibility(View.VISIBLE);
                    }
                    else {
                        Bitmap image = ((BitmapDrawable) holder.iv_cardimage.getDrawable()).getBitmap();
                        addSelectedItem(holder.getAdapterPosition(), image);
                        holder.ll_itemcard_button.setVisibility(View.GONE);
                    }
                    holder.card_item_cardlist.setChecked(!holder.card_item_cardlist.isChecked());
                }
            }
        });

        holder.card_item_cardlist.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (isSelectedItem(holder.getAdapterPosition())) {
                    removeSelectedItem(holder.getAdapterPosition());
                    holder.ll_itemcard_button.setVisibility(View.VISIBLE);
                }
                else {
                    Bitmap image = ((BitmapDrawable) holder.iv_cardimage.getDrawable()).getBitmap();
                    addSelectedItem(holder.getAdapterPosition(), image);
                    holder.ll_itemcard_button.setVisibility(View.GONE);
                }

                holder.card_item_cardlist.setChecked(!holder.card_item_cardlist.isChecked());

                return true;
            }
        });

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

    private Boolean isSelectedItem(int position){
        return selectedItemPositionsSet.contains(cardList.get(position));
    }

    private void addSelectedItem(int position, Bitmap bitmap){
        if(selectedItemPositionsSet.isEmpty() && !isAlwaysSelectable) {
            isSelectableMode = true;
            onActiveActionModeListener.onActiveActionMode(true);
        }
        selectedItemPositionsSet.add(cardList.get(position));
        selectedSetSize.setValue(selectedItemPositionsSet.size());

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(mCtx.getContentResolver()
                , bitmap
                , cardList.get(position).getName()
                , cardList.get(position).getType_line());

        urisToShare.add(Uri.parse(path));

    }

    private void removeSelectedItem(int position){
        selectedItemPositionsSet.remove(cardList.get(position));
        selectedSetSize.setValue(selectedItemPositionsSet.size());
        urisToShare.remove(position);
        if(selectedItemPositionsSet.isEmpty() && !isAlwaysSelectable){
            isSelectableMode = false;
            onActiveActionModeListener.onActiveActionMode(false);
        }
    }

    private String getCardType(Card card){
        if (card.getType_line().contains("Creature")) return "Creatures";
        if (card.getType_line().contains("Planeswalker")) return "Planeswalkers";
        if (card.getType_line().contains("Instant")
                || card.getType_line().contains("Sorcery")) return "Spells";
        if (card.getType_line().contains("Artifact")) return "Artifacts";
        if (card.getType_line().contains("Enchantment")) return "Enchantments";
        if (card.getType_line().contains("Land")) return "Lands";
        return "";
    }

    public class CardlistViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_cardimage;
        private FloatingActionButton fab_flip;
        private MaterialButton mb_itemcard_share;
        private MaterialButton mb_cardlist_wish;
        private MaterialCardView card_item_cardlist;
        private LinearLayout ll_itemcard_button;
        private TextView tv_cardlist_name;
        private TextView tv_cardlist_type;

        public CardlistViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_cardimage = itemView.findViewById(R.id.iv_item_cardlist_image);
            fab_flip = itemView.findViewById(R.id.fab_item_cardlist_flip);
            mb_itemcard_share = itemView.findViewById(R.id.mb_itemcard_share);
            mb_cardlist_wish = itemView.findViewById(R.id.mb_cardlist_wish);
            card_item_cardlist = itemView.findViewById(R.id.card_item_cardlist);
            ll_itemcard_button = itemView.findViewById(R.id.ll_itemcard_button);
            tv_cardlist_name = itemView.findViewById(R.id.tv_cardlist_name);
            tv_cardlist_type = itemView.findViewById(R.id.tv_cardlist_type);
        }
    }
}