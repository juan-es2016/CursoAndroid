package com.m2.montano.j.localizadorcajeroscochalav2.ServisiosApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Mai Thanh Hiep on
 */
public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void BuscarDireccionExitoso(List<Route> route);
    void resultadoNoValido(String status);
}
