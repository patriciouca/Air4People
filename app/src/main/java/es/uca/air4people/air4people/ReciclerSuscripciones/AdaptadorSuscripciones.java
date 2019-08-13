package es.uca.air4people.air4people.ReciclerSuscripciones;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import es.uca.air4people.air4people.BD.AndroidBaseDatos;
import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.memoria.MemoriaAplicacion;

public class AdaptadorSuscripciones extends RecyclerView.Adapter<AdaptadorSuscripciones.ContaminantesViewHolder>
        implements View.OnClickListener{

        private ArrayList<String> datos;
        private View.OnClickListener listener;
        private AdaptadorSuscripciones esto;

        public AdaptadorSuscripciones(ArrayList<String> datos) {
            esto=this;
            this.datos = datos;
        }

        @Override
        public AdaptadorSuscripciones.ContaminantesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.itemlistacontaminantes, viewGroup, false);

            AdaptadorSuscripciones.ContaminantesViewHolder tvh = new AdaptadorSuscripciones.ContaminantesViewHolder(itemView);
            itemView.setOnClickListener(this);

            return tvh;
        }


    @Override
        public void onBindViewHolder(final AdaptadorSuscripciones.ContaminantesViewHolder viewHolder, final int pos) {
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

    public static class ContaminantesViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTitulo;
        private Switch suscrito1,suscrito2,suscrito3,suscrito4;
        private View v;
        AndroidBaseDatos baseDatos;
        ArrayList<Suscripcion> suscripciones;
        boolean primera;

        public ContaminantesViewHolder(final View itemView) {
            super(itemView);
            Activity host = (Activity) itemView.getContext();
            baseDatos=((MemoriaAplicacion) host.getApplication()).getBase();
            txtTitulo = (TextView)itemView.findViewById(R.id.txtContaminante);
            suscrito1 = (Switch)itemView.findViewById(R.id.suscrito);
            suscrito2 = (Switch)itemView.findViewById(R.id.suscrito2);
            suscrito3 = (Switch)itemView.findViewById(R.id.suscrito3);
            suscrito4 = (Switch)itemView.findViewById(R.id.suscrito4);
            suscripciones=baseDatos.getSuscripciones();
            v=itemView;

            primera=true;

            suscrito1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    String titulo= (String) txtTitulo.getText();
                    Log.d("CAMBIO EN",titulo);

                    if (isChecked) {
                        baseDatos.addSuscripcion(titulo,1);
                    } else {
                        baseDatos.deleteSuscripcion(titulo,1);
                    }
                }
            });
            suscrito2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String titulo= (String) txtTitulo.getText();
                    Log.d("CAMBIO EN",titulo);
                    if (isChecked) {
                        baseDatos.addSuscripcion(titulo,2);
                    } else {
                        baseDatos.deleteSuscripcion(titulo,2);
                    }
                }
            });
            suscrito3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String titulo= (String) txtTitulo.getText();
                    Log.d("CAMBIO EN",titulo);
                    if (isChecked) {
                        baseDatos.addSuscripcion(titulo,3);
                    } else {
                        baseDatos.deleteSuscripcion(titulo,3);
                    }
                }
            });
            suscrito4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String titulo= (String) txtTitulo.getText();
                    Log.d("CAMBIO EN",titulo);
                    if (isChecked) {
                        baseDatos.addSuscripcion(titulo,4);
                    } else {
                        baseDatos.deleteSuscripcion(titulo,4);
                    }
                }
            });


        }

        public void bindTitular(final String t) {
            txtTitulo.setText(t);
            if (primera)
            {
                boolean marcado = false;
                for (int i = 0; i < suscripciones.size(); i++) {
                    if (suscripciones.get(i).nombre.equals(t)) {
                        marcado = true;
                        if (suscripciones.get(i).nivel == 1)
                            suscrito1.setChecked(true);
                        else if (suscripciones.get(i).nivel == 2)
                            suscrito2.setChecked(true);
                        else if (suscripciones.get(i).nivel == 3)
                            suscrito3.setChecked(true);
                        else if (suscripciones.get(i).nivel == 4)
                            suscrito4.setChecked(true);
                    }
                }

                if (!marcado) {
                    suscrito1.setChecked(false);
                    suscrito2.setChecked(false);
                    suscrito3.setChecked(false);
                    suscrito4.setChecked(false);
                }
                primera = false;
        }
        }}
}
