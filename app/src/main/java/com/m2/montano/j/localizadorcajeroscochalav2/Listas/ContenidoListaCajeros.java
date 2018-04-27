package com.m2.montano.j.localizadorcajeroscochalav2.Listas;

import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.m2.montano.j.localizadorcajeroscochalav2.Activities.BaseActivity;
import com.m2.montano.j.localizadorcajeroscochalav2.Adapters.CajerosRecyclerViewAdapter;
import com.m2.montano.j.localizadorcajeroscochalav2.Adapters.ListaNoConfirmadaViewAdapter;
import com.m2.montano.j.localizadorcajeroscochalav2.Fragments.ListaNoConfirmadoFragment;
import com.m2.montano.j.localizadorcajeroscochalav2.Fragments.TodosCajerosFragment;
import com.m2.montano.j.localizadorcajeroscochalav2.Model.Cajero;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ContenidoListaCajeros extends BaseActivity {
    
    public List<Cajero> ITEMS = new ArrayList<Cajero>();
    private ListaNoConfirmadoFragment.OnListaNoConfirmadoFragmentInteractionListener mListener;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference locais=database.getReference("CajerosNoConfirmados");

    public static final Map<String, Cajero> ITEM_MAP = new HashMap<String, Cajero>();

    public ArrayList<Cajero> getITEMS() {
        return (ArrayList<Cajero>) ITEMS;
    }

    public ContenidoListaCajeros(RecyclerView recyclerView, ListaNoConfirmadoFragment.OnListaNoConfirmadoFragmentInteractionListener mListener) {
        this.mListener=mListener;
        llenarListaFirebase(recyclerView);
    }
    public void llenarListaFirebase(final RecyclerView rv) {
        locais.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ITEMS.clear();
                Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();
                for (DataSnapshot dataSnapshot1 : dataSnapshots) {
                    Cajero cajero = dataSnapshot1.getValue(Cajero.class);
                    ITEMS.add(cajero);
                    //mAllValues.add(cajero.getNome());
                }
                rv.setAdapter(new ListaNoConfirmadaViewAdapter(ITEMS,mListener));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                closeLoadingDialog();
               // showAlertDialog(this,"parece que no tienes coneccion a internet",
                   //     "Verifique si tiene coneccion a internet", false);
            }
        });
    }

}
