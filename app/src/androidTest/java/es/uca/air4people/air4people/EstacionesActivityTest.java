package es.uca.air4people.air4people;

import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.Text;

import es.uca.air4people.air4people.recicler.ReciclerEstaciones.AdaptadorEstacion;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static es.uca.air4people.air4people.dannyroa.TestUtils.withRecyclerView;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(AndroidJUnit4.class)
public class EstacionesActivityTest {

    @Rule
    public ActivityTestRule<EstacionesActivity> mActivityRule = new ActivityTestRule<>(EstacionesActivity.class);

    @Test
    public void revisarEsta(){
        onView(withId(R.id.tituloTool)).check(matches(isDisplayed()));
        onView(withId(R.id.btnDelete)).check(matches(not(isDisplayed())));
    }

    @Test
    public void revisarContenido(){
        onView(withId(R.id.tituloTool)).check(matches(withText(containsString("Inicio"))));
    }

    @Test
    public void anadir(){
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.tituloTool)).check(matches(withText(containsString("Añadir estación"))));

        onView(withRecyclerView(R.id.addestacion)
                .atPositionOnView(0, R.id.titulo))
                .check(matches(withText("AlcalaDeGuadaira")));

        onView(withId(R.id.addestacion)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withRecyclerView(R.id.rec)
                .atPositionOnView(0, R.id.titulo))
                .check(matches(withText("AlcalaDeGuadaira")));


    }

    @Test
    public void detalle(){

        Activity activity = mActivityRule.getActivity();
        RecyclerView r=activity.findViewById(R.id.rec);
        View vista=r.getLayoutManager().findViewByPosition(0);
        String titulo=((TextView)(vista.findViewById(R.id.titulo))).getText().toString();

        onView(withId(R.id.rec)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.tituloTool)).check(matches(withText(containsString(titulo))));

        onView(withId(R.id.textView5)).check(matches(withText(containsString("Recomendaciones"))));
        onView(withId(R.id.textView6)).check(matches(withText(containsString("Mediciones"))));


        Espresso.pressBack();
        onView(withId(R.id.tituloTool)).check(matches(withText(containsString("Inicio"))));
    }

    @Test
    public void suscripciones(){

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navview)).perform(NavigationViewActions.navigateTo(R.id.menu_opcion_1));


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


        onView(withRecyclerView(R.id.recCon)
                .atPositionOnView(0, R.id.txtContaminante))
                .check(matches(withText("Ozono")));


        onView(withId(R.id.recCon
        )).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));


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

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navview)).perform(NavigationViewActions.navigateTo(R.id.menu_seccion_1));
    }

    @Test
    public void patologias(){

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navview)).perform(NavigationViewActions.navigateTo(R.id.menu_opcion_2));


        onView(withRecyclerView(R.id.recCon)
                .atPositionOnView(0, R.id.txtContaminante))
                .check(matches(withText("Enfermedades pulmonares")));


        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navview)).perform(NavigationViewActions.navigateTo(R.id.menu_seccion_1));
    }

    @Test
    public void configuracion(){

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navview)).perform(NavigationViewActions.navigateTo(R.id.menu_opcion_4));

        onView(withId(R.id.txt1)).check(matches(withText(R.string.configuraciones1)));
        onView(withId(R.id.txt2)).check(matches(withText(R.string.configuraciones2)));
        onView(withId(R.id.txt3)).check(matches(withText(R.string.configuraciones3)));


        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.navview)).perform(NavigationViewActions.navigateTo(R.id.menu_seccion_1));
    }
}