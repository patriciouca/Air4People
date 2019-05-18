package es.uca.air4people.air4people.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EstacionBD  extends SQLiteOpenHelper {

    //Sentencia SQL para crear la tabla de Estaciones
    String sqlCreate = "CREATE TABLE Estaciones (nombre TEXT)";

    public EstacionBD(Context contexto, String nombre,
                      SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Estaciones");
        db.execSQL(sqlCreate);
    }
}