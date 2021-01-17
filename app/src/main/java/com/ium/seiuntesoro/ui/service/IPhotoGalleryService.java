package com.ium.seiuntesoro.ui.service;

import android.content.Context;

import com.ium.seiuntesoro.ui.bean.PhotoGalleryItemBean;

import java.util.List;

public interface IPhotoGalleryService {

    List<PhotoGalleryItemBean> getListaItemsFoto(Context context);
    PhotoGalleryItemBean getSingleItemFotoById(Long id);
    List<PhotoGalleryItemBean> getSingleItemFotoByDescrizione(Context context, String str);
    String leggiDati(Context context);

}
