package com.m2.montano.j.localizadorcajeroscochalav2.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.m2.montano.j.localizadorcajeroscochalav2.Fragments.ListaNoConfirmadoFragment;
import com.m2.montano.j.localizadorcajeroscochalav2.Model.Cajero;
import com.m2.montano.j.localizadorcajeroscochalav2.R;

public class AdministradorMainActivity extends BaseActivity implements ListaNoConfirmadoFragment.OnListaNoConfirmadoFragmentInteractionListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Lista de cajeros por confirmar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //showLoadingDialog();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });

        setFragment(2);

    }

    @Override
    public void CajeroNoConfirmadaSelecionado(Cajero item) {
        Intent intent = new Intent(this, AnadirSitioActivity.class);
        AnadirSitioActivity.llenarDatosCajero(item);
        startActivity(intent);
        Toast.makeText(this,"desde administrador",Toast.LENGTH_SHORT).show();
    }
}
