package com.m2.montano.j.localizadorcajeroscochalav2.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.m2.montano.j.localizadorcajeroscochalav2.DataBase.AdminSQLiteOpenHelper;
import com.m2.montano.j.localizadorcajeroscochalav2.Fragments.AnadirSitioFragment;
import com.m2.montano.j.localizadorcajeroscochalav2.Fragments.ErrorConexionFragment;
import com.m2.montano.j.localizadorcajeroscochalav2.Fragments.ListaNoConfirmadoFragment;
import com.m2.montano.j.localizadorcajeroscochalav2.Fragments.MapsFragment;
import com.m2.montano.j.localizadorcajeroscochalav2.Fragments.TodosCajerosFragment;
import com.m2.montano.j.localizadorcajeroscochalav2.MainActivity;
import com.m2.montano.j.localizadorcajeroscochalav2.Model.Cajero;
import com.m2.montano.j.localizadorcajeroscochalav2.R;
import com.m2.montano.j.localizadorcajeroscochalav2.Utils.AsynTaskUtil;
import com.m2.montano.j.localizadorcajeroscochalav2.Utils.AsyncTaskCallBackUtil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.util.List;

/**
 * Created by bvega on 25/04/2017.
 */

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    boolean isConectadoServer;
    int mostrarErrorConeccion = 0;
    public MapsFragment mapsFragment;
    public TodosCajerosFragment fragmentTodosCajeros;
    public List<Cajero> cajeros;
    HttpURLConnection urlc=null;
    AsynTaskUtil asynTaskUtil;
    private ProgressDialog pDialog;
    private MiTareaAsincronaDialog tarea2;
    Socket s;
    //URL url=new URL("http://www.google.com");

    public boolean getIsConectadoServer() {
        return isConectadoServer;
    }

    public void setConectadoServer(boolean conectadoServer) {
        isConectadoServer = conectadoServer;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    protected void loadProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
    }

    protected void showToast(int resIdMessage) {
        showToast(getResources().getString(resIdMessage));
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showLoadingDialog() {
        if (progressDialog != null) {
            progressDialog.show();
        } else {
            loadProgress();
            progressDialog.show();
        }
    }

    protected void closeLoadingDialog() {
        if (progressDialog == null)
            return;

        progressDialog.dismiss();
    }

    public void loadFragment(Fragment fragment, @IdRes int containerView) {
        FragmentManager fm=getSupportFragmentManager();
        fm.executePendingTransactions ();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(containerView, fragment);
        fragmentTransaction.commit();
    }

    public void showAlertDialog(MainActivity mainActivity, String title, String message, Boolean status) {
        //AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(title);

        builder.setMessage(message);

        builder.setIcon((status) ? R.drawable.bien : R.drawable.alarma);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mostrarErrorConeccion = 0;
            }
        });

        builder.show();
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    public void pin() {

        String dirWeb = "www.google.com";
        int puerto = 80;
        try{
            s = new Socket(dirWeb, puerto);
            //s.connect(dirWeb,80);
            //new Socket();
            if(s.isConnected()){
                System.out.println("Conexión establecida con la dirección: " +  dirWeb + " a travéz del puerto: " + puerto);
                setConectadoServer(true);
            }
        }catch(Exception e){
            System.err.println("No se pudo establecer conexión con: " + dirWeb + " a travez del puerto: " + puerto);
            setConectadoServer(false);
           // closeLoadingDialog();

        }
    }

    protected void comprobar(final int posicion) {
        asynTaskUtil = new AsynTaskUtil(new AsyncTaskCallBackUtil() {
            @Override
            public void onPreExecute() {
                showLoadingDialog();
            }

            @Override
            public void doInBackgroundCallBack() {
                pin();
            }

            @Override
            public void onPostExecute() {
                if (getIsConectadoServer()) {
                    //setFragment(posicion);
                    loadFragment(mapsFragment, R.id.fragment_content_main);
                } else {
                    //setFragment(posicion);
                    if (mostrarErrorConeccion == 0) {
                        mostrarErrorConeccion();
                        loadFragment(mapsFragment, R.id.fragment_content_main);
                        //actualizar(posicion);

                    }
                }
                closeLoadingDialog();
            }

            @Override
            public void onCancelled() {
                Toast.makeText(getBaseContext(), "Tarea cancelada!", Toast.LENGTH_SHORT).show();
            }
        });
        asynTaskUtil.execute();
    }


    public void actualizar(int posicion) {
        switch (posicion) {
            case 0:
                mapsFragment = new MapsFragment();
                loadFragment(mapsFragment, R.id.fragment_content_main);
                break;
            case 1:
                //MapsFragmentReserva mapsFragment = new MapsFragmentReserva();
                // mapsFragment.setEtith_mapa(false);
                //loadFragment(mapsFragment, R.id.fragment);
                break;
            case 2:
                //FragmentTodosCajeros fragmentTodosCajeros = new FragmentTodosCajeros();
                //loadFragment(fragmentTodosCajeros, R.id.fragment);
                break;
            case 3:
                //if (new Coneccion(this).isOnline()) {
                // MapsFragmentReserva mapsFragmentEdith = new MapsFragmentReserva();
                //mapsFragmentEdith.setEtith_mapa(true);
                //loadFragment(mapsFragmentEdith, R.id.fragment);
                //} else {
                // setFragment(0);
                // mostrarErrorConeccion();
                //}
                break;

            case 4:
                /*fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                SendLocationFragment sendLocationFragment = new SendLocationFragment();
                fragmentTransaction.replace(R.id.fragment, sendLocationFragment);
                fragmentTransaction.commit();
                break;*/
                break;
            case 10:
                //HomeErrorFragment homeErrorFragment = new HomeErrorFragment();
                //mapsFragmentEdith.setEtith_mapa(true);
                //loadFragment(homeErrorFragment, R.id.fragment);
                //break;

        }
    }

    public void setFragment(int position) {
        switch (position) {
            case 0:
                hayConeccion(position);
                break;
            case 1:
                fragmentTodosCajeros=new TodosCajerosFragment();
                loadFragment(fragmentTodosCajeros, R.id.fragment_content_main);
                break;
            case 2:
                loadFragment(new ListaNoConfirmadoFragment(),R.id.fragment_administrador);
               break;
            case 3:

                break;

            case 4:
               
            case 10:
               AnadirSitioFragment blankFragment=new AnadirSitioFragment();
                loadFragment(blankFragment,R.id.fragment_content_main);
                break;

        }
    }

    public void mostrarErrorConeccion() {
        //llenarDBlocal();
        if (cajeros.size()==0) {
            loadFragment(new ErrorConexionFragment(),R.id.fragment_content_main);
        }else{
            showAlertDialog((MainActivity) this, "Error de conexion a internet",
                    "Parece que no tiene habilita conexión a internet, se cargaran los datos locales.", false);
        }
        mostrarErrorConeccion++;
    }

    public void hayConeccion(int position) {
        if (!isOnline()) {
            llenarDBlocal();
            mostrarErrorConeccion();

        } else {
            comprobar(position);
        }
    }

    private void llenarDBlocal() {
        cajeros.clear();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "cajeros", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase(); //Create and/or open a database that will be used for reading and writing.
        Cursor c = bd.rawQuery("select * from cajero", null);//devuelve la lista de cajeros
        Toast.makeText(this, c.getCount()+"lista cursos", Toast.LENGTH_SHORT).show();
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                String nombre=c.getString(1);
                double latitude=c.getDouble(2);
                double longitude=c.getDouble(3);
                String direccion=c.getString(4);
                String horario=c.getString(5);
                String sitioWeb=c.getString(6);
                int valoracion=c.getInt(7);
                String nombreEF=c.getString(8);

                Cajero cajero= new Cajero(nombre,latitude,longitude,direccion,horario,sitioWeb, valoracion,nombreEF);
                cajeros.add(cajero);
                Log.d("cagero",":"+nombre);

                //txtResultado.append(" " + cod + " - " + nom + "\n");
            } while(c.moveToNext());
        }else    Toast.makeText(this, "no existe ningun cajero registrado",
                Toast.LENGTH_SHORT).show();
        bd.close();
        actualizarListaCajeros();
        loadFragment(mapsFragment, R.id.fragment_content_main);
        //actualizar(0);
    }

    public void actualizarListaCajeros() {
        if (mapsFragment != null) {
            mapsFragment.setCajeros(cajeros);

        }
        if (fragmentTodosCajeros != null) {
            fragmentTodosCajeros.setListaCajeros(cajeros);
        }
    }

    private class MiTareaAsincronaDialog extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            for(int i=1; i<=10; i++) {
                //tareaLarga();
               pin();
                publishProgress(i*10);
                //cancel(true);

                if(isCancelled()) {
                    try {
                       cancel(true);
                        s.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                }
            }

            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progreso = values[0].intValue();

            pDialog.setProgress(progreso);
        }

        @Override
        protected void onPreExecute() {

            pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    MiTareaAsincronaDialog.this.cancel(true);
                }
            });

            pDialog.setProgress(0);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result)
            {
                pDialog.dismiss();
                Toast.makeText(getBaseContext(), "Tarea finalizada!", Toast.LENGTH_SHORT).show();
            }
            if (getIsConectadoServer()) {
                //setFragment(posicion);
                mapsFragment = new MapsFragment();
                loadFragment(mapsFragment, R.id.fragment_content_main);
            } else {
                //setFragment(posicion);
                if (mostrarErrorConeccion == 0) {
                    mostrarErrorConeccion();
                    loadFragment(mapsFragment,R.id.fragment_content_main);
                    //actualizar(posicion);

                }
            }
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getBaseContext(), "Tarea cancelada!", Toast.LENGTH_SHORT).show();
        }
    }
    private void tareaLarga()
    {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {}
    }
}

