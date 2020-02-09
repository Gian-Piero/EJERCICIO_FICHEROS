package com.example.ejercicio_ficheros;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Actividad2 extends AppCompatActivity {

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad2);
        spinner = findViewById(R.id.spinnerRecurso);

        recursos();
        cargarSpinner();
    }

    private void cargarSpinner()
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, recursos());
        spinner.setAdapter(adapter);
    }

    private ArrayList<String> recursos()
    {
        ArrayList<String> provincias = new ArrayList<String>();
        try {
            InputStream fraw = getResources().openRawResource(R.raw.provincias);
            BufferedReader br = new BufferedReader( new InputStreamReader(fraw));
            String linea = br.readLine();
            while (linea!=null){
                provincias.add(linea);
                linea = br.readLine();

            }
            fraw.close();

        }catch (Exception ex) {
            Log.e ("Ficheros", "Error al leer fichero desde recurso raw");
        }

        return provincias;
    }
}
