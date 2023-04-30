package com.example.wordscramble.models;

import org.jetbrains.annotations.NotNull;

public class Asignatura {
    private int asigId;
    private String asignombre;

    //constructores
    public Asignatura() {
    }

    public Asignatura(@NotNull int asigId, @NotNull String asignombre) {
        this.asigId = asigId;
        this.asignombre = asignombre;
    }

    //descriptores de acceso

    public int getAsigId() {
        return asigId;
    }

    public void setAsigId(int asigId) {
        this.asigId = asigId;
    }

    public String getAsignombre() {
        return asignombre;
    }

    public void setAsignombre(String asignombre) {
        this.asignombre = asignombre;
    }
}//fin de la clase asignatura
