package com.example.ejercicio_ficheros;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Actividad1 extends AppCompatActivity {

    private EditText txtAgregar;
    private TextView txtVer;

    // Variables para los permisos
    private static final int SOLICITUD_PERMISO_WRITE_SD = 0;
    private static final int SOLICITUD_PERMISO_READ_SD = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad1);
        txtAgregar = findViewById(R.id.editTextAgregar);
        txtVer = findViewById(R.id.txtVer);
    }

    public void agregarInterno(View view) {
        try{
            OutputStreamWriter osw= new OutputStreamWriter(openFileOutput("fichero_interno.txt", Context.MODE_APPEND));
            osw.write(txtAgregar.getText().toString() + "\n");
            txtAgregar.setText("");
            osw.close();
            Toast.makeText(this, "Texto guardado en FICH. INT.", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Log.e ("Ficheros", "ERROR!! al escribir fichero en memoria interna");
        }
    }

    public void leerInterno(View view) {
        try{
            BufferedReader fin = new BufferedReader(new InputStreamReader(openFileInput("fichero_interno.txt")));
            String todo = "";
            String texto = fin.readLine();
            while (texto!=null) {
                todo = todo + texto + "\n";
                texto = fin.readLine();
            }
            fin.close();
            txtVer.setText(todo);
        }
        catch (Exception ex){
            Log.e("Ficheros", "Error al leer fichero desde memoria interna");
        }
    }

    public void leerRecurso(View view) {
        try{
            InputStream fraw = getResources().getAssets().open("recursos/recurso_prueba.txt");
            BufferedReader brin = new BufferedReader(new InputStreamReader(fraw));
            String todo = "";
            String linea = brin.readLine();
            while (linea != null) {
                todo = todo + linea + "\n";
                linea = brin.readLine();
            }
            fraw.close();
            txtVer.setText(todo);
        }
        catch (Exception ex){
            Log.e("Ficheros", "Error al leer fichero de recursos en Assets");
        }

    }

    public void borrarFichInterno(View view) {
        try{
            OutputStreamWriter osw= new OutputStreamWriter(openFileOutput("fichero_interno.txt", Context.MODE_APPEND));
            osw.write("");
            osw.close();
            Toast.makeText(this, "Fichero interno borrado", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            Log.e ("Ficheros", "ERROR!! al escribir fichero en memoria interna");
        }
    }





    public void agregarExterno(View view) {
        if (comprobarPermisos()) {
            Toast.makeText(this,
                    "Tenemos permisos para escribir", Toast.LENGTH_SHORT).show();
            if (sdDisponible()) {
                escribirEnSD();
            } else
                Toast.makeText(this,
                        "Tarjeta NO lista para poder escribir", Toast.LENGTH_SHORT).show();
        } else {
            solicitarPermiso(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    "Sin este permiso no se puede ESCRIBIR en el dispositivo externo",
                    SOLICITUD_PERMISO_WRITE_SD, this);
        }
    }

    public void leerExterno(View view) {
        if (comprobarPermisos()) {
            Toast.makeText(this,
                    "Tenemos permisos para leer", Toast.LENGTH_SHORT).show();
            if (sdDisponible()) {
                leerDeSD();
            } else
                Toast.makeText(this, "Tarjeta NO lista para poder leer", Toast.LENGTH_SHORT).show();
        } else {
            solicitarPermiso(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    "Sin este permiso no se puede LEER en el dispositivo externo",
                    SOLICITUD_PERMISO_WRITE_SD, this);
        }
    }

    public void borrarFichExterno(View view) {
        if (comprobarPermisos()) {
            Toast.makeText(this,
                    "Tenemos permisos para borrar", Toast.LENGTH_SHORT).show();
            if (sdDisponible()) {
                borrarEnSD();
            } else
                Toast.makeText(this, "Tarjeta NO lista para poder borrar", Toast.LENGTH_SHORT).show();
        } else {
            solicitarPermiso(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    "Sin este permiso no se puede LEER en el dispositivo externo",
                    SOLICITUD_PERMISO_WRITE_SD, this);

        }
    }


    private boolean comprobarPermisos (){
        //Comprobamos que tenemos permiso para realizar la operación.
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        else {
            return false;
        }
    }

    private boolean sdDisponible () {
        boolean sdDisponible = false;
        boolean sdAccesoEscritura = false;
        //Comprobamos el estado de la memoria externa
        String estado = Environment.getExternalStorageState();
        Log.i("Memoria", estado);
        if (estado.equals(Environment.MEDIA_MOUNTED)) {
            sdDisponible = true;
            sdAccesoEscritura = true;
        } else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            sdDisponible = true;
            sdAccesoEscritura = false;
        } else {
            sdDisponible = false;
            sdAccesoEscritura = false;
        }
        if (sdDisponible) Toast.makeText(getApplicationContext(),
                "Tengo Tarjeta SD", Toast.LENGTH_SHORT).show();

        if (sdAccesoEscritura) Toast.makeText(getApplicationContext(),
                "La tarjeta SD es escribible", Toast.LENGTH_SHORT).show();

        if (sdDisponible && sdAccesoEscritura)
            return true;
        else
            return false;
    }

    private void escribirEnSD (){
        try {
            File ruta_sd = Environment.getExternalStorageDirectory();
            File f = new File(ruta_sd.getAbsolutePath(), "prueba_sd.txt");
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(f));
            osw.write(txtAgregar.getText().toString()+"\n");
            osw.close();
            Log.i("Ficheros", "fichero escrito correctamente");
        } catch (Exception ex) {
            Log.e("Ficheros", "Error al escribir fichero en tarjeta SD");
        }
    }

    private void borrarEnSD (){
        try {
            File ruta_sd = Environment.getExternalStorageDirectory();
            File f = new File(ruta_sd.getAbsolutePath(), "prueba_sd.txt");
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(f));
            osw.write("");
            osw.close();
            Log.i("Ficheros", "fichero borrado correctamente");
        } catch (Exception ex) {
            Log.e("Ficheros", "Error al borrar fichero en tarjeta SD");
        }
    }

    private void leerDeSD(){
        try {File ruta_sd = Environment.getExternalStorageDirectory();
            File f = new File(ruta_sd.getAbsolutePath(), "prueba_sd.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String texto = "";
            String linea = br.readLine();
            while (linea != null) {
                texto = texto + linea + "\n";
                linea = br.readLine();
            }
            br.close();
            Log.i("Ficheros", texto);
            txtVer.setText(texto);
        } catch (Exception ex) {
            Log.e("Ficheros", "ERROR!! en la lectura del fichero en SD");
        }
    }

    private static void solicitarPermiso (final String permiso,
                                          String justificacion,
                                          final int requestCode,
                                          final Actividad1 actividad){

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                actividad, permiso)) {
            //Informamos al usuario para qué y por qué
            // se necesitan los permisos
            new AlertDialog.Builder(actividad).setTitle("Solicitud de permiso")
                    .setMessage(justificacion).setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(actividad,
                                    new String[]{permiso}, requestCode);
                        }
                    }).show();
        }
        else {
            //Muestra el cuadro de dialogo para la solicitud de permisos y
            // registra el permiso según respuesta del usuario
            ActivityCompat.requestPermissions(actividad,
                    new String[]{permiso}, requestCode);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == SOLICITUD_PERMISO_WRITE_SD){
            if (grantResults.length >=1 &&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                sdDisponible();
                escribirEnSD();
            }
            else {
                Toast.makeText(this,"No se puede escribir en la memoria externa",
                        Toast.LENGTH_LONG ).show();
            }
        }
        else if (requestCode == SOLICITUD_PERMISO_READ_SD){
            if (grantResults.length == 1 &&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                leerDeSD();
            }
            else {
                Toast.makeText(this,"No se puede leer en la memoria externa",
                        Toast.LENGTH_LONG ).show();
            }
        }
    }


}
