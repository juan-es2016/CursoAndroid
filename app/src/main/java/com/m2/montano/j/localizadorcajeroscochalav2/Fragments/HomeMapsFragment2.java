package com.m2.montano.j.localizadorcajeroscochalav2.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.m2.montano.j.localizadorcajeroscochalav2.Activities.MarcarPuntoMapaActivity;
import com.m2.montano.j.localizadorcajeroscochalav2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeMapsFragment2 extends Fragment implements OnMapReadyCallback ,GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks{

    @BindView(R.id.textoAñadir)
    TextView textoAnadir;
    Unbinder unbinder;
    private GoogleMap mMap;
    private View view;
    private int request_code=1;
    private double latitud, longitud;
    private GoogleApiClient apiClient;

    private static final String LOGTAG = "android-localizacion";

    private static final int PETICION_PERMISO_LOCALIZACION = 101;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_maps, container, false);

        // ((MainActivity) getActivity()).getSupportActionBar().setTitle("Inicio");
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        unbinder = ButterKnife.bind(this, view);

        /*apiClient = new GoogleApiClient.Builder(getContext())
                //.enableAutoManage(getActivity(),getActivity())
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();*/
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public void onStart() {
        //apiClient.connect();
        Log.d("ConnectonStart", "Connected ");
        Log.d("ONstart", Boolean.toString(apiClient.isConnected()));
        super.onStart();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setBuildingsEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng cercado = new LatLng(-17.389844, -66.166862);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(cercado, 14);
       mMap.animateCamera(cameraUpdate);
        //mMap.setOnMapClickListener(this);
    }

    /*@Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(getActivity(), "todos", Toast.LENGTH_SHORT).show();
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.textoAñadir)
    public void onViewClicked() {
        /*MapsFragment mapsFragment=new MapsFragment();
        mapsFragment.setEtith_mapa(true);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content_main,mapsFragment);
        fragmentTransaction.commit();
        Toast.makeText(getActivity(), "texto", Toast.LENGTH_SHORT).show();*/

        //item.setEnabled(true);
        //Context context = AnadirSitioActivity.this;
        //MarcarPuntoMapaActivity.setFragment(this);
        //Context cont=getView().getContext();
        Intent intent = new Intent(getView().getContext(),MarcarPuntoMapaActivity.class);
        // intent.putExtra(DetailActivity.EXTRA_POSITION, getAdapterPosition());

        //getView().getContext().startActivity(intent);
        //getView().getContext().st;
        startActivityForResult(intent,request_code);
    }

    public void setPosicionActual(double latitud, double longitud) {
        this.latitud=latitud;
        this.longitud=longitud;
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        //Se ha producido un error que no se puede resolver automáticamente
        //y la conexión con los Google Play Services no se ha establecido.

        Log.e(LOGTAG, "Error grave al conectar con Google Play Services");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Conectado correctamente a Google Play Services

        Toast.makeText(getContext(),"conectado",Toast.LENGTH_SHORT).show();

        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
        } else {

            Location lastLocation =
                    LocationServices.FusedLocationApi.getLastLocation(apiClient);
            LatLng cercado = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(cercado));
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(cercado, 20);
            mMap.animateCamera(cameraUpdate);
            updateUI(lastLocation);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Se ha interrumpido la conexión con Google Play Services

        Log.e(LOGTAG, "Se ha interrumpido la conexión con Google Play Services");
    }

    private void updateUI(Location loc) {
        if (loc != null) {
            //lblLatitud.setText("Latitud: " + String.valueOf(loc.getLatitude()));
           // lblLongitud.setText("Longitud: " + String.valueOf(loc.getLongitude()));
        } else {
            //lblLatitud.setText("Latitud: (desconocida)");
            //lblLongitud.setText("Longitud: (desconocida)");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PETICION_PERMISO_LOCALIZACION) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Permiso concedido

                @SuppressWarnings("MissingPermission")
                Location lastLocation =
                        LocationServices.FusedLocationApi.getLastLocation(apiClient);

                updateUI(lastLocation);

            } else {
                //Permiso denegado:
                //Deberíamos deshabilitar toda la funcionalidad relativa a la localización.

                Log.e(LOGTAG, "Permiso denegado");
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (apiClient == null) {
           apiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }
}
