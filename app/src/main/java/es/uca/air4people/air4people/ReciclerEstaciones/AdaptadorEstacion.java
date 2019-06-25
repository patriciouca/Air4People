package es.uca.air4people.air4people.ReciclerEstaciones;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import es.uca.air4people.air4people.R;

public class AdaptadorEstacion extends RecyclerView.Adapter<AdaptadorEstacion.EstacionesViewHolder>{

        private ArrayList<String> datos;

        public AdaptadorEstacion(ArrayList<String> datos) {
            this.datos = datos;
        }

        @Override
        public AdaptadorEstacion.EstacionesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.itemlistaestaciones, viewGroup, false);

            AdaptadorEstacion.EstacionesViewHolder tvh = new AdaptadorEstacion.EstacionesViewHolder(itemView);
            return tvh;
        }


    @Override
        public void onBindViewHolder(AdaptadorEstacion.EstacionesViewHolder viewHolder, int pos) {
            String item = datos.get(pos);

            viewHolder.bindTitular(item);
        }

        @Override
        public int getItemCount() {
            return datos.size();
        }


    public static class EstacionesViewHolder
            extends RecyclerView.ViewHolder {

        private TextView txtTitulo;
        private View v;

        public EstacionesViewHolder(View itemView) {
            super(itemView);

            txtTitulo = (TextView)itemView.findViewById(R.id.titulo);
            v=itemView;


        }

        public void bindTitular(String t) {
            txtTitulo.setText(t);

        }}
}
