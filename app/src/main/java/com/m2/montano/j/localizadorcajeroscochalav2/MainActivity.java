package com.m2.montano.j.localizadorcajeroscochalav2;

import android.Manifest;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.m2.montano.j.localizadorcajeroscochalav2.Activities.AdministradorMainActivity;
import com.m2.montano.j.localizadorcajeroscochalav2.Activities.AnadirSitioActivity;
import com.m2.montano.j.localizadorcajeroscochalav2.Activities.BaseActivity;
import com.m2.montano.j.localizadorcajeroscochalav2.DataBase.AdminSQLiteOpenHelper;
import com.m2.montano.j.localizadorcajeroscochalav2.Fragments.AnadirSitioFragment;
import com.m2.montano.j.localizadorcajeroscochalav2.Fragments.DetalleLugarFragment;
import com.m2.montano.j.localizadorcajeroscochalav2.Fragments.DialogFrag;
import com.m2.montano.j.localizadorcajeroscochalav2.Fragments.DialogRuta;
import com.m2.montano.j.localizadorcajeroscochalav2.Fragments.ErrorConexionFragment;
import com.m2.montano.j.localizadorcajeroscochalav2.Fragments.MapsFragment;
import com.m2.montano.j.localizadorcajeroscochalav2.Fragments.TodosCajerosFragment;
import com.m2.montano.j.localizadorcajeroscochalav2.Model.Cajero;
import com.m2.montano.j.localizadorcajeroscochalav2.Utils.AsynTaskUtil;
import com.m2.montano.j.localizadorcajeroscochalav2.Utils.AsyncTaskCallBackUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, ErrorConexionFragment.OnFragmentInteractionListenerErrorConexion,
        TodosCajerosFragment.ListaTodosCajeros, DetalleLugarFragment.OnDetalleLugarFragmentInteractionListener,
        MapsFragment.OnFragmentMapInteractionListener, DialogRuta.OnSimpleDialogRuta
        , AnadirSitioFragment.OnFragmentInteractionListener, DialogFrag.OnSimpleDialogListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.ic_buscar)
    ImageView icBuscar;
    private LocationManager mlocManager;
    private GoogleApiClient apiClient;
    private static final String LOGTAG = "android-localización";

    private static final int PETICION_PERMISO_LOCALIZACION = 101;
    private static final int PETICION_CONFIG_UBICACION = 201;
    private LocationRequest locRequest;
    public double latitud, longitud;
    FirebaseDatabase database;
    DialogFrag dialogFrag;
    DialogRuta dialogRuta;
    Cajero cajeroDestino;
    ActionBar actionBar;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String TAG = "auth";
    private String extra;
    private int REQUEST_GOOGLE_PLAY_SERVICES = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Principal");

        cajeros = new ArrayList<Cajero>();
        //hayConeccion(0);
        mapsFragment = new MapsFragment();
        pruebaHayConeccion(0);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        autentificacion();
        cargarCajerosFirebase();
        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        } else {
            locationStart();
        }

    }
    private void setUpActionBar() {
        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            android.app.ActionBar actionBar = getActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void pruebaHayConeccion(int pos) {
        if (!isOnline()) {
            llenarDBlocal();
            mostrarErrorConexion2();
        } else {
            mostrarDrawer();
            loadFragment(mapsFragment, R.id.fragment_content_main);

        }
    }

    private void mostrarErrorConexion2() {
        if (cajeros.size() == 0) {
            loadFragment(new ErrorConexionFragment(), R.id.fragment_content_main);
        } else {
            mostrarDrawer();
            showAlertDialog((MainActivity) this, "Error de conexion a internet",
                    "Parece que no tiene habilita conexión a internet, se cargaran los datos locales.", false);
        }
    }


    public void mostrarDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);
    }


    private void autentificacion() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    extra = user.getEmail();
                    closeLoadingDialog();
                    startActivity(new Intent(MainActivity.this, AdministradorMainActivity.class));
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    private void limpiarDB() {
        getApplicationContext().deleteDatabase("cajeros");
    }

    private void llenarDBlocal() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,
                "cajeros", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase(); //Create and/or open a database that will be used for reading and writing.
        Cursor c = bd.rawQuery("select * from cajero", null);//devuelve la lista de cajeros
        Toast.makeText(this, c.getCount() + "lista cursos", Toast.LENGTH_SHORT).show();
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                String nombre = c.getString(1);
                double latitude = c.getDouble(2);
                double longitude = c.getDouble(3);
                String direccion = c.getString(4);
                String horario = c.getString(5);
                String sitioWeb = c.getString(6);
                int valoracion = c.getInt(7);
                String nombreEF = c.getString(8);

                Cajero cajero = new Cajero(nombre, latitude, longitude, direccion, horario, sitioWeb, valoracion, nombreEF);
                cajeros.add(cajero);
                Log.d("cagero", ":" + nombre);

            } while (c.moveToNext());
        } else Toast.makeText(this, "no existe ningun cajero registrado",
                Toast.LENGTH_SHORT).show();
        bd.close();
        actualizarListaCajeros();
        //ctualizar(0);
        loadFragment(mapsFragment, R.id.fragment_content_main);
    }

    private void locationStart() {

        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(settingsIntent, 1);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            actionBar.setTitle("Principal");
            icBuscar.setVisibility(View.VISIBLE);
            //mapsFragment=new MapsFragment();
            setFragment(0);
            //mapsFragment.onStart();
        } else if (id == R.id.cercanos_a_mi_ubcacion) {
            actionBar.setTitle("Cajeros Cercanos");
            icBuscar.setVisibility(View.VISIBLE);
            mapsFragment.setListaCajeros(cajeros);
            loadFragment(mapsFragment, R.id.fragment_content_main);
            // mapsFragment.cercanos();
            esperar2();
        } else if (id == R.id.nav_todos_cajeros) {
            actionBar.setTitle("Lista de Cajeros");
            icBuscar.setVisibility(View.INVISIBLE);
            setFragment(1);
            if (cajeros.size() > 0) {
                Toast.makeText(this, cajeros.size() + "", Toast.LENGTH_SHORT).show();
                esperar();
            }

        } else if (id == R.id.anadir_cajero) {
            item.setEnabled(true);
            Intent intent = new Intent(this, AnadirSitioActivity.class);
            startActivity(intent);

        } else if (id == R.id.ajustes) {
            //setFragment(2);
            dialogFrag = new DialogFrag();
            dialogFrag.show(getSupportFragmentManager(), "SimpleDialog");
            //eliminarCajero(7);
        } else if (id == R.id.nav_acerca_de) {
            showAlertDialog(this, "          Acerca de", "Buscar ATM es una aplicación diseñada para localizar, visualizar, buscar cajeros automáticos" +
                    " que se encuentren dentro de la Provincia de Cercado del Departamento de Cochabamba", true);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void eliminarCajero(int id) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "cajeros", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        bd.delete("cajero", "idCajero=" + id, null);
        bd.close();
    }

    private void esperar2() {
        AsynTaskUtil asynTaskUtil = new AsynTaskUtil(new AsyncTaskCallBackUtil() {
            @Override
            public void onPreExecute() {
                showLoadingDialog();
            }

            @Override
            public void doInBackgroundCallBack() {

                esperarTiempo();
            }

            @Override
            public void onPostExecute() {
                mapsFragment.setListaCajeros(cajeros);
                mapsFragment.cercanos();
                closeLoadingDialog();
            }

            @Override
            public void onCancelled() {

            }
        });
        asynTaskUtil.execute();
    }

    public void esperar() {
        AsynTaskUtil asynTaskUtil = new AsynTaskUtil(new AsyncTaskCallBackUtil() {
            @Override
            public void onPreExecute() {
                showLoadingDialog();
            }

            @Override
            public void doInBackgroundCallBack() {

                esperarTiempo();
            }

            @Override
            public void onPostExecute() {
                fragmentTodosCajeros.setListaCajeros(cajeros);
                closeLoadingDialog();
            }

            @Override
            public void onCancelled() {

            }
        });
        asynTaskUtil.execute();
    }

    private void esperarTiempo() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
    }


    public void cargarCajerosFirebase() {
        database = FirebaseDatabase.getInstance();
        final DatabaseReference locais = database.getReference("CajerosConfirmados");

        locais.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();
                cajeros.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshots) {
                    Cajero cajero = dataSnapshot1.getValue(Cajero.class);
                    cajeros.add(cajero);
                    cargarDataBaseLocal(cajero);
                    //displayNotification();
                    verificarNotificado(cajero, locais);

                }
                actualizarListaCajeros();
                closeLoadingDialog();
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    private void verificarNotificado(Cajero caj, DatabaseReference data) {
        if (caj.getNotificado() == 0) {
       displayNotification(caj);
       data.child(caj.getNombre()).child("notificado").setValue(1);
        }
    }

    private void displayNotification(Cajero caj) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Prueba")
                .setContentText("hola notificacion")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

    private void actulizarPantalla() {
        loadFragment(mapsFragment, R.id.fragment_content_main);
    }

    public void cargarDataBaseLocal(Cajero cajero) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "cajeros", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();  //es una clase para guardar datos
        //registro.put("idCajero","C"+indice);
        registro.put("nombre", cajero.getNombre().toString());
        registro.put("latitude", cajero.getLatitude() + "".toString());
        registro.put("longitude", cajero.getLongitude() + "".toString());
        registro.put("direccion", cajero.getDireccion().toString());
        registro.put("horario", cajero.getHorario().toString());
        registro.put("sitioWeb", cajero.getSitioWeb().toString());
        registro.put("valoracion", cajero.getValoracion() + "".toString());
        registro.put("nombreEF", cajero.getNombreEF().toString());
        long i = bd.insert("cajero", null, registro);
        if (i > 0) {
            //Toast.makeText(this, "Se cargaron los datos del cajero",
                   // Toast.LENGTH_SHORT).show();
        } else //Toast.makeText(this, "NO Se cargaron los datos de la persona",
            //Toast.LENGTH_SHORT).show();
            bd.close();
    }


    //////metodos de interface
    @Override
    public void seleccionarCajero(Cajero item) {

        mapsFragment.setEtith_mapa(false);
        mapsFragment.setClip_rv(true);
        mapsFragment.setCajero_rv(item);
        //mapsFragment.setCajeros(cajeros);
        loadFragment(mapsFragment, R.id.fragment_content_main);
    }

    @Override
    public void MostrarTodosEnMapa() {
        mapsFragment.setEtith_mapa(false);
        mapsFragment.setClip_rv(false);
        mapsFragment.setMarcarRuta(false);
        mapsFragment.setListarTodos(true);
        loadFragment(mapsFragment, R.id.fragment_content_main);
    }

    @Override
    public void cambiarTitleActionBar() {
        actionBar.setTitle("Información de Cajero");
    }

    @Override
    public void setFragmentError() {
        //mostrarErrorConeccion();
        llenarDBlocal();
        if (cajeros.size() == 0) {
            loadFragment(new ErrorConexionFragment(), R.id.fragment_content_main);
        } else {
            mostrarDrawer();
            showAlertDialog((MainActivity) this, "Error de conexion a internet",
                    "Parece que no tiene habilita conexión a internet, se cargaran los datos locales.", false);
        }

    }

    @Override
    public void setFragmentPrincipal() {
        mostrarDrawer();
        loadFragment(mapsFragment, R.id.fragment_content_main);
    }

    @Override
    public void onInvokeGooglePlayServices(int codeError) {
        showPlayServicesErrorDialog(codeError);
    }

    void showPlayServicesErrorDialog(final int errorCode) {
        Dialog dialog = GoogleApiAvailability.getInstance()
                .getErrorDialog(
                        this,
                        errorCode,
                        REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    // interface de la clase DetalleLugarFragment
    public void onDetalleLugarFragmentInteraction(final Cajero cajero_rv) {
        Toast.makeText(this, "fragment simple", Toast.LENGTH_SHORT).show();
        //mapsFragment=map;
        cajeroDestino = cajero_rv;
        dialogRuta = new DialogRuta();
        dialogRuta.show(getSupportFragmentManager(), "SimpleDialog");

    }

    @Override
    public void mostrarEnMapa(Cajero cajero) {
        mapsFragment.setClip_rv(true);
        mapsFragment.setCajero_rv(cajero);
        loadFragment(mapsFragment, R.id.fragment_content_main);
    }

    @Override
    public void onPossitiveButtonClick(String pas) {
        comprobarUsuario(pas);
        dialogFrag.dismiss();
        showLoadingDialog();
    }

    private void comprobarUsuario(String pas) {
        String e = "admin@gmail.com";
        String pa = pas;
        mAuth.signInWithEmailAndPassword(e, pa)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createSigninWithEmail:onComplete:" + task.isSuccessful());
                            //extra=task.getResult().getUser().getEmail();
                            // Log.d("usuario email", extra);
                        }

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        else {
                            Toast.makeText(MainActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                            closeLoadingDialog();
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onNegativeButtonClick() {
        dialogFrag.dismiss();
    }

    @Override
    public void trazarRutaPie() {
        mapsFragment.setClip_rv(false);
        mapsFragment.setEtith_mapa(false);
        mapsFragment.setDecisionPieMovil(true);
        mapsFragment.setMarcarRuta(true);
        mapsFragment.setCajero_rv(cajeroDestino);
        loadFragment(mapsFragment, R.id.fragment_content_main);
        dialogRuta.dismiss();
    }

    @Override
    public void trazarRutaMovil() {
        mapsFragment.setClip_rv(false);
        mapsFragment.setEtith_mapa(false);
        mapsFragment.setDecisionPieMovil(false);
        mapsFragment.setMarcarRuta(true);
        mapsFragment.setCajero_rv(cajeroDestino);
        loadFragment(mapsFragment, R.id.fragment_content_main);
        dialogRuta.dismiss();
    }

    @OnClick(R.id.ic_buscar)
    public void onViewClicked() {
        actionBar.setTitle("Lista de Cajeros");
        icBuscar.setVisibility(View.INVISIBLE);
        setFragment(1);
        if (cajeros.size() > 0) {
            Toast.makeText(this, cajeros.size() + "", Toast.LENGTH_SHORT).show();
            esperar();
        }
    }

    @Override
    public void volverIntentar() {
        actionBar.setTitle("Principal");
        //icBuscar.setVisibility(View.VISIBLE);

        setFragment(0);
        if (isOnline()) {
            mostrarDrawer();
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

}
