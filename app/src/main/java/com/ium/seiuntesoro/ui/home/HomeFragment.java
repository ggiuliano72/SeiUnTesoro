package com.ium.seiuntesoro.ui.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ium.seiuntesoro.databinding.FragmentHomeBinding;

import java.io.IOException;
import java.io.InputStream;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final ImageView imgView = binding.imgHome;
        imgView.setImageDrawable(loadImgFromAsset("Panoramica_Cattedrale_di_Palermo.jpg", getActivity().getApplicationContext()));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private Drawable loadImgFromAsset(String imgFile, Context cntx) {
        Drawable d;

        try {

            InputStream ims = cntx.getAssets().open(imgFile);

            d = Drawable.createFromStream(ims, null);

        }
        catch(IOException ex) {
            return null;
        }

        return d;
    }
}