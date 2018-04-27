package com.m2.montano.j.localizadorcajeroscochalav2.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.m2.montano.j.localizadorcajeroscochalav2.R;


/**
 * Created by Juan on 20/04/2017.
 */

public class BaseFragment extends Fragment {

    private ProgressDialog progress;
    //FirebaseDatabase database = FirebaseDatabase.getInstance();
   // DatabaseReference locais;
    //ArrayList<Cajero> cajeros;
    TodosCajerosFragment todosCajerosFragment;
    int tipoFragment=0;

    public BaseFragment() {
        //cajeros = new ArrayList<>();
        //locais = database.getReference("locais");
        //fragmentTodosCajeros=new FragmentTodosCajeros();
        //llenarListaFirebase();

    }

    protected void loadProgress() {
        progress = new ProgressDialog(getContext());
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setCancelable(false);
    }

    protected void showToast(int resIdMessage) {
        showToast(getResources().getString(resIdMessage));
    }

    protected void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected void showToastError() {
        Toast.makeText(getContext(), R.string.default_error, Toast.LENGTH_LONG).show();
    }

    protected void showLoadingDialog() {
        if (progress != null) {
            progress.show();
        } else {
            loadProgress();
            progress.show();
        }
    }

    protected void closeLoadingDialog() {
        if (progress == null)
            return;

        progress.dismiss();
    }

    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        //AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(title);

        builder.setMessage(message);

        builder.setIcon((status) ? R.drawable.bien : R.drawable.alarma);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }
}
