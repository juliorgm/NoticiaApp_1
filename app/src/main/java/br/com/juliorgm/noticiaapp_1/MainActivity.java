package br.com.juliorgm.noticiaapp_1;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<List<Noticia>> {

    private NoticiaAdapter mAdapter;
    private RecyclerView mRecyclerNoticias;
    private TextView mTextError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextError = findViewById(R.id.txtMensagemErro);

        mRecyclerNoticias = findViewById(R.id.recyclerNoticias);
        mRecyclerNoticias.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerNoticias.setLayoutManager(layoutManager);
        mAdapter = new NoticiaAdapter(this,new ArrayList<Noticia>());
        mRecyclerNoticias.setAdapter(mAdapter);
        RecyclerView.ItemAnimator i = new DefaultItemAnimator();
        mRecyclerNoticias.setItemAnimator(i);

      initLoader();
      cliqueLista();
    }

    private void initLoader(){
        try{
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(0, null, this);
        }catch (Exception e){
            Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
            alterarVisibilidadeViews(R.string.messagem_erro_rede);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initLoader();
    }

    private void setupRecyclerAdapter(List<Noticia> noticiaList) {
        mAdapter = new NoticiaAdapter(this,noticiaList);
        mRecyclerNoticias.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public Loader<List<Noticia>> onCreateLoader(int id, Bundle args) {
        return new NoticiaLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Noticia>> loader, List<Noticia> data) {
        setupRecyclerAdapter(data);
        if(data == null) {
            alterarVisibilidadeViews(R.string.messagem_erro_pesquisa);
        }else if(data.size()==0){
            alterarVisibilidadeViews(R.string.messagem_erro_pesquisa);
        }
        else {
            mRecyclerNoticias.setVisibility(View.VISIBLE);
            mTextError.setVisibility(View.GONE);
        }
    }

    private void alterarVisibilidadeViews(int messagem_erro_pesquisa) {
        mRecyclerNoticias.setVisibility(View.GONE);
        mTextError.setVisibility(View.VISIBLE);
        mTextError.setText(messagem_erro_pesquisa);
    }

    @Override
    public void onLoaderReset(Loader<List<Noticia>> loader) {

    }

    private void cliqueLista(){
        mAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Noticia noticia = mAdapter.getNoticia(position);
                String url = noticia.getmUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
                startActivity(intent);
                return false;
            }
        });
        return super.onOptionsItemSelected(item);
    }
}
