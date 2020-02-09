package com.example.ejercicio_ficheros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Actividad3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad3);

        // setup reclycerview with adapter
        RecyclerView recyclerView = findViewById(R.id.rv_lista_webs);

        final AdaptadorPaginasWeb adaptador = new AdaptadorPaginasWeb(this, cargarPaginas());
        recyclerView.setAdapter(adaptador);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adaptador.setOnItemClickListener(new AdaptadorPaginasWeb.OnItemClickListener() {
            @Override
            public void onIrPulsado(int position) {
                Uri uri = Uri.parse(adaptador.mdata.get(position).getEnlace());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }

    private ArrayList<PaginaWeb> cargarPaginas()
    {
        ArrayList<PaginaWeb> paginas = new ArrayList<PaginaWeb>();

        try{
            InputStream fraw = getResources().getAssets().open("recursos/paginas_webs.txt");
            BufferedReader brin = new BufferedReader(new InputStreamReader(fraw));
            String linea = brin.readLine();
            while (linea != null) {
                String [] trozos = linea.split(";");
                paginas.add(new PaginaWeb(trozos[0].trim(), trozos[1].trim(), trozos[2].trim(), trozos[3].trim()));
                linea = brin.readLine();
            }
            fraw.close();
        }
        catch (Exception ex){
            Log.e("Ficheros", "Error al leer fichero de recursos en Assets");
        }

        return paginas;
    }


}
