package com.example.wordscramble.models;

public class Palabra {
    //declaración de campos
    private int idPalabra;
    private String palabra;
    private int ponderacion;
    private int idAsignatura;
    private String descripcion;

    //constructores

    public Palabra() {
    }

    public Palabra(int idPalabra){
        this.idPalabra = idPalabra;
    }

    public Palabra(int idPalabra, String palabra, int ponderacion, int idAsignatura) {
        this.idPalabra = idPalabra;
        this.palabra = palabra;
        this.ponderacion = ponderacion;
        this.idAsignatura = idAsignatura;
    }

    public Palabra(String palabra, int ponderacion) {
        this.palabra = palabra;
        this.ponderacion = ponderacion;
    }

    public Palabra(int idPalabra, String palabra, String descripcion, int ponderacion) {
        this.idPalabra = idPalabra;
        this.palabra = palabra;
        this.descripcion = descripcion;
        this.ponderacion = ponderacion;
    }

    //getters and setters
    public int getIdPalabra() {
        return idPalabra;
    }

    public void setIdPalabra(int idPalabra) {
        this.idPalabra = idPalabra;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPonderacion() {
        return ponderacion;
    }

    public void setPonderacion(int ponderacion) {
        this.ponderacion = ponderacion;
    }

    public int getIdAsignatura() {
        return idAsignatura;
    }

    public void setIdAsignatura(int idAsignatura) {
        this.idAsignatura = idAsignatura;
    }
}
