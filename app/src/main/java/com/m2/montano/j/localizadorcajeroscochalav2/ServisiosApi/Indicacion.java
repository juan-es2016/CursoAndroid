package com.m2.montano.j.localizadorcajeroscochalav2.ServisiosApi;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by juanes on 8/6/2017.
 */

public class Indicacion {
    String distancia;
    String duracion;
    String instruccion;
    String maniobra;
    public List<LatLng> points;

    public Indicacion(String distancia, String duracion, String instruccion, String maniobra, List<LatLng> points) {
        this.distancia = distancia;
        this.duracion = duracion;
        this.instruccion = instruccion;
        this.maniobra = maniobra;
        this.points = points;
    }

    public List<LatLng> getPoints() {
        return points;
    }

    public void setPoints(List<LatLng> points) {
        this.points = points;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getInstruccion() {
        return instruccion;
    }

    public void setInstruccion(String instruccion) {
        this.instruccion = instruccion;
    }

    public String getManiobra() {
        return maniobra;
    }

    public void setManiobra(String maniobra) {
        this.maniobra = maniobra;
    }
}
