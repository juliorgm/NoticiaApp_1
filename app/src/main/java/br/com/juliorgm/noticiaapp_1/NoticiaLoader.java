package br.com.juliorgm.noticiaapp_1;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

class NoticiaLoader extends AsyncTaskLoader<List<Noticia>>{

    private String REQUEST_URL =
            "https://content.guardianapis.com/search?&show-tags=contributor&show-fields=thumbnail&api-key=82636ef5-b9df-4bfa-a27a-08a427d84c34";

    public NoticiaLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Noticia> loadInBackground() {
        return QueryUtils.pegaListaNoticia(REQUEST_URL);
    }
}
