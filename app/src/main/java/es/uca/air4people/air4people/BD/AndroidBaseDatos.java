package es.uca.air4people.air4people.BD;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import es.uca.air4people.air4people.ContaminacionHelper;
import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.ReciclerSuscripciones.Suscripcion;
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

    public ArrayList<Suscripcion> getSuscripciones(){
        EstacionBD usdbh =
                new EstacionBD(contexto, "DBEstaciones", null, 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        ArrayList<Suscripcion> suscripciones=new ArrayList<>();
        if(db != null) {
            Cursor c = db.rawQuery("SELECT * FROM Suscripciones", null);

            if (c.moveToFirst()) {
                do {
                    Suscripcion s=new Suscripcion(c.getString(0),
                            c.getInt(1));
                    suscripciones.add(s);
                } while (c.moveToNext());
            }
            db.close();
        }

        return suscripciones;
    }

    public ArrayList<String[]> getNotificaciones(){
        EstacionBD usdbh =
                new EstacionBD(contexto, "DBEstaciones", null, 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        ArrayList<String[]> notificaciones=new ArrayList<>();
        if(db != null) {
            Cursor c = db.rawQuery("SELECT * FROM Notificaciones", null);

            if (c.moveToFirst()) {
                do {
                    String vector[]=new String[2];
                    String titulo = c.getString(0);
                    String cuerpo = c.getString(1);
                    vector[0]=titulo;
                    vector[1]=cuerpo;
                    notificaciones.add(vector);
                } while (c.moveToNext());
            }
            db.close();
        }

        return notificaciones;
    }

    public ArrayList<String> getPatologias(){
        EstacionBD usdbh =
                new EstacionBD(contexto, "DBEstaciones", null, 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        ArrayList<String> suscripciones=new ArrayList<>();
        if(db != null) {
            Cursor c = db.rawQuery("SELECT * FROM Patologias", null);

            if (c.moveToFirst()) {
                do {
                    String nombre = c.getString(0);
                    suscripciones.add(nombre);
                } while (c.moveToNext());
            }
            db.close();
        }

        return suscripciones;
    }

    public void deleteEstacion(String nombre)
    {
        EstacionBD usdbh =
                new EstacionBD(contexto, "DBEstaciones", null, 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        if(db != null) {
            db.execSQL("DELETE FROM Estaciones WHERE nombre='" + nombre + "' ");
            db.close();

            ArrayList<Suscripcion> suscripciones=getSuscripciones();
            for (int i=0;i<suscripciones.size();i++)
            {
                Suscripcion suscripcion=suscripciones.get(i);
                String cadena=procesarNombre(suscripcion.nombre,suscripcion.nivel);
                FirebaseMessaging.getInstance().unsubscribeFromTopic(nombre+"_"+cadena);
            }

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


            ArrayList<Suscripcion> suscripciones=getSuscripciones();
            for (int i=0;i<suscripciones.size();i++)
            {
                String suscripcion=suscripciones.get(i).nombre;
                int nivel=suscripciones.get(i).nivel;
                suscripcion=procesarNombre(suscripcion,nivel);
                FirebaseMessaging.getInstance().subscribeToTopic(nombre+"_"+suscripcion);
            }

        }
    }

    public void addNotificacion(String titulo,String cuerpo)
    {
        EstacionBD usdbh =
                new EstacionBD(contexto, "DBEstaciones", null, 1);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        if(db != null)
        {
            db.execSQL("INSERT INTO Notificaciones (titulo,cuerpo) " +
                    "VALUES ( '" + titulo +"'"+","+"'"+cuerpo+"')");

            db.close();
        }
    }

    public void deleteSuscripcion(String nombre,int nivel)
    {
        EstacionBD usdbh =
                new EstacionBD(contexto, "DBEstaciones", null, 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        if(db != null) {
            db.execSQL("DELETE FROM Suscripciones WHERE nombre='" + nombre + "' AND " +
                    "nivel="+nivel);
            db.close();

            ArrayList<String> estaciones=getEstaciones();
            nombre=procesarNombre(nombre,nivel);
            for (int i=0;i<estaciones.size();i++)
            {
                String estacion=estaciones.get(i);
                FirebaseMessaging.getInstance().unsubscribeFromTopic(estacion+"_"+nombre);
            }

        }
    }

    public void addSuscripcion(String nombre,int nivel)
    {
        EstacionBD usdbh =
                new EstacionBD(contexto, "DBEstaciones", null, 1);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        if(db != null)
        {
            db.execSQL("INSERT INTO Suscripciones (nombre,nivel) " +
                    "VALUES ( '" + nombre +"',"+nivel+")");

            db.close();
        }

        ArrayList<String> estaciones=getEstaciones();
        nombre=procesarNombre(nombre,nivel);
        for (int i=0;i<estaciones.size();i++)
        {
            String estacion=estaciones.get(i);
            FirebaseMessaging.getInstance().subscribeToTopic(estacion+"_"+nombre);
        }


    }

    public void deletePatologia(String nombre)
    {
        EstacionBD usdbh =
                new EstacionBD(contexto, "DBEstaciones", null, 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();
        if(db != null) {
            db.execSQL("DELETE FROM Patologias WHERE nombre='" + nombre + "' ");
            db.close();
        }
    }

    public void addPatologia(String nombre)
    {
        EstacionBD usdbh =
                new EstacionBD(contexto, "DBEstaciones", null, 1);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        if(db != null)
        {
            db.execSQL("INSERT INTO Patologias (nombre) " +
                    "VALUES ( '" + nombre +"')");

            db.close();
        }

    }

    public static String procesarNombre(String n,int nivel)
    {
        return ContaminacionHelper.diminutivo(n)+"_"+ContaminacionHelper.diminutivoNivel(nivel);
    }

}
