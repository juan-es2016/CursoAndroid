package com.m2.montano.j.localizadorcajeroscochalav2.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Juan on 9/7/2017.
 */

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table cajero(idCajero integer primary key autoincrement,nombre text , latitude double unique, longitude double unique, direccion text,horario text,sitioWeb text,valoracion integer,nombreEF text)");
        //db.execSQL("create table cajero3(dni integer primary key, nombre text, colegio text, nromesa integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists cajero");
        db.execSQL("create table cajero(idCajero integer primary key autoincrement ,nombre text , latitude double unique, longitude double unique, direccion text,horario text,sitioWeb text,valoracion integer,nombreEF text)");
        //db.execSQL("create table cajero3(dni integer primary key, nombre text, colegio text, nromesa integer)");
    }
}
