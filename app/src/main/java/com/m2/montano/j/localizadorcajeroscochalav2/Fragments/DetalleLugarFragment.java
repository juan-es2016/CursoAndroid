package com.m2.montano.j.localizadorcajeroscochalav2.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.m2.montano.j.localizadorcajeroscochalav2.Model.Cajero;
import com.m2.montano.j.localizadorcajeroscochalav2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnDetalleLugarFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetalleLugarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalleLugarFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    CollapsingToolbarLayout collapsingToolbar;
    int postion;
    Resources resources;
    LinearLayout linearLayout;

    @BindView(R.id.titulo_lugar)
    TextView titulo_lugar;
    @BindView(R.id.nombre_cajero)
    TextView nombre_cajero;
    @BindView(R.id.direccion_cajero)
    TextView direccion_cajero;
    @BindView(R.id.horario_atencion)
    TextView horario_atencion;
    @BindView(R.id.url)
    Button sitio_web;
    @BindView(R.id.como_llegar)
    Button b_como_llegar;
    Cajero cajero_rv;
    @BindView(R.id.mostrar_en_mapa)
    Button mostrarEnMapa;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnDetalleLugarFragmentInteractionListener mListener;
    private MapsFragment mapFragment;

    public DetalleLugarFragment() {
        // Required empty public constructor
    }

    public void setCajero_rv(Cajero cajero_rv) {
        this.cajero_rv = cajero_rv;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetalleLugarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetalleLugarFragment newInstance(Parcelable param1, Parcelable param2) {
        DetalleLugarFragment fragment = new DetalleLugarFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        args.putParcelable(ARG_PARAM2, param2);
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle_lugar, container, false);

        ButterKnife.bind(this, view);
        //////
        sitio_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://" + sitio_web.getText().toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        ////////

        //b_como_llegar = (Button) view.findViewById(R.id.como_llegar);
        collapsingToolbar =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        resources = getResources();
        //mapFragment.setClip_rv(true);
        // mapFragment.setCajero_rv(cajero_rv);
        MapsFragment mapsFragment = new MapsFragment();
        mapsFragment.setClip_rv(true);
        mapsFragment.setCajero_rv(cajero_rv);
        FragmentTransaction fragmentTransaction2 = getFragmentManager().beginTransaction();
        fragmentTransaction2.replace(R.id.fragmentMap2, mapsFragment);
        fragmentTransaction2.commit();
        llenarDatos();
        return view;
    }

    public void llenarDatos() {
        titulo_lugar.setText(cajero_rv.getNombre());
        nombre_cajero.setText(cajero_rv.getNombre());
        direccion_cajero.setText(cajero_rv.getDireccion());
        horario_atencion.setText(cajero_rv.getHorario());
        sitio_web.setText(cajero_rv.getSitioWeb());

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Cajero uri) {
        if (mListener != null) {
            mListener.onDetalleLugarFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDetalleLugarFragmentInteractionListener) {
            mListener = (OnDetalleLugarFragmentInteractionListener) context;
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

    public void setMapFragment(MapsFragment mapFragment) {
        this.mapFragment = mapFragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.mostrar_en_mapa, R.id.como_llegar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mostrar_en_mapa:
                if (mListener != null) {
                    mListener.mostrarEnMapa(cajero_rv);
                }
                break;
            case R.id.como_llegar:
                if (mListener != null) {
                    mListener.onDetalleLugarFragmentInteraction(cajero_rv);
                }
                break;
        }
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
    public interface OnDetalleLugarFragmentInteractionListener {
        // TODO: Update argument type and name
        void onDetalleLugarFragmentInteraction(Cajero cajero_rv);

        void mostrarEnMapa(Cajero cajero);
    }
}
