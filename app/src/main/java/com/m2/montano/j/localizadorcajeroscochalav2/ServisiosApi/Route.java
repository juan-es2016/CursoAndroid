package com.m2.montano.j.localizadorcajeroscochalav2.ServisiosApi;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mai Thanh Hiep on
 */
public class Route {
    public Distance distance;
    public Duration duration;
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;

    public List<LatLng> points;
    //public ArrayList<String> pasos;
    public ArrayList<Indicacion> listpasos;
}
