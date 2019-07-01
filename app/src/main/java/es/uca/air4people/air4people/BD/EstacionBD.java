package es.uca.air4people.air4people.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EstacionBD  extends SQLiteOpenHelper {

    //Sentencia SQL para crear la tabla de Estaciones
    String sqlCreate = "CREATE TABLE Estaciones (nombre TEXT NOT NULL UNIQUE)";

    String sqlCreate2 = "CREATE TABLE Suscripciones (nombre TEXT NOT NULL UNIQUE)";

    String sqlCreate3 = "CREATE TABLE Notificaciones (titulo TEXT NOT NULL UNIQUE,cuerpo TEXT NOT NULL UNIQUE)";

    public EstacionBD(Context contexto, String nombre,
                      SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
        db.execSQL(sqlCreate2);
        db.execSQL(sqlCreate3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Estaciones");
        db.execSQL(sqlCreate);
        db.execSQL("DROP TABLE IF EXISTS Suscripciones");
        db.execSQL(sqlCreate2);
        db.execSQL("DROP TABLE IF EXISTS Notificaciones");
        db.execSQL(sqlCreate3);
    }
}