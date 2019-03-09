package es.uca.air4people.air4people.lista;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import es.uca.air4people.air4people.R;

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
            v = inf.inflate(R.layout.item, null);
        }

        EstacionLista dir = items.get(position);

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