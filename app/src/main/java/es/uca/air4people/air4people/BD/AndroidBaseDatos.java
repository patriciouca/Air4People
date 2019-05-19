package es.uca.air4people.air4people.BD;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.Servicio.Estacion;

public class AndroidBaseDatos extends Activity{

    Context contexto;

    public AndroidBaseDatos(Context contexto) {
        this.contexto = contexto;
    }

    public ArrayList<String> getEstaciones(){
        EstacionBD usdbh =
                new EstacionBD(contexto, "DBEstaciones", null, 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        ArrayList<String> estaciones=new ArrayList<>();
        if(db != null) {
            Cursor c = db.rawQuery("SELECT * FROM Estaciones", null);

            if (c.moveToFirst()) {
                do {
                    String nombre = c.getString(0);
                    estaciones.add(nombre);
                } while (c.moveToNext());
            }
            db.close();
        }

        return estaciones;
    }

    public void deleteEstacion(String nombre)
    {
        EstacionBD usdbh =
                new EstacionBD(contexto, "DBEstaciones", null, 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        if(db != null) {
            db.execSQL("DELETE FROM Estaciones WHERE nombre='" + nombre + "' ");
            db.close();
        }
    }

    public void addEstacion(String nombre)
    {
        EstacionBD usdbh =
                new EstacionBD(contexto, "DBEstaciones", null, 1);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        if(db != null)
        {
            db.execSQL("INSERT INTO Estaciones (nombre) " +
                    "VALUES ( '" + nombre +"')");

            db.close();
        }
    }


}
