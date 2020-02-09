package com.example.ejercicio_ficheros;

public class PaginaWeb {

    private String nombre;
    private String enlace;
    private String logo;
    private String identificador;

    public PaginaWeb(String nombre, String enlace, String logo, String identificador)
    {
        this.nombre = nombre;
        this.enlace = enlace;
        this.logo = logo;
        this.identificador = identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEnlace() {
        return enlace;
    }

    public String getLogo() {
        return logo;
    }

    public String getIdentificador() {
        return identificador;
    }

    
}
