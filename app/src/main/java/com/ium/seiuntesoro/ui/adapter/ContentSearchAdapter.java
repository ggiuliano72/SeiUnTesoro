package com.ium.seiuntesoro.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ium.seiuntesoro.databinding.ItemListContentBinding;
import com.ium.seiuntesoro.ui.bean.PhotoGalleryItemBean;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ContentSearchAdapter extends RecyclerView.Adapter<ContentSearchAdapter.ViewHolder> {

    private final List<PhotoGalleryItemBean> mValues;
    private final View.OnClickListener mOnClickListener;
    private final Context mContext;


    public ContentSearchAdapter(List<PhotoGalleryItemBean> items,
                                View.OnClickListener onClickListener,
                                Context context) {
        mValues = items;
        mOnClickListener = onClickListener;
        mContext = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        ItemListContentBinding binding =
                ItemListContentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.mContentView.setText(mValues.get(position).getTitolo());
        holder.mImageView.setImageDrawable(this.loadImgFromAsset(mValues.get(position).getPathImg(), mContext));
        holder.mTextPosizione.setText(mValues.get(position).getPosizione().toString());
        holder.itemView.setTag(mValues.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);

    }

    @Override
    public int getItemCount() {
        return mValues.size();
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

    /* --- ViewHolder ---- */
    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mContentView;
        final ImageView mImageView;
        final TextView mTextPosizione;

        ViewHolder(ItemListContentBinding binding) {
            super(binding.getRoot());
            mContentView = binding.content;
            mImageView = binding.imageView;
            mTextPosizione = binding.posizione;
        }

    }
}
