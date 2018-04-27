package com.m2.montano.j.localizadorcajeroscochalav2.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.m2.montano.j.localizadorcajeroscochalav2.Fragments.HomeMapsFragment;
import com.m2.montano.j.localizadorcajeroscochalav2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MarcarPuntoMapaActivity extends AppCompatActivity implements HomeMapsFragment.OnFragmentInteractionListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.guardar_punto_seleccionado)
    Button guardarPuntoSeleccionado;

    LatLng puntoMapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcar_punto_mapa);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        HomeMapsFragment homeMapsFragment = new HomeMapsFragment();
        homeMapsFragment.setOcultarTexto(true);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.marcarPuntoMapa, homeMapsFragment);
        fragmentTransaction.commit();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.ubicacion_en_el_mapa);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
/// metodos de interface
    @Override
    public void onFragmentInteraction() {

    }

    @Override
    public void CodenadasPuntoMapa(LatLng punto) {
        Toast.makeText(this, "marcando un punto", Toast.LENGTH_SHORT).show();
        puntoMapa = punto;
    }
//
    @OnClick({R.id.guardar_punto_seleccionado, R.id.toolbar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.guardar_punto_seleccionado:
             salvarPuntoSeleccionado();
                break;
        }
    }

    private void salvarPuntoSeleccionado() {
        if (puntoMapa!=null){
        String cad = "hola";
        Intent data = new Intent();
        data.setData(Uri.parse(cad));
        data.putExtra("latitud", puntoMapa.latitude);
        data.putExtra("longitud", puntoMapa.longitude);
        setResult(RESULT_OK, data);
        finish();
        }else Toast.makeText(this, "seleccine un punto por favor", Toast.LENGTH_SHORT).show();
    }
}
