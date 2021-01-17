package com.ium.seiuntesoro.ui.service;

import android.content.Context;


import com.ium.seiuntesoro.ui.bean.PhotoGalleryItemBean;
import com.ium.seiuntesoro.ui.bean.PosizioneItemBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PhotoGalleryService implements IPhotoGalleryService {

    @Override
    public List<PhotoGalleryItemBean> getListaItemsFoto(Context context) {
        String jsonString = this.leggiDati(context);

        return this.getDati(jsonString);
    }

    @Override
    public PhotoGalleryItemBean getSingleItemFotoById(Long id) {
        return null;
    }

    @Override
    public List<PhotoGalleryItemBean> getSingleItemFotoByDescrizione(Context context, String str) {
        List<PhotoGalleryItemBean> lista = new ArrayList<PhotoGalleryItemBean>();
        List<PhotoGalleryItemBean> lstResult = new ArrayList<PhotoGalleryItemBean>();
        String jsonString = this.leggiDati(context);
        lista = this.getDati(jsonString);

        for ( PhotoGalleryItemBean item: lista ) {
            //Ricerco su titolo e descrizione
            if (item.getTitolo().contains(str) || item.getDescrizione().contains(str)){
                lstResult.add(item);
            }
        }
        return lstResult;
    }


    @Override
    public String leggiDati(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("photoitem.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return json;
    }

    private List<PhotoGalleryItemBean> getDati(String datiJson){
        List<PhotoGalleryItemBean> lst = new ArrayList<PhotoGalleryItemBean>();
        try {
            JSONObject object = new JSONObject(datiJson);
            JSONArray array = object.getJSONArray("dati");
            for (int i = 0; i < array.length(); i++)  {
                PhotoGalleryItemBean pgItem = new PhotoGalleryItemBean();
                JSONObject mainObject = array.getJSONObject(i);

                pgItem.setId(Long.parseLong(mainObject.get("id").toString()));
                pgItem.setTitolo(mainObject.get("titolo").toString());
                pgItem.setDescrizione(mainObject.get("descrizione").toString());
                pgItem.setPathImg(mainObject.get("imgfile").toString());
                pgItem.setPathAudio(mainObject.get("audiofile").toString());

                PosizioneItemBean pos = new PosizioneItemBean();
                JSONArray arrayPosizione = mainObject.getJSONArray("posizione");
                JSONObject objPosizione = arrayPosizione.getJSONObject(0);

                pos.setNrStanza(Long.parseLong(objPosizione.get("stanza").toString()));
                pos.setNrTeca(Long.parseLong(objPosizione.get("teca").toString()));
                pos.setPosInTeca(Long.parseLong(objPosizione.get("pos").toString()));

                pgItem.setPosizione(pos);
                lst.add(pgItem);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lst;
    }
}
