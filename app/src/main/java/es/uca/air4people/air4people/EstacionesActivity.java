package es.uca.air4people.air4people;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import es.uca.air4people.air4people.Servicio.Estacion;
import es.uca.air4people.air4people.Servicio.EstacionService;
import es.uca.air4people.air4people.fragments.Fragment1;
import es.uca.air4people.air4people.fragments.ListaMisEstaciones;
import es.uca.air4people.air4people.fragments.Mapa;
import es.uca.air4people.air4people.fragments.MapaDetalle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        irInicio();

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        nav = (NavigationView)findViewById(R.id.navview);

        constraint = findViewById(R.id.reglasprincipal);

        Toolbar appbar = (Toolbar)findViewById(R.id.appbar);
        setSupportActionBar(appbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_dehaze);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
        set=new ConstraintSet();
        set.clone(constraint);
        set.clear(R.id.principal,ConstraintSet.BOTTOM);
        set.applyTo(constraint);
        //MENU LATERAL
        nav.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        boolean fragmentTransaction = false;
                        Fragment fragment = null;

                        switch (menuItem.getItemId()) {
                            case R.id.menu_seccion_1:
                                fragment = new ListaMisEstaciones();
                                fuera=false;
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_2:
                                fragment = new Mapa();
                                fuera=true;
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_3:
                                fragment = new Fragment1();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_opcion_1:
                                Log.i("NavigationView", "Pulsada opción 1");
                                break;
                            case R.id.menu_opcion_2:
                                Log.i("NavigationView", "Pulsada opción 2");
                                break;
                        }

                        if(fragmentTransaction) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .commit();

                            menuItem.setChecked(true);
                            getSupportActionBar().setTitle(menuItem.getTitle());
                        }

                        mDrawerLayout.closeDrawers();

                        return true;
                    }
                });
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
        fuera=false;
    }

    @Override
    public void onBackPressed() {
        if(fuera){
            irInicio();
        }else{
            finish();
        }
    }

}
