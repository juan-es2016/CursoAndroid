package com.m2.montano.j.localizadorcajeroscochalav2.Adapters;

/**
 * Created by juanes on 8/6/2017.
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.m2.montano.j.localizadorcajeroscochalav2.R;
import com.m2.montano.j.localizadorcajeroscochalav2.ServisiosApi.Indicacion;

import java.util.ArrayList;


public class AdaptadorIndicaciones extends
        RecyclerView.Adapter<AdaptadorIndicaciones.ViewHolder> {
    //pasos para llegar
    ArrayList<Indicacion> pasos;
    protected LayoutInflater inflador;   //Crea Layouts a partir del XML
    protected Context contexto;          //Lo necesitamos para el inflador
    protected View.OnClickListener onClickListener;

    public AdaptadorIndicaciones(ArrayList<Indicacion> pasos) {
        this.contexto = contexto;
        this.pasos = pasos;
        //inflador = (LayoutInflater) contexto
               // .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    //Creamos nuestro ViewHolder, con los tipos de elementos a modificar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        public TextView instruccion, tiempo, distancia;
        public ImageView flecha;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView=(CardView) itemView.findViewById(R.id.card_view_pasos);
            instruccion = (TextView) itemView.findViewById(R.id.intruccion);
            tiempo = (TextView) itemView.findViewById(R.id.tiempo_indi);
            distancia = (TextView) itemView.findViewById(R.id.distancia_indi);
            flecha = (ImageView) itemView.findViewById(R.id.flecha);

        }
    }

    // Creamos el ViewHolder con las vista de un elemento sin personalizar
    @Override
    public AdaptadorIndicaciones.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflamos la vista desde el xml
       // View v = inflador.inflate(R.layout.card_view_pasos, null);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_pasos,parent,false);
        v.setOnClickListener(onClickListener);
        return new ViewHolder(v);
    }

    // Usando como base el ViewHolder y lo personalizamos
    @Override
    public void onBindViewHolder(ViewHolder holder, int posicion) {
        Indicacion indicacion = pasos.get(posicion);
        personalizaVista(holder, indicacion);
    }

    // Personalizamos un ViewHolder a partir de un lugar
    public void personalizaVista(ViewHolder holder, Indicacion indicacion) {
        holder.instruccion.setText(indicacion.getInstruccion());
        holder.tiempo.setText(indicacion.getDuracion());
        holder.distancia.setText(indicacion.getDistancia());
        int id = R.drawable.bien;
        switch (indicacion.getManiobra()) {
            case "turn-right":
                id = R.drawable.ic_subdirectory_arrow_right_black_36dp;
                break;
            case "null":
                id = R.drawable.ic_arrow_upward_black_36dp;
                break;
            case "straight":
                id = R.drawable.ic_arrow_upward_black_36dp;
                break;
            case "turn-left":
                id = R.drawable.ic_subdirectory_arrow_left_black_36dp;
                break;
            case "turn-slight-left":
                id = R.drawable.gire_ligero_izquierda;
                break;
            case "turn-slight-right":
                id = R.drawable.gire_ligero_derecha;
                break;
        }
        holder.flecha.setImageResource(id);
        holder.flecha.setScaleType(ImageView.ScaleType.FIT_END);
    }

    // Indicamos el número de elementos de la lista
    @Override
    public int getItemCount() {
        return pasos.size();
    }
}
