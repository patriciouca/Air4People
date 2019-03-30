package es.uca.air4people.air4people.lista;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import es.uca.air4people.air4people.ComprobarContaminacion;
import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.Servicio.Prediccion;

public class AdapterEstacion extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<EstacionLista> items;

    public AdapterEstacion (Activity activity, ArrayList<EstacionLista> items) {
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

    public void addAll(ArrayList<EstacionLista> estaciones) {
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
            v = inf.inflate(R.layout.itemlista, null);
        }

        EstacionLista dir = items.get(position);

        TextView title = (TextView) v.findViewById(R.id.titulo);
        title.setText(dir.getTitulo());

        LayoutInflater inflater = LayoutInflater.from(v.getContext());
        LinearLayout l=v.findViewById(R.id.anadir);
        LinearLayout horizontal=null;
        int contador=0;
        for (Prediccion a:dir.getPredicciones()){
            if(contador==0)
            {
                horizontal=new LinearLayout(v.getContext());
                horizontal.setOrientation(LinearLayout.HORIZONTAL);
                horizontal.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                l.addView(horizontal);
            }

            View hijo = inflater.inflate(R.layout.prediccionvertical, null);
            TextView tmote=hijo.findViewById(R.id.tvT);
            TextView valor=hijo.findViewById(R.id.tvV);
            ProgressBar barra=hijo.findViewById(R.id.pB);
            ImageButton botoncito=hijo.findViewById(R.id.btInf);

            tmote.setText(ComprobarContaminacion.diminutivo(a.getDes_kind()));
            valor.setText(String.valueOf(a.getValue())+""+a.getUnit());
            int nivel=ComprobarContaminacion.comprobar(a.getDes_kind(),a.getValue());
            if(nivel!=0)
                tmote.setTextColor(ComprobarContaminacion.getColor(nivel));
            barra.setVisibility(View.GONE);
            botoncito.setVisibility(View.GONE);
            horizontal.addView(hijo);
            contador++;
            if(contador==2)
                contador=0;
        }
        



            /*
            ImageView imagen = (ImageView) v.findViewById(R.id.imageView);
            imagen.setImageDrawable(dir.getImage());
            */
        return v;
    }
}