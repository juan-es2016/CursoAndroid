package com.m2.montano.j.localizadorcajeroscochalav2.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.m2.montano.j.localizadorcajeroscochalav2.Fragments.ListaNoConfirmadoFragment.OnListaNoConfirmadoFragmentInteractionListener;
import com.m2.montano.j.localizadorcajeroscochalav2.Model.Cajero;
import com.m2.montano.j.localizadorcajeroscochalav2.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Cajero} and makes a call to the
 * specified {@link OnListaNoConfirmadoFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ListaNoConfirmadaViewAdapter extends RecyclerView.Adapter<ListaNoConfirmadaViewAdapter.ViewHolder> {

    private final List<Cajero> mValues;
    private final OnListaNoConfirmadoFragmentInteractionListener mListener;

    public ListaNoConfirmadaViewAdapter(List<Cajero> items, OnListaNoConfirmadoFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        personalizaVista(holder, mValues.get(position));
    }
    public void personalizaVista(final ViewHolder holder, Cajero lugar) {
        holder.mItem = lugar;
        holder.nombre.setText(lugar.getNombre());
        holder.direccion.setText(lugar.getDireccion());
        int id = R.drawable.bien;
        int id2 = R.drawable.interrogante;
        switch(lugar.getNombreEF()) {
            case "Banco Prodem S.A.":id = R.drawable.banco_prodem; break;
            case "Banco Unión S.A.": id = R.drawable.banco_union;  break;
            case "Banco Nacional de Bolivia S.A.":      id = R.drawable.banco_bnb;       break;
            case "Banco Mercantil Santa Cruz S.A.":id = R.drawable.banco_mercantil_sc; break;
            case "Banco Solidario S.A.":      id = R.drawable.banco_sol;       break;
            case "Banco Bisa S.A.":    id = R.drawable.banco_bisa;     break;
            case "Banco Económico S.A.":  id = R.drawable.banco_economico;   break;
            case "Banco Ganadero S.A.":    id = R.drawable.banco_ganadero;     break;
            case "Banco Fassil S.A.": id = R.drawable.banco_fassil;  break;
        }
        holder.foto.setImageResource(id);
        holder.foto.setScaleType(ImageView.ScaleType.FIT_END);
        holder.valoracion.setRating(lugar.getValoracion());
        switch (lugar.getValoracion()){
            case 3:id2=R.drawable.bien; break;
        }
        holder.img_valoracion.setImageResource(id2);
        holder.foto.setScaleType(ImageView.ScaleType.FIT_END);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.CajeroNoConfirmadaSelecionado(holder.mItem);
                    //Log.d("prueba","clipp");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public Cajero mItem;
        @BindView(R.id.card_view)CardView cardView;
        @BindView(R.id.nombre1)TextView nombre;
        @BindView(R.id.direccion1)TextView direccion;
        @BindView(R.id.foto1)ImageView foto;
        @BindView(R.id.imag_valoracion)ImageView img_valoracion;
        @BindView(R.id.valoracion1)
        RatingBar valoracion;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
