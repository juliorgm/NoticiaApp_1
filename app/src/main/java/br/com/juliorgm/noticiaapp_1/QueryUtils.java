package br.com.juliorgm.noticiaapp_1;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

class QueryUtils {

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.d("Error response code: ", String.valueOf(urlConnection.getResponseCode()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {

                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Noticia> jsonParaList(String stringJSON) {

        List<Noticia> listaNoticia = new ArrayList<>();

        try {

            JSONObject baseJsonResponse = new JSONObject(stringJSON);
            JSONObject response = baseJsonResponse.getJSONObject("response");
            JSONArray resultsArray = response.getJSONArray("results");

            for (int i = 0; i < resultsArray.length(); i++) {

                JSONObject currentResults = resultsArray.getJSONObject(i);

                String titulo = currentResults.getString("webTitle");
                String secao = currentResults.getString("sectionName");
                String data = currentResults.getString("webPublicationDate");
                String url = currentResults.getString("webUrl");
                String thumbnail = currentResults.getJSONObject("fields").getString("thumbnail");
                JSONArray arrayAutor = currentResults.getJSONArray("tags");
                String autor;
                if (arrayAutor.length()!= 0) {
                    JSONObject currenttagsauthor = arrayAutor.getJSONObject(0);
                    autor = currenttagsauthor.getString("webTitle");
                }else{
                    autor = "Autor desconhecido";
                }

                Noticia noticia = new Noticia(titulo, secao, data, autor, url,thumbnail);

                listaNoticia.add(noticia);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listaNoticia;
    }
    public static List<Noticia> pegaListaNoticia(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonParaList(jsonResponse);
    }
}
