package es.uca.air4people.air4people.ReciclerEstaciones;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.Servicio.Estacion;

public class AdaptadorEstacion extends RecyclerView.Adapter<AdaptadorEstacion.EstacionesViewHolder>{

        private ArrayList<Estacion> datos;

        public AdaptadorEstacion(ArrayList<Estacion> datos) {
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
            Estacion item = datos.get(pos);
            viewHolder.bindTitular(item);
        }

        @Override
        public int getItemCount() {
            return datos.size();
        }


    public static class EstacionesViewHolder
            extends RecyclerView.ViewHolder {

        private TextView txtTitulo;
        private TextView idEstacion;
        private View v;

        public EstacionesViewHolder(View itemView) {
            super(itemView);

            txtTitulo = (TextView)itemView.findViewById(R.id.titulo);
            idEstacion = (TextView)itemView.findViewById(R.id.idEstacion);
            v=itemView;


        }

        public void bindTitular(Estacion t) {
            txtTitulo.setText(t.getMote_name());
            idEstacion.setText(String.valueOf(t.getMote_id()));
        }}
}
