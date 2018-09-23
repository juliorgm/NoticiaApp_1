package br.com.juliorgm.noticiaapp_1;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import java.util.List;

class NoticiaLoader extends AsyncTaskLoader<List<Noticia>>{
    private final String mApiKey = BuildConfig.ApiKey;
    private final String REQUEST_URL ="https://content.guardianapis.com/search?";

    public NoticiaLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Noticia> loadInBackground() {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String minNoticia = sharedPrefs.getString(getContext().getString(R.string.settings_num_noticias_key),getContext().getString(R.string.settings_num_noticias_default));

        String secao = sharedPrefs.getString(getContext().getString(R.string.secao_key),getContext(). getString(R.string.secao_todas_value));

        Uri baseUri = Uri.parse(REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("show-fields", "thumbnail");
        uriBuilder.appendQueryParameter("page-size", minNoticia);
        if (!secao.equals(getContext().getString(R.string.secao_todas_value))) {
            uriBuilder.appendQueryParameter("section", secao);
        }
        uriBuilder.appendQueryParameter("api-key", mApiKey);

        String uri =  uriBuilder.toString();
        return QueryUtils.pegaListaNoticia(uri);
    }
}
