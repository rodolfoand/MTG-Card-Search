package com.example.mtgcardsearch.ui.card;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mtgcardsearch.databinding.FragmentCardBinding;
import com.example.mtgcardsearch.model.Card;
import com.example.mtgcardsearch.model.CardSearchResult;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;


public class CardFragment extends Fragment {

    private FragmentCardBinding binding;
    private CardViewModel cardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cardViewModel =
                new ViewModelProvider(this).get(CardViewModel.class);

        binding = FragmentCardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        cardViewModel.getCard(getArguments().getString("id")).observe(getViewLifecycleOwner(), new Observer<Card>() {
            @Override
            public void onChanged(Card card) {
                card.setImage_url();
                setCardImage(binding.ivCardImage, card.getImage_url());

                binding.txCardName.setText(card.getName());
                binding.txCardTypeLine.setText(card.getType_line());
                binding.txCardOracleText.setText(card.getOracle_text());

                if (card.getLoyalty() != null){
                    String s_loyalty = "Loyalty: " + card.getLoyalty();
                    binding.txCardLoyalty.setText(s_loyalty);
                } else {
                    binding.txCardLoyalty.setVisibility(View.GONE);
                }

                String s_artist = "Illustrated by " + card.getArtist();
                binding.txCardArtist.setText(s_artist);

                binding.fabCardFlip.setAlpha(0.60f);
                binding.fabCardFlip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        card.setFace_position();
                        card.setImage_url();
                        setCardImage(binding.ivCardImage, card.getImage_url());
                    }
                });

                if (!card.hasImageInCardFaces())
                    binding.fabCardFlip.setVisibility(View.INVISIBLE);
                else
                    binding.fabCardFlip.setVisibility(View.VISIBLE);

                for (Map.Entry<String, String> entry : card.getLegalities().entrySet()) {
                    addTextView(entry.hashCode()
                            , entry.getKey().toUpperCase(Locale.ROOT) + ": " + entry.getValue().toUpperCase(Locale.ROOT)
                            , binding.llCardInfo
                            , View.FOCUS_LEFT);
                }

                String labelSet = card.getSet_name() + " (" + card.getSet().toUpperCase() + ")";
                binding.txSetName.setText(labelSet);

                String labelSetLine = "#"
                        + card.getCollector_number()
                        + " · "
                        + card.getRarity().toUpperCase()
                        + " · "
                        + card.getLang().toUpperCase();
                binding.txSetline.setText(labelSetLine);

                Uri set_search_uri = Uri.parse(card.getPrints_search_uri());
                String order = set_search_uri.getQueryParameter("order");
                String unique = set_search_uri.getQueryParameter("unique");
                String q = set_search_uri.getQueryParameter("q");

                cardViewModel.getCards("false", "false"
                        , order, "1", unique, "auto", q)
                        .observe(getViewLifecycleOwner(), new Observer<CardSearchResult>() {
                            @Override
                            public void onChanged(CardSearchResult cardSearchResult) {
                                List<String> arraySetNames = cardSearchResult.getData().stream()
                                        .map(card -> card.getSet_name()
                                                + " · #" + card.getCollector_number())
                                        .collect(Collectors.toList());

                                for (String setName: arraySetNames) {
                                    addTextView(setName.hashCode()
                                            , setName
                                            , binding.llPrintList
                                            , View.FOCUS_LEFT);
                                }
                            }
                        });

            }
        });

        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public void setCardImage(ImageView iv_cardimage, String url_image){
        Glide.with(iv_cardimage.getContext())
                .load(url_image)
                .into(iv_cardimage);
    }

    public void addTextView(int id, String text, LinearLayout layout, int align){
        LinearLayout linearLayout = layout;
        TextView textView = new TextView(getActivity());

        textView.setId(id);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(layoutParams);
        textView.setText(text);
        textView.setTextAlignment(align);
        try{
            linearLayout.addView(textView);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}