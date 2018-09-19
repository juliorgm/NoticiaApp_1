package br.com.juliorgm.noticiaapp_1;

public class Noticia {
    private String mTitulo;
    private String mSecao;
    private String mData;
    private String mAutor;
    private String mUrl;
    private String thumbnail;

    public Noticia(String mTitulo, String mSecao, String mData, String mAutor, String mUrl,String thumbnail) {
        this.mTitulo = mTitulo;
        this.mSecao = mSecao;
        this.mData = mData;
        this.mAutor = mAutor;
        this.mUrl = mUrl;
        this.thumbnail = thumbnail;
    }

    public String getmTitulo() {
        return mTitulo;
    }

    public String getmSecao() {
        return mSecao;
    }

    public String getmData() {
        return mData;
    }

    public String getmAutor() {
        return mAutor;
    }

    public String getmUrl() {
        return mUrl;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
