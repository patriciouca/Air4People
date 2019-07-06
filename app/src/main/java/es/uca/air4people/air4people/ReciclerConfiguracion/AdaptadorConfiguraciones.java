package es.uca.air4people.air4people.ReciclerConfiguracion;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import es.uca.air4people.air4people.BD.AndroidBaseDatos;
import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.memoria.MemoriaAplicacion;

public class AdaptadorConfiguraciones extends RecyclerView.Adapter<AdaptadorConfiguraciones.ConfiguracionViewHolder>
        implements View.OnClickListener{

        private ArrayList<String> datos;
        private View.OnClickListener listener;

        public AdaptadorConfiguraciones(ArrayList<String> datos) {
            this.datos = datos;
        }

        @Override
        public AdaptadorConfiguraciones.ConfiguracionViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.itemlistaconfiguracion, viewGroup, false);

            AdaptadorConfiguraciones.ConfiguracionViewHolder tvh = new AdaptadorConfiguraciones.ConfiguracionViewHolder(itemView);
            itemView.setOnClickListener(this);

            return tvh;
        }


    @Override
        public void onBindViewHolder(final AdaptadorConfiguraciones.ConfiguracionViewHolder viewHolder, final int pos) {
            String item = datos.get(pos);

            viewHolder.bindTitular(item);

        }

        @Override
        public int getItemCount() {
            return datos.size();
        }

        public void setOnClickListener(View.OnClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            if(listener != null)
                listener.onClick(view);
        }

    public static class ConfiguracionViewHolder
            extends RecyclerView.ViewHolder {

        private TextView txtTitulo;
        private View v;

        public ConfiguracionViewHolder(final View itemView) {
            super(itemView);
            txtTitulo = (TextView)itemView.findViewById(R.id.txtConfiguracion);
            v=itemView;
        }

        public void bindTitular(String t) {
            txtTitulo.setText(t);


        }}
}
