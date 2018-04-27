package com.m2.montano.j.localizadorcajeroscochalav2.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.m2.montano.j.localizadorcajeroscochalav2.Adapters.AdaptadorIndicaciones;
import com.m2.montano.j.localizadorcajeroscochalav2.Model.Cajero;
import com.m2.montano.j.localizadorcajeroscochalav2.R;
import com.m2.montano.j.localizadorcajeroscochalav2.ServisiosApi.BuscarDireccion;
import com.m2.montano.j.localizadorcajeroscochalav2.ServisiosApi.DirectionFinderListener;
import com.m2.montano.j.localizadorcajeroscochalav2.ServisiosApi.Indicacion;
import com.m2.montano.j.localizadorcajeroscochalav2.ServisiosApi.Route;
import com.m2.montano.j.localizadorcajeroscochalav2.Utils.AsynTaskUtil;
import com.m2.montano.j.localizadorcajeroscochalav2.Utils.AsyncTaskCallBackUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentMapInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapsFragment extends BaseFragment implements
        OnMapReadyCallback, GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener
        , DirectionFinderListener, MenuItem.OnActionExpandListener{


    boolean marcarRuta = false;
    @BindView(R.id.pasos)
    Button pasos;
    @BindView(R.id.tvDistance)
    TextView tvDistance;
    @BindView(R.id.imagedistancia)
    ImageView imagedistancia;
    @BindView(R.id.tvDuration)
    TextView tvDuration;
    @BindView(R.id.imagetiempo)
    ImageView imagetiempo;
    @BindView(R.id.tiempo_distancia)
    LinearLayout linearLayoutTiempoDistancia;
    @BindView(R.id.recycler_view_pasos)
    RecyclerView recyclerViewPasos;
    @BindView(R.id.mas_informacion)
    Button masInformacion;

    private GoogleMap googleMap;
    private List<Polyline> polylinePaths = new ArrayList<>();
    LatLng posAct;
    private boolean etith_mapa, clip_rv = false;
    Cajero cajero_rv;
    private Marker marker_edith;
    PolylineOptions polylineOptions, polylineOptionsPasos;
    View rootView;
    Double posActLat, posActLon, posAltLat, posAltLon;
    public List<Cajero> cajeros;
    public boolean decisionPieMovil = false;
    boolean markerRuta = false;
    Location posicion;

    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Indicacion> listpasos;
    AdaptadorIndicaciones adaptador;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentMapInteractionListener mListener;
    private boolean listarTodos;
    private Marker clicMarker;

    public MapsFragment() {
        // Required empty public constructor
        cajeros = new ArrayList<Cajero>();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MapsFragmentReserva.
     */
    // TODO: Rename and change types and number of parameters
    public static MapsFragment newInstance() {

            MapsFragment fragment = new MapsFragment();
            // Setup de argumentos en caso de que los haya
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
        rootView = inflater.inflate(R.layout.fragment_maps, container, false);
        ButterKnife.bind(this, rootView);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        recyclerViewPasos.setHasFixedSize(true);
        listpasos = new ArrayList<Indicacion>();
        Resources res = getResources();

        //mPresenter.attemptLogin(
             //   "HOLA",
              //  "PATO");

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentMapInteractionListener) {
            mListener = (OnFragmentMapInteractionListener) context;
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

    public void setListaCajeros(List<Cajero> listaCajeros) {
        this.cajeros = listaCajeros;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void setListarTodos(boolean listarTodos) {
        this.listarTodos = listarTodos;
    }

    @OnClick({R.id.pasos, R.id.mas_informacion})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pasos:
                adaptador = new AdaptadorIndicaciones(listpasos);
                recyclerViewPasos.setAdapter(adaptador);
                layoutManager = new LinearLayoutManager(getActivity());
                recyclerViewPasos.setLayoutManager(layoutManager);
                recyclerViewPasos.setVisibility(View.VISIBLE);
                adaptador.setOnItemClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        long posRV = recyclerViewPasos.getChildAdapterPosition(v);
                        int aux = (int) posRV;
                        Indicacion indicacion = listpasos.get(aux);
                        polylineOptionsPasos = new PolylineOptions().
                                geodesic(true).
                                color(Color.GREEN).
                                width(10);

                        for (int i = 0; i < indicacion.points.size(); i++)
                            polylineOptionsPasos.add(indicacion.points.get(i));

                        googleMap.addPolyline(polylineOptionsPasos);
                    }
                });
                pasos.setVisibility(rootView.INVISIBLE);
                break;
            case R.id.mas_informacion:
                crearDetalleLugar(clicMarker);
                break;
        }
    }

    ////metodos interface loginContract

    @Override
    public void onResume() {
        super.onResume();
        //mPresenter.start();
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
    public interface OnFragmentMapInteractionListener {
        // TODO: Update argument type and name
        void cambiarTitleActionBar();

        void setFragmentError();

        void setFragmentPrincipal();

        void onInvokeGooglePlayServices(int codeError);
    }

    @Override
    public void onMapReady(GoogleMap googleM) {
        this.googleMap = googleM;

        if (etith_mapa) {
            googleMap.setOnMapClickListener(this);//para que escuche el mapa
        }


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

        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnInfoWindowClickListener(this);

        googleMap.getUiSettings().setMapToolbarEnabled(false);
        //googleMap.setOnMapClickListener(this);//para que escuche el mapa
        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            public void onMyLocationChange(Location pos) {
                // TODO Auto-generated method stub

                // Extraigo la Lat y Lon del Listener

                posicion = pos;
                posActLat = pos.getLatitude();
                posActLon = pos.getLongitude();
                posAct = new LatLng(posActLat, posActLon);
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);//es para el tipo de mapa

            }
        });
        if (clip_rv) {
            //Toast.makeText(getActivity(), "longitud cajero" + cajero_rv.getLatitude(), Toast.LENGTH_SHORT).show();
            googleMap.clear();
            LatLng local1 = new LatLng(cajero_rv.getLatitude(), cajero_rv.getLongitude());
            marker_edith = googleMap.addMarker(new MarkerOptions().position(local1).title(cajero_rv.getNombre())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icono_cajero)));
            marker_edith.showInfoWindow();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(local1, 17));
            //fab.setVisibility(View.VISIBLE);
        } else {
            if (marcarRuta) {
                esperar();
                linearLayoutTiempoDistancia.setVisibility(View.VISIBLE);
            } else {
                if (listarTodos) {
                    mostrarTodos();

                } else {
                    LatLng cercado = new LatLng(-17.389844, -66.166862);
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(cercado, 13);
                    googleMap.animateCamera(cameraUpdate);
                }
            }
        }

    }

    private void mostrarTodos() {
        googleMap.clear();
        LatLng cercado = new LatLng(-17.389844, -66.166862);
        for (int i = 0; i < cajeros.size(); i++) {
            Cajero cajero = cajeros.get(i);
            LatLng pos = new LatLng(cajero.getLatitude(), cajero.getLongitude());
            marker_edith = googleMap.addMarker(new MarkerOptions().position(pos).title(cajero.getNombre())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icono_cajero)));
            marker_edith.showInfoWindow();
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cercado, 13));
    }

    @Override
    public void onMapClick(LatLng latLng) {

        marker_edith = googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("presione en el marcador para añadir el cajero").icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                .draggable(true));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
        marker_edith.showInfoWindow();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        masInformacion.setVisibility(View.VISIBLE);
        clicMarker=marker;
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        crearDetalleLugar(marker);
    }

    public void crearDetalleLugar(Marker marker) {
        googleMap.clear();
        posAltLat = marker.getPosition().latitude;
        posAltLon = marker.getPosition().longitude;

        mListener.cambiarTitleActionBar();
        DetalleLugarFragment detalleLugarFragment = new DetalleLugarFragment();

        Cajero ca = buscarCajero(marker.getTitle());
        detalleLugarFragment.setCajero_rv(ca);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.fragment_content_main, detalleLugarFragment);
        fragmentTransaction.commit();
        fragmentTransaction.remove(this);
    }

    private Cajero buscarCajero(String titulo) {
        //boolean encontrado=false;
        for (int i = 0; i < cajeros.size(); i++) {
            if (cajeros.get(i).getNombre().equals(titulo)) {
                //encontrado=true;
                return cajeros.get(i);
            }
        }
        return null;
    }


    public void setEtith_mapa(boolean etith_mapa) {
        this.etith_mapa = etith_mapa;
    }

    public void setCajero_rv(Cajero cajero_rv) {
        this.cajero_rv = cajero_rv;
    }

    public void setCajeros(List<Cajero> cajeros) {
        this.cajeros = cajeros;
    }

    public void setClip_rv(boolean clip_rv) {
        this.clip_rv = clip_rv;
    }

    public void setDecisionPieMovil(boolean decisionPieMovil) {
        this.decisionPieMovil = decisionPieMovil;
    }

    public void setMarcarRuta(boolean marcarRuta) {
        this.marcarRuta = marcarRuta;
    }


    public void cercanos() {
        //Toast.makeText(getContext(), cajeros.size() + "", Toast.LENGTH_SHORT).show();
        //mListener.onFragmentMapInteraction();
        markerRuta = false;
        listpasos.clear();
        adaptador = new AdaptadorIndicaciones(listpasos);
        recyclerViewPasos.setAdapter(adaptador);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewPasos.setLayoutManager(layoutManager);
        recyclerViewPasos.setVisibility(View.INVISIBLE);
        pasos.setVisibility(View.INVISIBLE);
        linearLayoutTiempoDistancia.setVisibility(View.INVISIBLE);
        googleMap.clear();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(posAct, 15);
        googleMap.animateCamera(cameraUpdate);
        if (cajeros.size() > 0) {
            for (int i = 0; i < cajeros.size(); i++) {
                Location location = new Location("localizacion 1");
                location.setLatitude(posActLat);  //latitud
                location.setLongitude(posActLon); //longitud
                Location location2 = new Location("localizacion 2");
                location2.setLatitude(cajeros.get(i).getLatitude());  //latitud
                location2.setLongitude(cajeros.get(i).getLongitude()); //longitud
                double distance = location.distanceTo(location2);
                if (distance <= 1000) {
                    marker_edith = googleMap.addMarker(new MarkerOptions()

                            .position(new LatLng(cajeros.get(i).getLatitude(), cajeros.get(i).getLongitude())).title(cajeros.get(i).getNombre())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.icono_cajero)));
                    //googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(LayoutInflater.from(getActivity())));
                    //marker_edith.showInfoWindow();

                }
            }
        }
    }

    public void esperar() {
        AsynTaskUtil asynTaskUtil = new AsynTaskUtil(new AsyncTaskCallBackUtil() {
            @Override
            public void onPreExecute() {
                showLoadingDialog();
            }

            @Override
            public void doInBackgroundCallBack() {

                esperarTiempo();
            }

            @Override
            public void onPostExecute() {
                posAltLat = cajero_rv.getLatitude();
                posAltLon = cajero_rv.getLongitude();
                sendRequest();
            }

            @Override
            public void onCancelled() {

            }
        });
        asynTaskUtil.execute();
    }

    private void esperarTiempo() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
    }
    //// metodos de interface

    private void sendRequest() {
        Toast.makeText(getContext(), "valor origen " + posActLat + "::" + posActLon, Toast.LENGTH_SHORT).show();
        String origin = String.valueOf(posActLat) + "," + " " + String.valueOf(posActLon);
        String destination = String.valueOf(posAltLat) + "," + " " + String.valueOf(posAltLon);

        try {
            new BuscarDireccion(this, origin, destination, decisionPieMovil).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDirectionFinderStart() {
        showLoadingDialog();
        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

    @Override
    public void BuscarDireccionExitoso(List<Route> routes) {
        listpasos.clear();
        closeLoadingDialog();
        polylinePaths = new ArrayList<>();
        pasos.setVisibility(View.VISIBLE);
        for (Route route : routes) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            tvDuration.setText(route.duration.text);
            tvDistance.setText(route.distance.text);

            for (int i = 0; i < route.listpasos.size(); i++) {
                listpasos.add(route.listpasos.get(i));
            }
            marker_edith = googleMap.addMarker(new MarkerOptions().
                    position(route.endLocation).title(route.endAddress)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green)));
            markerRuta = true;
            polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(googleMap.addPolyline(polylineOptions));

        }
    }

    @Override
    public void resultadoNoValido(String status) {
        switch (status) {
            case "NOT_FOUND":
                showAlertDialog(getContext(), "Error", "no se pudo geocodificar al menos a una de las ubicaciones especificadas en el origen, el destino o los waypoints de la solicitud.", false);
                break;
            case "ZERO_RESULTS":
                showAlertDialog(getContext(), "Error", "no fue posible hallar una ruta entre el origen y el destino.", false);
                break;
            case "INVALID_REQUEST":
                showAlertDialog(getContext(), "Error", "la solicitud proporcionada no es válida", false);
                break;
            case "OVER_QUERY_LIMIT":
                showAlertDialog(getContext(), "Error", "el servicio recibió demasiadas solicitudes desde tu aplicación dentro del período permitido.", false);
                break;
            case "REQUEST_DENIED":
                showAlertDialog(getContext(), "Error", "el servicio no permitió que tu aplicación usara el servicio de indicaciones.", false);
                break;
            case "UNKNOWN_ERROR":
                showAlertDialog(getContext(), "Error", "no se pudo procesar una solicitud de indicaciones debido a un error en el servidor. La solicitud puede tener éxito si realizas un nuevo intento.", false);
                break;
            case "NULL":
                showAlertDialog(getContext(), "Error", "no se pudo procesar la solicitud debido a un error de conexión, por favor verifique que este activo conexión a internet", false);
                break;
        }
        closeLoadingDialog();
        googleMap.clear();
        LatLng local1 = new LatLng(cajero_rv.getLatitude(), cajero_rv.getLongitude());
        marker_edith = googleMap.addMarker(new MarkerOptions().position(local1).title(cajero_rv.getNombre()));
        marker_edith.showInfoWindow();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(local1, 17));
        //fab.setVisibility(View.VISIBLE);
        linearLayoutTiempoDistancia.setVisibility(rootView.INVISIBLE);
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return true;
    }

}
