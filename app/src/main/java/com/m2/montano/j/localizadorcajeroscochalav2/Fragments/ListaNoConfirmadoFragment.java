package com.m2.montano.j.localizadorcajeroscochalav2.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m2.montano.j.localizadorcajeroscochalav2.Adapters.CajerosRecyclerViewAdapter;
import com.m2.montano.j.localizadorcajeroscochalav2.Adapters.ListaNoConfirmadaViewAdapter;
import com.m2.montano.j.localizadorcajeroscochalav2.Listas.ContenidoListaCajeros;
import com.m2.montano.j.localizadorcajeroscochalav2.Model.Cajero;
import com.m2.montano.j.localizadorcajeroscochalav2.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListaNoConfirmadoFragmentInteractionListener}
 * interface.
 */
public class ListaNoConfirmadoFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListaNoConfirmadoFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListaNoConfirmadoFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ListaNoConfirmadoFragment newInstance(int columnCount) {
        ListaNoConfirmadoFragment fragment = new ListaNoConfirmadoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_no_confirmada, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new ListaNoConfirmadaViewAdapter(new ContenidoListaCajeros (recyclerView,mListener).getITEMS(),mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListaNoConfirmadoFragmentInteractionListener) {
            mListener = (OnListaNoConfirmadoFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListaNoConfirmadoFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListaNoConfirmadoFragmentInteractionListener {
        // TODO: Update argument type and name
        void CajeroNoConfirmadaSelecionado(Cajero item);
    }
}
