package es.uca.air4people.air4people;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import es.uca.air4people.air4people.BD.AndroidBaseDatos;
import es.uca.air4people.air4people.BD.EstacionBD;
import es.uca.air4people.air4people.ReciclerEstaciones.AdaptadorEstacionesMediciones;
import es.uca.air4people.air4people.ReciclerMedicionFecha.AdaptadorMedicionesFecha;
import es.uca.air4people.air4people.Servicio.Estacion;
import es.uca.air4people.air4people.Servicio.EstacionService;
import es.uca.air4people.air4people.fragments.AddEstacion;
import es.uca.air4people.air4people.fragments.Fragment1;
import es.uca.air4people.air4people.fragments.ListaMisEstaciones;
import es.uca.air4people.air4people.fragments.Mapa;
import es.uca.air4people.air4people.fragments.MapaDetalle;
import es.uca.air4people.air4people.fragments.MasInformacion;
import es.uca.air4people.air4people.fragments.Patologias;
import es.uca.air4people.air4people.fragments.Suscripciones;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EstacionesActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    private ConstraintLayout constraint;
    private ConstraintSet set;

    private LinearLayout linear;

    private NavigationView nav;

    private boolean fuera;
    private static boolean fuera2;

    private static Context contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        nav = (NavigationView)findViewById(R.id.navview);

        constraint = findViewById(R.id.reglasprincipal);

        Toolbar appbar = (Toolbar)findViewById(R.id.appbar);

        setSupportActionBar(appbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        contexto=getApplicationContext();
        irInicio();

        set=new ConstraintSet();
        set.clone(constraint);
        set.setMargin(R.id.principal,ConstraintSet.BOTTOM,0);
        set.applyTo(constraint);

        //MENU LATERAL
        nav.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        boolean fragmentTransaction = false;
                        Fragment fragment = null;
                        String etiqueta="";

                        switch (menuItem.getItemId()) {
                            case R.id.menu_seccion_1:
                                fuera=true;
                                fragment = new ListaMisEstaciones();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_2:
                                fragment = new Mapa();
                                etiqueta="Mapa";
                                fuera=true;
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_opcion_1:
                                fragment = new Suscripciones();
                                fuera=true;
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_opcion_2:
                                fragment = new Patologias();
                                fuera=true;
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_opcion_3:
                                Log.i("NavigationView", "Pulsada opción 2");
                                break;
                            case R.id.menu_opcion_5:
                                fragment = new MasInformacion();
                                fuera=true;
                                fragmentTransaction = true;
                                break;
                        }

                        //ComprobarMapa
                        Fragment mapa = getSupportFragmentManager().findFragmentByTag("Mapa");
                        if (mapa != null && mapa.isVisible()) {
                            Mapa mapavariable= (Mapa)mapa;
                            ((Mapa) mapa).guardarVentana();

                        }

                        if(fragmentTransaction) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, fragment,etiqueta).
                                    addToBackStack(null)
                                    .commit();

                            menuItem.setChecked(true);
                            getSupportActionBar().setTitle(menuItem.getTitle());
                            EstacionesActivity.setTitulo(menuItem.getTitle().toString());

                        }

                        mDrawerLayout.closeDrawers();

                        return true;
                    }
                });

    }

    public static void setContexto(Context c){

        contexto=c;
    }

    public static void setTitulo(String titulo){

        Activity host = (Activity) contexto;
        AppCompatActivity t=((AppCompatActivity) host);
        TextView tb=t.findViewById(R.id.tituloTool);
        tb.setText(titulo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            //...
        }

        return super.onOptionsItemSelected(item);
    }

    public void irInicio(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new ListaMisEstaciones())
                .commit();
        getSupportActionBar().setTitle("Inicio");
        fuera=false;
    }

    public static void setFuera2(){
        fuera2=true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            Fragment mapa = getSupportFragmentManager().findFragmentByTag("Mapa");
            boolean condicionMapa=mapa != null && mapa.isVisible();
            if (condicionMapa) {
                Mapa mapavariable= (Mapa)mapa;
                if(mapavariable.isHay())
                    ((Mapa) mapa).guardarVentana();
                else
                {
                    irInicio();
                    EstacionesActivity.setTitulo("Inicio");
                }
            }
            else if(AdaptadorEstacionesMediciones.esSeleccionado()){
                AdaptadorEstacionesMediciones.pulsarAtras();
            }
            else{
                if(fuera || fuera2){
                    irInicio();
                    EstacionesActivity.setTitulo("Inicio");
                }else{
                    finish();
                }
            }


        }
    }

}
