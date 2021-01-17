package com.ium.seiuntesoro.ui.bean;

public class PhotoGalleryItemBean {

    private Long id;
    private String titolo;
    private String descrizione;
    private String pathImg;
    private String pathAudio;
    private PosizioneItemBean posizione;

    public PosizioneItemBean getPosizione() {
        return posizione;
    }

    public void setPosizione(PosizioneItemBean posizione) {
        this.posizione = posizione;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getPathImg() {
        return pathImg;
    }

    public void setPathImg(String pathImg) {
        this.pathImg = pathImg;
    }

    public String getPathAudio() {
        return pathAudio;
    }

    public void setPathAudio(String pathAudio) {
        this.pathAudio = pathAudio;
    }

    

    @Override
    public String toString() {
        return "PhotoGalleryItemBean{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", pathImg='" + pathImg + '\'' +
                ", pathAudio='" + pathAudio + '\'' +
                '}';
    }
}
