package es.uca.air4people.air4people.BD;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import es.uca.air4people.air4people.R;

public class AndroidBaseDatos extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EstacionBD usdbh =
                new EstacionBD(this, "DBEstaciones", null, 1);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
            //Insertamos los datos en la tabla Usuarios
            db.execSQL("INSERT INTO Estaciones (nombre) " +
                    "VALUES ( '" + "Mediterraneo" +"')");

            //Cerramos la base de datos
            db.close();
        }
    }



}
