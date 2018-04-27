package com.m2.montano.j.localizadorcajeroscochalav2.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.m2.montano.j.localizadorcajeroscochalav2.Activities.MarcarPuntoMapaActivity;
import com.m2.montano.j.localizadorcajeroscochalav2.Model.Cajero;
import com.m2.montano.j.localizadorcajeroscochalav2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeMapsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeMapsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeMapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.textoAñadir)
    TextView textoAnadir;
    Unbinder unbinder;
    private GoogleMap mMap;
    private View view;
    private int request_code = 1;
    boolean ocultarTexto = false;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Marker marker_edith;
    private double latitud;
    private double longitud;
    private boolean mostrarPunto = false;
    private Cajero cajero;
    private Location localizacion;

    public HomeMapsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeMapsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeMapsFragment newInstance(String param1, String param2) {
        HomeMapsFragment fragment = new HomeMapsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_maps, container, false);

        // ((MainActivity) getActivity()).getSupportActionBar().setTitle("Inicio");
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        unbinder = ButterKnife.bind(this, view);
        if (ocultarTexto) {
            textoAnadir.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentMapInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        if (cajero!=null){
            LatLng cajero1 = new LatLng(cajero.getLatitude(),cajero.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(cajero1, 14);
            marker_edith = mMap.addMarker(new MarkerOptions().position(cajero1).title(cajero.getNombre()));
            mMap.animateCamera(cameraUpdate);
        }else{
if (localizacion!=null) {
    //mMap.setBuildingsEnabled(true);
    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    LatLng ubicacionActual = new LatLng(localizacion.getLatitude(),localizacion.getLongitude());
    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(ubicacionActual, 14);
    mMap.animateCamera(cameraUpdate);
    mMap.setOnMapClickListener(this);
}else{
    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    LatLng cercado = new LatLng(-17.3525926, -66.2490476);
    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(cercado, 14);
    mMap.animateCamera(cameraUpdate);
    mMap.setOnMapClickListener(this);
}
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mMap.clear();
        //Toast.makeText(getActivity(), "todos", Toast.LENGTH_SHORT).show();
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                .draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
        mListener.CodenadasPuntoMapa(latLng);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.textoAñadir)
    public void onViewClicked() {
        //textoAnadir.setVisibility(View.INVISIBLE);
        mListener.onFragmentInteraction();
    }

    public void setOcultarTexto(boolean ocultarTexto) {
        //textoAnadir.setVisibility(View.INVISIBLE);
        this.ocultarTexto = ocultarTexto;
    }

    public void setLatitudLongitud(double latitud, double longitud) {
        this.latitud=latitud;
        this.longitud=longitud;
    }

    public void mostrarPunto(boolean punto) {
        this.mostrarPunto=punto;
    }

    public void marcarPunto() {
        LatLng latLng= new LatLng(latitud,longitud);
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("presione en el marcador para añadir el cajero").icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                .draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
    }

    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }

    public void setLocalizacion(Location localizacion) {
        this.localizacion = localizacion;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
        void CodenadasPuntoMapa(LatLng punto);
    }
}
