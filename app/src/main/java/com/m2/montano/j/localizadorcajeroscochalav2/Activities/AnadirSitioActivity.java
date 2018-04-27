package com.m2.montano.j.localizadorcajeroscochalav2.Activities;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.m2.montano.j.localizadorcajeroscochalav2.Fragments.HomeMapsFragment;
import com.m2.montano.j.localizadorcajeroscochalav2.Model.Cajero;
import com.m2.montano.j.localizadorcajeroscochalav2.R;
import com.m2.montano.j.localizadorcajeroscochalav2.Utils.AsynTaskUtil;
import com.m2.montano.j.localizadorcajeroscochalav2.Utils.AsyncTaskCallBackUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnadirSitioActivity extends BaseActivity implements HomeMapsFragment.OnFragmentInteractionListener {

    private static Cajero cajero1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ic_enviar)
    ImageView icEnviar;
    @BindView(R.id.nombre_cajero)
    EditText nombreCajero;
    @BindView(R.id.direccion)
    EditText direccion;
    @BindView(R.id.selecionar_lugar)
    FrameLayout selecionarLugar;
    @BindView(R.id.horario)
    Button horario;
    @BindView(R.id.sitio_web)
    EditText sitioWeb;
    @BindView(R.id.nombreEF)
    Button nombreEF;
    private boolean[] items_checked = {true, false, false, false, false, false, false, true};
    private ArrayList<Integer> itemsSeleccionados;
    private String seleccionado = "";
    private String[] dias;
    private int hora, minutos;
    private String inicio, fin;
    private String[] entidades_financiera;
    private int request_code = 1;
    HomeMapsFragment homeMapsFragment;
    private Geocoder geocoder;
    private List<Address> list;
    private double latitud, longitud;
    private Session session;

    public void setCajeros(List<Cajero> cajeros) {
        this.cajeros = cajeros;
    }

    List<Cajero> cajeros;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_sitio_actvity);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.añade_sitio);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cajero1 = null;
                finish();
            }
        });

        homeMapsFragment = new HomeMapsFragment();
        //FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        // fragmentTransaction.replace(R.id.selecionar_lugar, homeMapsFragment);
        //fragmentTransaction.commit();
        if (cajero1 != null) {
            llenarVista();
        } else {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.selecionar_lugar, homeMapsFragment);
            fragmentTransaction.commit();
        }

        itemsSeleccionados = new ArrayList();
        itemsSeleccionados.add(0);
        itemsSeleccionados.add(7);

        list=new ArrayList<>();


        dias = getResources().getStringArray(R.array.valores_array_dias);
        entidades_financiera = getResources().getStringArray(R.array.nombres_entidad_financiera);
    }

    private void llenarVista() {
        nombreCajero.setText(cajero1.getNombre().toString());
        direccion.setText(cajero1.getDireccion().toString());
        horario.setText(cajero1.getHorario().toString());
        sitioWeb.setText(cajero1.getSitioWeb().toString());
        nombreEF.setText(cajero1.getNombreEF().toString());
        latitud = cajero1.getLatitude();
        longitud = cajero1.getLongitude();
        homeMapsFragment.setCajero(cajero1);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.selecionar_lugar, homeMapsFragment);
        fragmentTransaction.commit();
    }

    ////metodo de interface de homeFragment
    @Override
    public void onFragmentInteraction() {
        Intent intent = new Intent(this, MarcarPuntoMapaActivity.class);
        startActivityForResult(intent, request_code);
    }

    @Override
    public void CodenadasPuntoMapa(LatLng punto) {

    }

    @OnClick({R.id.nombreEF, R.id.horario, R.id.ic_enviar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.nombreEF:
                seleccionarNombreEntidadFinanciera();
                break;
            case R.id.horario:
                mostrarDialogoHorario();
                break;
            case R.id.ic_enviar:
                showLoadingDialog();
                salvarDatos();
                cajero1 = null;
                //finish();
                break;
        }
    }

    private void salvarDatos() {
        DatabaseReference myRef;
        if (nombreCajero.getText().length() != 0) {
            if (direccion.getText().length() != 0) {
                if (latitud != 0 && longitud != 0) {
                    if (nombreEF.getText().length() != 0){
                        if (cajero1 != null) {
                            myRef = FirebaseDatabase.getInstance().getReference("CajerosConfirmados");
                        } else
                            myRef = FirebaseDatabase.getInstance().getReference("CajerosNoConfirmados");

                    final String nombre = nombreCajero.getText().toString();

                    final String editDireccion = direccion.getText().toString();
                    final String editHorario = horario.getText().toString();
                    final String editSitioWeb = sitioWeb.getText().toString();
                    final String editNombreEF = nombreEF.getText().toString();
                    Cajero cajero = new Cajero();

                    cajero.setNombre(nombre);
                    cajero.setLatitude(latitud);
                    cajero.setLongitude(longitud);
                    cajero.setDireccion(editDireccion);
                    cajero.setHorario(editHorario);
                    cajero.setSitioWeb(editSitioWeb);
                    cajero.setNombreEF(editNombreEF);
                    cajero.setValoracion(1);
                    cajero.setNotificado(0);

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put(nombre, cajero.toMap());
                    myRef.updateChildren(childUpdates);

                    closeLoadingDialog();
                 }else nombreEF.setError(getString(R.string.campo_vacio));
                } else
                    Toast.makeText(this, "Por favor marque la ubicación en el mapa", Toast.LENGTH_SHORT).show();
            } else direccion.setError(getString(R.string.campo_vacio));
            //finish();
        } else {
            //some logic
            nombreCajero.setError(getString(R.string.campo_vacio));

            //d.dismiss();
        }
        if (cajero1 != null) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            ref.child("CajerosNoConfirmados").child(cajero1.getNombre()).removeValue();
            //enviarMensaje();
            finish();
        } else {
            if (latitud != 0 && longitud != 0) {
                enviarMensaje();
            }else closeLoadingDialog();
            //finish();
        }
    }

    private void seleccionarNombreEntidadFinanciera() {
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        final String[] nombreEFaux = {entidades_financiera[0]};
        builder2.setTitle("Seleccionar nombre de entidad financiera")
                .setSingleChoiceItems(R.array.nombres_entidad_financiera, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                nombreEFaux[0] = entidades_financiera[item];
                            }
                        })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nombreEF.setText(nombreEFaux[0]);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


        builder2.show();
    }

    private void mostrarDialogoHorario() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Seleccionar dias")
                .setMultiChoiceItems(R.array.valores_array_dias, items_checked, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            itemsSeleccionados.add(which);
                            Toast.makeText(AnadirSitioActivity.this, "selecionados :" + itemsSeleccionados.size(), Toast.LENGTH_SHORT).show();
                        } else if (itemsSeleccionados.contains(which)) {
                            itemsSeleccionados.remove(Integer.valueOf(which));
                        }

                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        llenarHorario(itemsSeleccionados, horario);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder1.show();
    }

    public void llenarHorario(ArrayList<Integer> itemsSeleccionados, final Button horario) {

        seleccionado = "";
        String horas = "";
        if (itemsSeleccionados.size() == 8) {
            String inicio = dias[0];
            String fin = dias[6];
            horario.setText(inicio.substring(0, 3) + "-" + fin.substring(0, 3) + "," + "24h");
            llenarItemsChecked(itemsSeleccionados);
        } else {
            for (int i = 0; i < itemsSeleccionados.size(); i++) {
                if (itemsSeleccionados.get(i) != 7) {
                    Integer aux = itemsSeleccionados.get(i);
                    seleccionado = seleccionado + dias[aux].substring(0, 3) + ", ";
                } else {
                    horas = dias[7].substring(8, 12);
                }
            }
            if (horas == "") {
                final Calendar c = Calendar.getInstance();
                hora = c.get(Calendar.HOUR_OF_DAY);
                minutos = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Toast.makeText(AnadirSitioActivity.this, "horario:" + hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
                        horarioInicio(hourOfDay + ":" + minute);
                        final Calendar c2 = Calendar.getInstance();
                        hora = c2.get(Calendar.HOUR_OF_DAY);
                        minutos = c2.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog2 = new TimePickerDialog(AnadirSitioActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                //Toast.makeText(AnadirSitioActivity.this, "horario:" + hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
                                horarioFin(hourOfDay + ":" + minute);
                                llenarhorario(horario, seleccionado);
                            }
                        }, hora, minutos, false);
                        timePickerDialog2.show();
                    }
                }, hora, minutos, false);
                timePickerDialog.show();
            }
            llenarItemsChecked(itemsSeleccionados);
            horario.setText(seleccionado + horas);

        }
    }

    public void llenarhorario(Button horario, String seleccionado) {
        String horas = inicio + " - " + fin;
        horario.setText(seleccionado + horas);
    }

    public void llenarItemsChecked(ArrayList<Integer> itemsSeleccionados) {
        limpiarItemsChecked();
        for (int i = 0; i < itemsSeleccionados.size(); i++) {
            items_checked[itemsSeleccionados.get(i)] = true;
        }

    }

    public void horarioInicio(String inicioh) {
        inicio = inicioh;
    }

    public void horarioFin(String finh) {
        fin = finh;
    }

    public void limpiarItemsChecked() {
        for (int i = 0; i < items_checked.length; i++) {
            items_checked[i] = false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if ((requestCode == request_code) && (resultCode == RESULT_OK)) {
            //tvTexto.setText(data.getDataString()+ data.getStringExtra("prueba extra"));
            Toast.makeText(this, "punto mapa", Toast.LENGTH_SHORT).show();
            //nombreCajero.setText(data.getDataString() + data.getDoubleExtra("latitud", 0.0));
            //direccion.setText(data.getDataString() + data.getDoubleExtra("longitud", 0.0));
            homeMapsFragment.setLatitudLongitud(data.getDoubleExtra("latitud", 0.0), data.getDoubleExtra("longitud", 0.0));
            homeMapsFragment.marcarPunto();
            geocoder = new Geocoder(homeMapsFragment.getContext(), Locale.getDefault());
            latitud = data.getDoubleExtra("latitud", 0.0);
            longitud = data.getDoubleExtra("longitud", 0.0);
            llenarDireccion(latitud, longitud);
        }
    }

    private void llenarDireccion(final double lati, final double longi) {

        AsynTaskUtil asynTaskUtil = new AsynTaskUtil(new AsyncTaskCallBackUtil() {
            @Override
            public void onPreExecute() {
            }

            @Override
            public void doInBackgroundCallBack() {
                try {
                    list = geocoder.getFromLocation(lati, longi, 1);
                } catch (IOException e) {
                  Log.e("error geocoder",""+e+list)  ;
                }
            }

            @Override
            public void onPostExecute() {

                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    if (DirCalle.getLocality() != null && DirCalle.getCountryName() != null) {
                        direccion.setTextKeepState(DirCalle.getAddressLine(0).toString() + "," + DirCalle.getLocality() + "," + DirCalle.getCountryName());
                        //progressDialog.dismiss();
                    } else
                        Toast.makeText(AnadirSitioActivity.this, "no hay locality", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled() {

            }
        });
        asynTaskUtil.execute();
    }

    public static void llenarDatosCajero(Cajero cajero) {
        cajero1 = cajero;
    }

    private void enviarMensaje() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("j.montano.m2@gmail.com", "elizabet2013");
            }
        });

        //pdialog = ProgressDialog.show(this, "", "Sending Mail...", true);

        //Main2Activity.RetreiveFeedTask task = new Main2Activity.RetreiveFeedTask();
        //task.execute();
        AsynTaskUtil asynTaskUtil = new AsynTaskUtil(new AsyncTaskCallBackUtil() {
            @Override
            public void onPreExecute() {
                showLoadingDialog();
            }

            @Override
            public void doInBackgroundCallBack() {
                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress("testfrom354@gmail.com"));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("juan-es20@hotmail.com"));
                    message.setSubject("Confirmar registro de cajero");
                    message.setContent("se agrego un nuevo cajero, por favor acceda a la aplicacion y confirme", "text/html; charset=utf-8");
                    Transport.send(message);
                } catch (MessagingException e) {
                   Log.e("error de coneccion sms",e+"");// e.printStackTrace();
                } catch (Exception e) {
                    //e.printStackTrace();
                    Log.e("error de coneccion sms",e+"");
                }
            }

            @Override
            public void onPostExecute() {
                closeLoadingDialog();
                Toast.makeText(getApplicationContext(), "Se ha enviado un mensaje al administrador para su verificación", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onCancelled() {

            }
        });
        asynTaskUtil.execute();
    }
}
