package com.example.mtgcardsearch.ui.setlist;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestBuilder;
import com.example.mtgcardsearch.R;
import com.example.mtgcardsearch.model.ListSearchResult;
import com.example.mtgcardsearch.model.OnBottomReachedListener;
import com.example.mtgcardsearch.model.Set;
import com.example.mtgcardsearch.model.SetSearchResult;
import com.example.mtgcardsearch.utils.GlideApp;
import com.example.mtgcardsearch.utils.SvgSoftwareLayerSetter;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class SetlistAdapter extends RecyclerView.Adapter<SetlistAdapter.SetlistViewHolder> {


    Context mCtx;
    ListSearchResult listSearchResult;
    List<Set> setList;
    OnBottomReachedListener onBottomReachedListener;

    public SetlistAdapter(Context mCtx) {
        this.mCtx = mCtx;
    }

    public void setSetSearchResult(SetSearchResult setSearchResult){
        this.setList = setSearchResult.getData();
        this.listSearchResult = setSearchResult;
    }

    public void setList(List<Set> setList) {
        this.setList = setList;
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){
        this.onBottomReachedListener = onBottomReachedListener;
    }

    @NonNull
    @Override
    public SetlistAdapter.SetlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_setlist, parent, false);

        return new SetlistAdapter.SetlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SetlistAdapter.SetlistViewHolder holder, int position) {
        Set set = setList.get(position);
        holder.tv_name_setlist.setText(set.getName());
        holder.tv_setlist_release.setText("Released at: " + set.getReleased_at().toString());

        Uri uri = Uri.parse(set.getIcon_svg_uri());
        holder.requestBuilder.load(uri).into(holder.iv_item_setlist);

        holder.card_item_setlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri_cards_page = Uri.parse(set.getSearch_uri());

                Bundle bundle = new Bundle();
                bundle.putString("query", uri_cards_page.getQueryParameter("q"));

                Navigation.findNavController(view).navigate(R.id.nav_cardlist, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (setList == null) ? 0 : setList.size();
    }

    public class SetlistViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name_setlist;
        ImageView iv_item_setlist;
        RequestBuilder<PictureDrawable> requestBuilder;
        MaterialCardView card_item_setlist;
        TextView tv_setlist_release;

        public SetlistViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name_setlist = itemView.findViewById(R.id.tv_setlist_name);
            iv_item_setlist = itemView.findViewById(R.id.iv_item_setlist);
            card_item_setlist = itemView.findViewById(R.id.card_item_setlist);
            tv_setlist_release = itemView.findViewById(R.id.tv_setlist_release);

            requestBuilder =
                    GlideApp.with(mCtx)
                            .as(PictureDrawable.class)
//                            .placeholder(R.drawable.image_loading)
                            .error(R.drawable.ic_baseline_error_24)
                            .transition(withCrossFade())
                            .listener(new SvgSoftwareLayerSetter());
        }
    }


}
