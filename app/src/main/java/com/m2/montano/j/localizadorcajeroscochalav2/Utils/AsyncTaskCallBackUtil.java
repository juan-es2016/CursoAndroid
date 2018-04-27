package com.m2.montano.j.localizadorcajeroscochalav2.Utils;

/**
 * Created by bvega on 09/05/2017.
 */

public interface AsyncTaskCallBackUtil {
        void onPreExecute();
        void doInBackgroundCallBack();
        void onPostExecute();
        void onCancelled();
}
