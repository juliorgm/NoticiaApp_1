package br.com.juliorgm.noticiaapp_1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class NoticiaAdapter extends RecyclerView.Adapter<NoticiaAdapter.ViewHolder>{

    private Context mContext;
    private List<Noticia> mListaNoticia;
    private static ItemClickListener sItemClickListener;

    public NoticiaAdapter(Context mContext, List<Noticia> mListaMusica) {
        this.mContext = mContext;
        this.mListaNoticia = mListaMusica;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_noticia,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Noticia noticia = mListaNoticia.get(position);

        holder.mTitulo.setText(noticia.getmTitulo());
        holder.mSecao.setText(noticia.getmSecao());
        holder.mAutor.setText(noticia.getmAutor());
        holder.mData.setText(noticia.getmData());

        if (noticia.getThumbnail().equals("logo"))Glide.with(mContext).load(R.drawable.logo).into(holder.mThumbnail);
        else Glide.with(mContext).load(noticia.getThumbnail()).into(holder.mThumbnail);
    }

    @Override
    public int getItemCount() {
        return mListaNoticia.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitulo, mAutor, mData,mSecao;
        private ImageView mThumbnail;

        private ViewHolder(View itemView) {
            super(itemView);
            mTitulo = itemView.findViewById(R.id.txtTitulo);
            mAutor = itemView.findViewById(R.id.txtAutor);
            mData = itemView.findViewById(R.id.txtData);
            mSecao = itemView.findViewById(R.id.txtSecao);
            mThumbnail = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(sItemClickListener != null) {
                sItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener){
        sItemClickListener = itemClickListener;
    }

    public Noticia getNoticia(int position){
        return mListaNoticia.get(position);
    }
}
