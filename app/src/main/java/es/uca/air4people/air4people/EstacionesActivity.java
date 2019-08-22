package es.uca.air4people.air4people;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import es.uca.air4people.air4people.recicler.ReciclerEstaciones.AdaptadorEstacionesMediciones;
import es.uca.air4people.air4people.fragments.Configuracion;
import es.uca.air4people.air4people.fragments.ListaMisEstaciones;
import es.uca.air4people.air4people.fragments.Mapa;
import es.uca.air4people.air4people.fragments.MasInformacion;
import es.uca.air4people.air4people.fragments.Patologias;
import es.uca.air4people.air4people.fragments.Suscripciones;

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
                                menuItem.setChecked(true);
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_2:
                                fragment = new Mapa();
                                etiqueta="Mapa";
                                fuera=true;
                                menuItem.setChecked(true);
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
                                menuItem.setChecked(false);
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_opcion_4:
                                fragment = new Configuracion();
                                etiqueta="Configuracion";
                                menuItem.setChecked(false);
                                fuera=true;
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_opcion_5:
                                fragment = new MasInformacion();
                                fuera=true;
                                menuItem.setChecked(false);
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

                            final ImageButton b=findViewById(R.id.btnDelete);
                            if(!ListaMisEstaciones.permitido)
                            {
                                ListaMisEstaciones.permitido=true;
                                b.setVisibility(View.INVISIBLE);
                            }

                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, fragment,etiqueta).
                                    addToBackStack(null)
                                    .commit();

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
        nav.getMenu().getItem(0).setChecked(true);
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
                ListaMisEstaciones.permitido=true;
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
