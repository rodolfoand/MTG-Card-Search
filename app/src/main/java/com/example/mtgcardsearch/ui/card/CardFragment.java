package com.example.mtgcardsearch.ui.card;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mtgcardsearch.R;
import com.example.mtgcardsearch.databinding.FragmentCardBinding;
import com.example.mtgcardsearch.model.Card;
import com.example.mtgcardsearch.model.CardSearchResult;

import java.io.ByteArrayOutputStream;
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

        setLoading(true);

        cardViewModel.getCard(getArguments().getString("id")).observe(getViewLifecycleOwner(), new Observer<Card>() {
            @Override
            public void onChanged(Card card) {
                card.setImage_url();
                setCardImage(binding.ivCardImage, card.getImage_url());

                if (card.getPrinted_name() == null)
                    binding.txCardName.setText(card.getName());
                else
                    binding.txCardName.setText(card.getPrinted_name());

                if (card.getPrinted_type_line() == null)
                    binding.txCardTypeLine.setText(card.getType_line());
                else
                    binding.txCardTypeLine.setText(card.getPrinted_type_line());

                if (card.getPrinted_text() != null) {
                    binding.txCardOracleText.setText(card.getPrinted_text());
                    binding.btCarddetailOracle.setVisibility(View.VISIBLE);
                } else if (card.getOracle_text() != null) {
                    binding.txCardOracleText.setText(card.getOracle_text());
                } else {
                    binding.txCardOracleText.setVisibility(View.GONE);
                    binding.lineCarddetailOracleText.setVisibility(View.GONE);
                }

                if (card.getFlavor_text() != null)
                    binding.txCardDetailFlavor.setText(card.getFlavor_text());
                else {
                    binding.txCardDetailFlavor.setVisibility(View.GONE);
                    binding.lineCarddetailFlavor.setVisibility(View.GONE);
                }

                String s_subline = "";
                if (card.getLoyalty() != null){
                    s_subline = "Loyalty: " + card.getLoyalty();
                } else if (card.getPower() != null && card.getToughness() != null){
                    s_subline = card.getPower() + "/" + card.getToughness();
                }

                if (!s_subline.isEmpty()) {
                    binding.txCardDetailSubline.setText(s_subline);
                } else {
                    binding.txCardDetailSubline.setVisibility(View.GONE);
                    binding.lineCarddetailSubline.setVisibility(View.GONE);
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

                if (card.getLang().equals("en"))
                    binding.mbCardDetailEn.setVisibility(View.GONE);

                Uri set_search_uri = Uri.parse(card.getPrints_search_uri());
                String order = set_search_uri.getQueryParameter("order");
                String unique = set_search_uri.getQueryParameter("unique");
                String dir = set_search_uri.getQueryParameter("dir");
                String q = set_search_uri.getQueryParameter("q");

                binding.mcCardDetailPrints.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("query", q);
                        bundle.putString("unique", unique);
                        bundle.putString("dir", dir);
                        Navigation.findNavController(view).navigate(R.id.nav_cardlist, bundle);
                    }
                });

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
                binding.mbCardDetailEn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String query = card.getName() + " set:" + card.getSet() + " rarity:" + card.getRarity();
                        Bundle bundle = new Bundle();
                        bundle.putString("query", query);
                        Navigation.findNavController(view).navigate(R.id.nav_cardlist, bundle);
                    }
                });

                binding.btCarddetailOracle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        binding.txCardOracleText.setText(card.getOracle_text());
                        binding.btCarddetailPrinted.setVisibility(View.VISIBLE);
                        binding.btCarddetailOracle.setVisibility(View.GONE);
                    }
                });
                binding.btCarddetailPrinted.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        binding.txCardOracleText.setText(card.getPrinted_text());
                        binding.btCarddetailPrinted.setVisibility(View.GONE);
                        binding.btCarddetailOracle.setVisibility(View.VISIBLE);
                    }
                });

                binding.mbCardDetailShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Bitmap image = ((BitmapDrawable) binding.ivCardImage.getDrawable()).getBitmap();
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                        String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), image, card.getName(), card.getType_line());


                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
                        shareIntent.setType("image/jpeg");
                        startActivity(Intent.createChooser(shareIntent, null));
                    }
                });

                setLoading(false);
            }
        });

        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void setLoading(boolean loading){
        if (loading){
            binding.svCardDetail.setVisibility(View.GONE);
            binding.pbCardDetail.setVisibility(View.VISIBLE);
        } else {
            binding.svCardDetail.setVisibility(View.VISIBLE);
            binding.pbCardDetail.setVisibility(View.GONE);
        }
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