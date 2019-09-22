package es.uca.air4people.air4people.unitarias;

import android.database.sqlite.SQLiteDatabase;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.uca.air4people.air4people.BD.AndroidBaseDatos;
import es.uca.air4people.air4people.BD.EstacionBD;
import es.uca.air4people.air4people.EstacionesActivity;
import es.uca.air4people.air4people.memoria.MemoriaAplicacion;

import static org.junit.Assert.assertTrue;

public class BDTestUni {

    @Rule
    public ActivityTestRule<EstacionesActivity> mActivityRule = new ActivityTestRule<>(EstacionesActivity.class);
    AndroidBaseDatos bd;

    @Before
    public void inicio() {
        bd=((MemoriaAplicacion) mActivityRule.getActivity().getApplicationContext()).getBase();
    }

    @Test
    public void conexion(){
        EstacionBD usdbh =
                new EstacionBD(mActivityRule.getActivity().getApplicationContext(), "DBEstaciones", null, 1);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        assertTrue(db!=null);
        db.close();
    }

    @Test
    public void getEstaciones(){
        assertTrue(bd.getEstaciones()!=null);
    }

    @Test
    public void getSuscripciones(){
        assertTrue(bd.getSuscripciones()!=null);
    }

    @Test
    public void getPatologias(){
        assertTrue(bd.getPatologias()!=null);
    }

}
