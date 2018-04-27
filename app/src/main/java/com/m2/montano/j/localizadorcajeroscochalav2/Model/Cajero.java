package com.m2.montano.j.localizadorcajeroscochalav2.Model;


import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by juanes on 14/5/2017.
 */

public class Cajero {
    private String nombre;
    private double latitude;
    private double longitude;
    private String direccion;
    private String horario;
    private String sitioWeb;
    private int valoracion;
    private String nombreEF;
    private int notificado;

    public Cajero(String nombre, double latitude, double longitude, String direccion, String horario, String sitioWeb, int valoracion, String nombreEF) {
        this.nombre = nombre;
        this.latitude = latitude;
        this.longitude = longitude;
        this.direccion = direccion;
        this.horario = horario;
        this.sitioWeb = sitioWeb;
        this.valoracion = valoracion;
        this.nombreEF = nombreEF;
    }

    public Cajero() {
    }

    public int getNotificado() {
        return notificado;
    }

    public void setNotificado(int notificado) {
        this.notificado = notificado;
    }

    public String getNombreEF() {
        return nombreEF;
    }

    public void setNombreEF(String nombreEF) {
        this.nombreEF = nombreEF;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("nombre", nombre);
        result.put("latitude", latitude);
        result.put("longitude", longitude);
        result.put("direccion", direccion);
        result.put("nombreEF",nombreEF);
        result.put("horario", horario);
        result.put("sitioWeb",sitioWeb);
        result.put("valoracion",valoracion);
        result.put("notificado",notificado);

        return result;
    }
}
