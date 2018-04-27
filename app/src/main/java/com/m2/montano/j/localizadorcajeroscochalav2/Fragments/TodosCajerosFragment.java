package com.m2.montano.j.localizadorcajeroscochalav2.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.m2.montano.j.localizadorcajeroscochalav2.Adapters.CajerosRecyclerViewAdapter;
import com.m2.montano.j.localizadorcajeroscochalav2.Model.Cajero;
import com.m2.montano.j.localizadorcajeroscochalav2.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link ListaTodosCajeros}
 * interface.
 */
public class TodosCajerosFragment extends Fragment implements SearchView.OnQueryTextListener,
        MenuItem.OnActionExpandListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    @BindView(R.id.list)
    RecyclerView list;
    Unbinder unbinder;
    @BindView(R.id.mostrar_en_mapa)
    Button mostrarEnMapa;
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private ListaTodosCajeros InterfaceListaCajeros;
    private List<Cajero> listaCajeros;
    private View view;
    SearchView searchView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TodosCajerosFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TodosCajerosFragment newInstance(int columnCount) {
        TodosCajerosFragment fragment = new TodosCajerosFragment();
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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_todos_cajeros, container, false);
        listaCajeros = new ArrayList<Cajero>();
        searchView = new SearchView(getContext());
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search");

        super.onCreateOptionsMenu(menu, inflater);

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListaTodosCajeros) {
            InterfaceListaCajeros = (ListaTodosCajeros) context;
            //Toast.makeText(getActivity(), "prueba Item", Toast.LENGTH_SHORT).show();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ListaTodosCajeros");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        InterfaceListaCajeros = null;
    }


    public void setListaCajeros(List<Cajero> listaCajeros) {
        this.listaCajeros = listaCajeros;
       // if (view instanceof RecyclerView) {
           // Context context = view.getContext();
            //list = (RecyclerView) view;
           // if (mColumnCount <= 1) {
                list.setLayoutManager(new LinearLayoutManager(getContext()));
           // } else {
             //   list.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            //}
            list.setAdapter(new CajerosRecyclerViewAdapter(listaCajeros, InterfaceListaCajeros));
        //}
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.mostrar_en_mapa)
    public void onViewClicked() {
        InterfaceListaCajeros.MostrarTodosEnMapa();
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //showToast("longitud cajero" + cajeros.size());
        ArrayList<Cajero> filteredValues = new ArrayList<Cajero>(listaCajeros);
        for (Cajero cajero : listaCajeros) {
            String aux = cajero.getNombre();
            if (!aux.toLowerCase().contains(newText.toLowerCase())) {
                filteredValues.remove(cajero);
            }//else aux=true;
        }
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        // } else {
        //   list.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        //}
        list.setAdapter(new CajerosRecyclerViewAdapter(filteredValues, InterfaceListaCajeros));

        return true;
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
    public interface ListaTodosCajeros {
        // TODO: Update argument type and name
        void seleccionarCajero(Cajero cajero);
        void MostrarTodosEnMapa();
    }


    ////////////////////////////////////////////////



}
