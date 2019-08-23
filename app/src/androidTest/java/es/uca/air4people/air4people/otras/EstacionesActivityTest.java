package es.uca.air4people.air4people.otras;

import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.w3c.dom.Text;

import es.uca.air4people.air4people.EstacionesActivity;
import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.recicler.ReciclerEstaciones.AdaptadorEstacion;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static es.uca.air4people.air4people.dannyroa.TestUtils.withRecyclerView;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.StringContains.containsString;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class EstacionesActivityTest {

    @Rule
    public ActivityTestRule<EstacionesActivity> mActivityRule = new ActivityTestRule<>(EstacionesActivity.class);

    @Test
    public void A_revisarPrincipal(){
        /*Comprobar titulo*/
        onView(ViewMatchers.withId(R.id.tituloTool)).check(matches(isDisplayed()));
        onView(withId(R.id.tituloTool)).check(matches(withText(containsString("Inicio"))));
        /*Comprobar no hay boton borrar*/
        onView(withId(R.id.btnDelete)).check(matches(not(isDisplayed())));
    }

    @Test
    public void B_anadir(){
        /*Pulsar mas*/
        onView(withId(R.id.fab)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*Comprobar titulo*/
        onView(withId(R.id.tituloTool)).check(matches(withText(containsString("Añadir estación"))));
        /*Comprobar primer elemento AlcalaDeGuadaira*/
        onView(withRecyclerView(R.id.addestacion)
                .atPositionOnView(0, R.id.titulo))
                .check(matches(withText("AlcalaDeGuadaira")));

        onView(withId(R.id.addestacion)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*Comprobar que se ha añadido*/
        onView(withRecyclerView(R.id.rec)
                .atPositionOnView(0, R.id.titulo))
                .check(matches(withText("AlcalaDeGuadaira")));


    }

    @Test
    public void C_detalle(){

        Activity activity = mActivityRule.getActivity();
        RecyclerView r=activity.findViewById(R.id.rec);
        View vista=r.getLayoutManager().findViewByPosition(0);
        String titulo=((TextView)(vista.findViewById(R.id.titulo))).getText().toString();

        /*Pulsar primer elemento*/
        onView(withId(R.id.rec)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        /*Comprobar titulo*/
        onView(withId(R.id.tituloTool)).check(matches(withText(containsString(titulo))));
        /*Comprobar interfaz*/
        onView(withId(R.id.textView5)).check(matches(withText(containsString("Recomendaciones"))));
        onView(withId(R.id.textView6)).check(matches(withText(containsString("Mediciones"))));

        /*Comprobar pulsar atras lleva inicio*/
        Espresso.pressBack();
        onView(withId(R.id.tituloTool)).check(matches(withText(containsString("Inicio"))));
    }

    /*Eliminar el primer elemento*/
    @Test
    public void D_borrarElemento(){

        /*Nombre primer elemento*/
        Activity activity = mActivityRule.getActivity();
        RecyclerView r=activity.findViewById(R.id.rec);
        View vista=r.getLayoutManager().findViewByPosition(0);
        /*Tiempo de carga de la vista*/
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String titulo=((TextView)(vista.findViewById(R.id.titulo))).getText().toString();

        /*Borrar*/
        onView(withRecyclerView(R.id.rec).atPositionOnView(0,R.id.titulo)).perform(longClick());
        onView(withId(R.id.btnDelete)).perform(click());

        /*Comprobar que no está*/
        try{
            onView(withRecyclerView(R.id.rec).atPositionOnView(0,R.id.titulo)).check(matches(not(withText(containsString(titulo)))));
        }
        catch (NullPointerException e)
        {

        }

    }

    @Test
    public void E_mapas(){
        /*Ir mapas*/
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navview)).perform(NavigationViewActions.navigateTo(R.id.menu_seccion_2));
        /*Titulo*/
        onView(withId(R.id.tituloTool)).check(matches(withText(containsString("Mapas"))));
        /*Pulsar cartuja*/
        UiDevice device = UiDevice.getInstance(getInstrumentation());
        UiObject marker = device.findObject(new UiSelector().descriptionContains("Cartuja"));
        try {
            marker.click();
            Thread.sleep(1000);
            /*La vista que se despliega tiene de titulo cartuja*/
            onView(withId(R.id.titulo)).check(matches(withText(containsString("Cartuja"))));
        }catch (UiObjectNotFoundException e)
        {

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*Volver inicio*/
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navview)).perform(NavigationViewActions.navigateTo(R.id.menu_seccion_1));

    }

    @Test
    public void F_suscripciones(){

        /*Ir suscripciones*/
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navview)).perform(NavigationViewActions.navigateTo(R.id.menu_opcion_1));
        /*Titulo*/
        onView(withId(R.id.tituloTool)).check(matches(withText(containsString("Suscripciones"))));

        /*No desplegados interruptores*/
        onView(withRecyclerView(R.id.recCon)
                .atPositionOnView(0, R.id.suscrito))
                .check(matches(not(isDisplayed())));
        onView(withRecyclerView(R.id.recCon)
                .atPositionOnView(0, R.id.suscrito2))
                .check(matches(not(isDisplayed())));
        onView(withRecyclerView(R.id.recCon)
                .atPositionOnView(0, R.id.suscrito3))
                .check(matches(not(isDisplayed())));
        onView(withRecyclerView(R.id.recCon)
                .atPositionOnView(0, R.id.suscrito4))
                .check(matches(not(isDisplayed())));

        /*Comprobar Ozono*/
        onView(withRecyclerView(R.id.recCon)
                .atPositionOnView(0, R.id.txtContaminante))
                .check(matches(withText("Ozono")));

        /*Pulsar Ozono*/
        onView(withId(R.id.recCon
        )).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        /*Comprobar que se despliegan interruptores*/
        onView(withRecyclerView(R.id.recCon)
                .atPositionOnView(0, R.id.suscrito))
                .check(matches(isDisplayed()));
        onView(withRecyclerView(R.id.recCon)
                .atPositionOnView(0, R.id.suscrito2))
                .check(matches(isDisplayed()));
        onView(withRecyclerView(R.id.recCon)
                .atPositionOnView(0, R.id.suscrito3))
                .check(matches(isDisplayed()));
        onView(withRecyclerView(R.id.recCon)
                .atPositionOnView(0, R.id.suscrito4))
                .check(matches(isDisplayed()));

        /*Ir inicio*/
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navview)).perform(NavigationViewActions.navigateTo(R.id.menu_seccion_1));
    }

    @Test
    public void G_patologias(){

        /*Ir patologias*/
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navview)).perform(NavigationViewActions.navigateTo(R.id.menu_opcion_2));
        onView(withId(R.id.tituloTool)).check(matches(withText(containsString("Patologías"))));
        /*Comprobar primer elemento*/
        onView(withRecyclerView(R.id.recCon)
                .atPositionOnView(0, R.id.txtContaminante))
                .check(matches(withText("Enfermedades pulmonares")));

        /*Ir inicio*/
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navview)).perform(NavigationViewActions.navigateTo(R.id.menu_seccion_1));
    }

    @Test
    public void H_configuracion(){

        /*Ir configuracion*/
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navview)).perform(NavigationViewActions.navigateTo(R.id.menu_opcion_4));
        /*Comprobar titulo*/
        onView(withId(R.id.tituloTool)).check(matches(withText(containsString("Configuración"))));
        /*Comprobar textos*/
        onView(withId(R.id.txt1)).check(matches(withText(R.string.configuraciones1)));
        onView(withId(R.id.txt2)).check(matches(withText(R.string.configuraciones2)));
        onView(withId(R.id.txt3)).check(matches(withText(R.string.configuraciones3)));

        /*Volver inicio*/
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navview)).perform(NavigationViewActions.navigateTo(R.id.menu_seccion_1));
    }

}