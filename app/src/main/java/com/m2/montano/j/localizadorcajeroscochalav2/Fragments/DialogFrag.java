package com.m2.montano.j.localizadorcajeroscochalav2.Fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.m2.montano.j.localizadorcajeroscochalav2.R;

import javax.mail.Quota;

/**
 * Fragmento con diálogo básico
 */
public class DialogFrag extends android.support.v4.app.DialogFragment {

    OnSimpleDialogListener listener;

    public DialogFrag() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createSimpleDialog();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (OnSimpleDialogListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(
                    activity.toString() +
                            " no implementó OnSimpleDialogListener");

        }
    }

    /**
     * Crea un diálogo de alerta sencillo
     *
     * @return Nuevo diálogo
     */
    public AlertDialog createSimpleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog_signin, null);

        builder.setView(v);
        Button signin = (Button) v.findViewById(R.id.entrar_boton);
        Button cancelar = (Button) v.findViewById(R.id.bt_cancelar);
        final EditText pasword = (EditText) v.findViewById(R.id.contraseña);
        signin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (pasword.getText().toString().length() != 0) {
                            listener.onPossitiveButtonClick(pasword.getText().toString());
                        } else pasword.setError("el campo esta vacio");
                    }
                }

        );
        cancelar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        listener.onNegativeButtonClick();

                    }
                }

        );
        return builder.create();
    }

    public interface OnSimpleDialogListener {
        void onPossitiveButtonClick(String pasword);// Eventos Botón Positivo

        void onNegativeButtonClick();// Eventos Botón Negativo
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
