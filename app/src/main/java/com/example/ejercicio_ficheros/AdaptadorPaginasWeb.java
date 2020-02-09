package com.example.ejercicio_ficheros;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorPaginasWeb extends RecyclerView.Adapter<AdaptadorPaginasWeb.myViewHolder> {


    Context context;
    List<PaginaWeb> mdata;
    OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onIrPulsado(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public AdaptadorPaginasWeb(Context context, List<PaginaWeb> mdata){
        this.context = context;
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.pagina_web, parent, false);
        return new myViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, int position) {
        holder.txtIdentificador.setText(mdata.get(position).getIdentificador() + "-");
        holder.txtNombre.setText(mdata.get(position).getNombre());
        switch (mdata.get(position).getLogo()) {
            case "bing":
                holder.logo.setImageResource(R.drawable.bing);
                break;
            case "yahoo":
                holder.logo.setImageResource(R.drawable.yahoo);
                break;
            case "google":
                holder.logo.setImageResource(R.drawable.google);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView txtNombre;
        Button btnIr;
        ImageView logo;
        TextView txtIdentificador;

        public myViewHolder(View itemView, final OnItemClickListener listener){
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            logo = itemView.findViewById(R.id.imgLogo);
            btnIr = itemView.findViewById(R.id.btnIrWeb);
            txtIdentificador = itemView.findViewById(R.id.txtIdentificador);

            btnIr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onIrPulsado(position);
                        }
                    }
                }
            });
        }
    }
}
