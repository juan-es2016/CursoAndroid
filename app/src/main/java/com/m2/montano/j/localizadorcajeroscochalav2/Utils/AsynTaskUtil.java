package com.m2.montano.j.localizadorcajeroscochalav2.Utils;

import android.os.AsyncTask;

/**
 * Created by Juan on 9/1/2017.
 */

public class AsynTaskUtil extends AsyncTask<Void, Integer, Void>  {

    AsyncTaskCallBackUtil listener;

    public AsynTaskUtil(AsyncTaskCallBackUtil listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        listener.doInBackgroundCallBack();

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.onPostExecute();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        listener.onCancelled();
    }

}
