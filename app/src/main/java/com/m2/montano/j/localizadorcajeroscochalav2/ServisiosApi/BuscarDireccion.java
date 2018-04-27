package com.m2.montano.j.localizadorcajeroscochalav2.ServisiosApi;

import android.os.AsyncTask;
import android.text.Html;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mai Thanh Hiep on 4/3/2016.
 */
public class BuscarDireccion {
    private static final String DIRECTION_URL_API = "https://maps.googleapis.com/maps/api/directions/json?";
    private static final String GOOGLE_API_KEY = "AIzaSyD2zlkNaVpxleVy5OrlaqqA3mrczKESxwU";
    private DirectionFinderListener listener;
    private String origin;
    private String destination;
    private boolean decision;

    public BuscarDireccion(DirectionFinderListener listener, String origin, String destination, boolean decision) {
        this.listener = listener;
        this.origin = origin;
        this.destination = destination;
        this.decision = decision;
    }

    public void execute() throws UnsupportedEncodingException {
        listener.onDirectionFinderStart();
        new DownloadRawData().execute(createUrl());
    }

    private String createUrl() throws UnsupportedEncodingException {
        String urlOrigin = URLEncoder.encode(origin, "utf-8");
        String urlDestination = URLEncoder.encode(destination, "utf-8");
        String url;
        if (decision) {
            url = DIRECTION_URL_API + "origin=" + urlOrigin + "&destination=" + urlDestination + "&language=es" + "&mode=walking" + "&key=" + GOOGLE_API_KEY;
        } else {
            url = DIRECTION_URL_API + "origin=" + urlOrigin + "&destination=" + urlDestination + "&language=es" + "&key=" + GOOGLE_API_KEY;
        }
        return url;
    }

    private class DownloadRawData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String link = params[0];
            try {
                URL url = new URL(link);
                InputStream is = url.openConnection().getInputStream();
                StringBuffer buffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String res) {
            try {
                parseArchivoJSon(res);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void parseArchivoJSon(String data) throws JSONException {
        if (data == null){
            listener.resultadoNoValido("NULL");
            return;
        }

        List<Route> routes = new ArrayList<Route>();
        ArrayList<Indicacion> listpasos = new ArrayList<Indicacion>();
        ArrayList<String> pasos = new ArrayList<String>();
        JSONObject jsonData = new JSONObject(data);
        String status=jsonData.getString("status").toString();
        if (status.equals("OK")) {
            JSONArray jsonRoutes = jsonData.getJSONArray("routes");
            for (int i = 0; i < jsonRoutes.length(); i++) {
                JSONObject jsonRoute = jsonRoutes.getJSONObject(i);
                Route route = new Route();
                //Indicacion indicacion=new Indicacion();

                JSONObject overview_polylineJson = jsonRoute.getJSONObject("overview_polyline");
                JSONArray jsonLegs = jsonRoute.getJSONArray("legs");
                JSONObject jsonLeg = jsonLegs.getJSONObject(0);
                JSONArray jsonsteps = jsonLeg.getJSONArray("steps");
                for (int i1 = 0; i1 < jsonsteps.length(); i1++) {
                    String maniobra;
                    JSONObject jsonstep = jsonsteps.getJSONObject(i1);
                    JSONObject jsonDistancePasos = jsonstep.getJSONObject("distance");
                    JSONObject jsonDurationPasos = jsonstep.getJSONObject("duration");
                    String instruccion = Html.fromHtml(jsonstep.getString("html_instructions")).toString();
                    JSONObject polyline = jsonstep.getJSONObject("polyline");
                    boolean aux = jsonstep.has("maneuver");
                    if (!aux) {
                        maniobra = "null";
                    } else {
                        maniobra = jsonstep.getString("maneuver").toString();
                        //maniobra = "turn-left";
                    }
                    //pasos.add(i1, Html.fromHtml(jsonstep.getString("html_instructions")).toString());
                    Indicacion indicacion = new Indicacion(jsonDistancePasos.getString("text"), jsonDurationPasos.getString("text"),
                            instruccion, maniobra, decodePolyLine(polyline.getString("points")));
                    listpasos.add(indicacion);

                }
                //JSONObject jsonstep = jsonsteps.getJSONObject(0);
                //JSONObject pasoso=jsonstep.getJSONObject("html_instructions");
                JSONObject jsonDistance = jsonLeg.getJSONObject("distance");
                JSONObject jsonDuration = jsonLeg.getJSONObject("duration");
                JSONObject jsonEndLocation = jsonLeg.getJSONObject("end_location");
                JSONObject jsonStartLocation = jsonLeg.getJSONObject("start_location");

                route.distance = new Distance(jsonDistance.getString("text"), jsonDistance.getInt("value"));
                route.duration = new Duration(jsonDuration.getString("text"), jsonDuration.getInt("value"));
                route.endAddress = jsonLeg.getString("end_address");
                route.startAddress = jsonLeg.getString("start_address");
                route.startLocation = new LatLng(jsonStartLocation.getDouble("lat"), jsonStartLocation.getDouble("lng"));
                route.endLocation = new LatLng(jsonEndLocation.getDouble("lat"), jsonEndLocation.getDouble("lng"));
                //route.pasos = decodeHTML(jsonstep.getString("html_instructions"));
                //saca en formato html y lo convierte en string;
                route.listpasos = listpasos;
                //route.pasos=pasos;
                route.points = decodePolyLine(overview_polylineJson.getString("points"));
                //route.points = decodePolyLine("CmRbAAAA1bxW5x-aRQ2YzUtfMLQUfPQStM2UmppIC5L1qGJXKdafn4ptCMG3R8Ouk_Xjiczo02nIZN3Wq2-OlaZ3Hnh_qCpiaM0lCqP8YjSUX_6kkmjDtXyIopAPy_YhIFnO_X92EhCqRls72VtIQ87ILk9ak0haGhRLJZjBeHtzctyiJNdwUF-24S6V3w");

                routes.add(route);
            }

            listener.BuscarDireccionExitoso(routes);
        }else listener.resultadoNoValido(status);

    }

    private List<LatLng> decodePolyLine(final String poly) {
        int len = poly.length();
        int index = 0;
        List<LatLng> decoded = new ArrayList<LatLng>();
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int b;
            int shift = 0;
            int result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            decoded.add(new LatLng(
                    lat / 100000d, lng / 100000d
            ));
        }

        return decoded;
    }

}
