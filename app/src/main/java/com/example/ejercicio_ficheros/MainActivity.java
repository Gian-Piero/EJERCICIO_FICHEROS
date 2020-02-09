package com.example.ejercicio_ficheros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void irActividad1(View view) {
        Intent intent = new Intent(this, Actividad1.class);
        startActivity(intent);
    }

    public void irActividad2(View view) {
        Intent intent = new Intent(this, Actividad2.class);
        startActivity(intent);
    }

    public void irActividad3(View view) {
        Intent intent = new Intent(this, Actividad3.class);
        startActivity(intent);
    }
}
