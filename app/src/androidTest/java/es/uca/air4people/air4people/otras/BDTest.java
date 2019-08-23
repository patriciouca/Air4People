package es.uca.air4people.air4people.otras;

import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.messaging.FirebaseMessaging;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import es.uca.air4people.air4people.BD.AndroidBaseDatos;
import es.uca.air4people.air4people.BD.EstacionBD;
import es.uca.air4people.air4people.ContaminacionHelper;
import es.uca.air4people.air4people.EstacionesActivity;
import es.uca.air4people.air4people.Servicio.Estacion;
import es.uca.air4people.air4people.memoria.MemoriaAplicacion;
import es.uca.air4people.air4people.recicler.ReciclerSuscripciones.Suscripcion;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class BDTest {

    @Rule
    public ActivityTestRule<EstacionesActivity> mActivityRule = new ActivityTestRule<>(EstacionesActivity.class);
    AndroidBaseDatos bd;

    @Before
    public void inicio() {
        bd=((MemoriaAplicacion) mActivityRule.getActivity().getApplicationContext()).getBase();
    }

    @Test
    public void estaciones(){
        if(bd.getEstaciones().indexOf("nombre")!=-1)
            bd.deleteEstacion(new Estacion(333,"nombre"));

        bd.addEstacion(new Estacion(333,"nombre"));
        assertTrue(bd.getEstaciones().indexOf("nombre")!=-1);
        bd.deleteEstacion(new Estacion(333,"nombre"));
        assertTrue(bd.getEstaciones().indexOf("nombre")==-1);
    }

    @Test
    public void patologias(){
        if(bd.getPatologias().indexOf("nombre")!=-1)
            bd.deletePatologia("nombre");

        bd.addPatologia("nombre");
        assertTrue(bd.getPatologias().indexOf("nombre")!=-1);
        bd.deletePatologia("nombre");
        assertTrue(bd.getPatologias().indexOf("nombre")==-1);
    }

    @Test
    public void suscripciones(){

        if(bd.enSuscripcion("nombre",1))
            bd.deleteSuscripcion("nombre",1);

        bd.addSuscripcion("nombre",1);
        assertTrue(bd.enSuscripcion("nombre",1));
        bd.deleteSuscripcion("nombre",1);
        assertTrue(!bd.enSuscripcion("nombre",1));
    }

}
