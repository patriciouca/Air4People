package es.uca.air4people.air4people;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EstacionesActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView nav;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] mPlanetTitles={"Inicio","Entramos"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final ArrayList<Estacion> estaciones = new ArrayList<Estacion>();
        estaciones.add(new Estacion("Hola",25));
        estaciones.add(new Estacion("Adios",45));
        estaciones.add(new Estacion("Remedio",35));


        ListView lv = (ListView) findViewById(R.id.lista);
        AdapterEstacion adapter = new AdapterEstacion(this, estaciones);
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                Toast.makeText(EstacionesActivity.this,estaciones.get(position).getTitulo(), Toast.LENGTH_LONG).show();

            }
        });
        */

        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        nav = (NavigationView)findViewById(R.id.navview);

        Toolbar appbar = (Toolbar)findViewById(R.id.appbar);
        setSupportActionBar(appbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.common_google_signin_btn_icon_dark_focused);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        nav.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        boolean fragmentTransaction = false;
                        Fragment fragment = null;

                        switch (menuItem.getItemId()) {
                            case R.id.menu_seccion_1:
                                fragment = new Fragment1();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_2:
                                fragment = new Fragment1();
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


    public class Estacion {

        private String titulo;
        private int numero;
        //private Drawable imagen;

        public Estacion(String titulo, int numero) {
            super();
            this.titulo = titulo;
            this.numero = numero;
        }

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        public int getNumero() {
            return numero;
        }

        public void setNumero(int numero) {
            this.numero = numero;
        }
    }

    public class AdapterEstacion extends BaseAdapter {

        protected Activity activity;
        protected ArrayList<Estacion> items;

        public AdapterEstacion (Activity activity, ArrayList<Estacion> items) {
            this.activity = activity;
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        public void clear() {
            items.clear();
        }

        public void addAll(ArrayList<Estacion> estaciones) {
            for (int i = 0; i < estaciones.size(); i++) {
                items.add(estaciones.get(i));
            }
        }

        @Override
        public Object getItem(int arg0) {
            return items.get(arg0);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (convertView == null) {
                LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inf.inflate(R.layout.item, null);
            }

            Estacion dir = items.get(position);

            TextView title = (TextView) v.findViewById(R.id.titulo);
            title.setText(dir.getTitulo());

            TextView description = (TextView) v.findViewById(R.id.numero);
            description.setText(String.valueOf(dir.getNumero()));

            /*
            ImageView imagen = (ImageView) v.findViewById(R.id.imageView);
            imagen.setImageDrawable(dir.getImage());
            */
            return v;
        }
    }
}
