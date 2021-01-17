package com.ium.seiuntesoro.ui.photogallery;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.ium.seiuntesoro.databinding.FragmentItemDetailBinding;
import com.ium.seiuntesoro.ui.bean.PhotoGalleryItemBean;

import java.io.IOException;
import java.io.InputStream;

import com.ium.seiuntesoro.R;


public class ItemDetailFragment extends Fragment {

    /**
     * Argomenti passati dal padre
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_ITEM_TITOLO = "item_titolo" ;
    public static final String ARG_ITEM_DESCRIZIONE = "item_descrizione" ;
    public static final String ARG_ITEM_IMG = "item_img" ;
    public static final String ARG_ITEM_AUDIOSRC = "item_audio" ;

    private PhotoGalleryItemBean mItem;
    private FragmentItemDetailBinding binding;

    int flagAudioButton = 0;

    public ItemDetailFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = new PhotoGalleryItemBean();
            mItem.setId(Long.parseLong(getArguments().getString(ARG_ITEM_ID)));
            mItem.setTitolo(getArguments().getString(ARG_ITEM_TITOLO));
            mItem.setDescrizione(getArguments().getString(ARG_ITEM_DESCRIZIONE));
            mItem.setPathImg(getArguments().getString(ARG_ITEM_IMG));
            mItem.setPathAudio(getArguments().getString(ARG_ITEM_AUDIOSRC));
        }
        flagAudioButton = R.drawable.ic_play;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        binding = FragmentItemDetailBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        ImageView imgAudio = binding.imageButton;

        imgAudio.setImageResource(flagAudioButton);

        MediaPlayer mp=new MediaPlayer();
        try {
            mp.setDataSource(getActivity().getApplicationContext().getAssets().openFd(mItem.getPathAudio()).getFileDescriptor());
            mp.setVolume(100, 100);
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    flagAudioButton = R.drawable.ic_play;
                    imgAudio.setImageResource(flagAudioButton);
                }
            });

            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }


        imgAudio.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                if (flagAudioButton == R.drawable.ic_play) {
                    Log.println(Log.INFO, "AUDIO", "Audio play: " + mItem.getPathAudio());
                    mp.start();
                    imgAudio.setImageResource(R.drawable.ic_stop);
                    flagAudioButton = R.drawable.ic_stop;

                } else  if (flagAudioButton == R.drawable.ic_stop) {
                    Log.println(Log.INFO, "AUDIO", "Audio stop: " + mItem.getPathAudio());
                    mp.stop();
                    try {
                        mp.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imgAudio.setImageResource(R.drawable.ic_play);
                    flagAudioButton = R.drawable.ic_play;
                }
            }
        });

        CollapsingToolbarLayout toolbarLayout = rootView.findViewById(R.id.toolbar_layout);

        if (mItem != null) {
            TextView textView = binding.itemDetail;
            ImageView imageView = binding.detailImageView;
            textView.setText(mItem.getDescrizione());
            imageView.setImageDrawable(loadImgFromAsset(mItem.getPathImg()));
            if (toolbarLayout != null) {
                toolbarLayout.setTitle(mItem.getTitolo());
            }
        }

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /* Carica l'immaggine da Assets */
    private Drawable loadImgFromAsset(String imgFile) {
        Drawable d;

        try {

            InputStream ims = getActivity().getApplicationContext().getAssets().open(imgFile);

            d = Drawable.createFromStream(ims, null);

        }
        catch(IOException ex) {
            return null;
        }

        return d;
    }
}