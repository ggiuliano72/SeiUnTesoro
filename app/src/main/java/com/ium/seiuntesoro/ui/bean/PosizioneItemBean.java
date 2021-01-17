package com.ium.seiuntesoro.ui.bean;

public class PosizioneItemBean {
    private Long nrStanza;
    private Long nrTeca;
    private Long posInTeca;

    public Long getNrStanza() {
        return nrStanza;
    }

    public void setNrStanza(Long nrStanza) {
        this.nrStanza = nrStanza;
    }

    public Long getNrTeca() {
        return nrTeca;
    }

    public void setNrTeca(Long nrTeca) {
        this.nrTeca = nrTeca;
    }

    public Long getPosInTeca() {
        return posInTeca;
    }

    public void setPosInTeca(Long posInTeca) {
        this.posInTeca = posInTeca;
    }

    @Override
    public String toString() {
        return  "Stanza " + nrStanza +
                ", Teca " + nrTeca +
                ", Posizione " + posInTeca;
    }
}
