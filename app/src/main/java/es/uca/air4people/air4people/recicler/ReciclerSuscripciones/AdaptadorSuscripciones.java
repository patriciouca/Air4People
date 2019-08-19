package es.uca.air4people.air4people.recicler.ReciclerSuscripciones;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import es.uca.air4people.air4people.BD.AndroidBaseDatos;
import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.memoria.MemoriaAplicacion;

import static java.lang.Thread.sleep;

public class AdaptadorSuscripciones extends RecyclerView.Adapter<AdaptadorSuscripciones.ContaminantesViewHolder>
        implements View.OnClickListener{

        private ArrayList<String> datos;
        private View.OnClickListener listener;
        AndroidBaseDatos baseDatos;
        ArrayList<Suscripcion> suscripciones;
        View ultimaView;
        AdaptadorSuscripciones.ContaminantesViewHolder tvh;

        public AdaptadorSuscripciones(ArrayList<String> datos) {
            this.datos = datos;
        }

        @Override
        public AdaptadorSuscripciones.ContaminantesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.itemlistacontaminantes, viewGroup, false);
            ultimaView=null;
            Activity host = (Activity) itemView.getContext();
            baseDatos=((MemoriaAplicacion) host.getApplication()).getBase();
            suscripciones=baseDatos.getSuscripciones();
            tvh = new AdaptadorSuscripciones.ContaminantesViewHolder(itemView);
            itemView.setOnClickListener(this);

            return tvh;
        }

    @Override
        public void onBindViewHolder(final AdaptadorSuscripciones.ContaminantesViewHolder viewHolder, final int pos) {
            String item = datos.get(pos);
            viewHolder.bindTitular(item);

        for (int i = 0; i < suscripciones.size(); i++) {
            if (suscripciones.get(i).nombre.equals(item)) {

                if (suscripciones.get(i).nivel == 1)
                    viewHolder.suscrito1.setChecked(true);
                else if (suscripciones.get(i).nivel == 2)
                    viewHolder.suscrito2.setChecked(true);
                else if (suscripciones.get(i).nivel == 3)
                    viewHolder.suscrito3.setChecked(true);
                else if (suscripciones.get(i).nivel == 4)
                    viewHolder.suscrito4.setChecked(true);
            }
        }

            viewHolder.suscrito1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    String titulo= (String) viewHolder.txtTitulo.getText();


                    if (isChecked) {
                        baseDatos.addSuscripcion(titulo,1);
                    } else {
                        baseDatos.deleteSuscripcion(titulo,1);
                    }
                }
            });

        viewHolder.suscrito2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String titulo= (String) viewHolder.txtTitulo.getText();


                if (isChecked) {
                    baseDatos.addSuscripcion(titulo,2);
                } else {
                    baseDatos.deleteSuscripcion(titulo,2);
                }
            }
        });

        viewHolder.suscrito3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String titulo= (String) viewHolder.txtTitulo.getText();

                if (isChecked) {
                    baseDatos.addSuscripcion(titulo,3);
                } else {
                    baseDatos.deleteSuscripcion(titulo,3);
                }

            }
        });
        viewHolder.suscrito4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String titulo= (String) viewHolder.txtTitulo.getText();


                if (isChecked) {
                    baseDatos.addSuscripcion(titulo,4);
                } else {
                    baseDatos.deleteSuscripcion(titulo,4);
                }

            }
        });

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

            ConstraintLayout constraint=view.findViewById(R.id.ocultar);

            ImageView flecha= view.findViewById(R.id.flecha);

            Transition transition = new Fade();
            transition.setDuration(400);
            transition.addTarget(R.id.ocultar);
            TransitionManager.beginDelayedTransition((ViewGroup) constraint.getParent(), transition);

            constraint.setVisibility(View.VISIBLE);
            flecha.setRotation(0);

            if(ultimaView!=null && view!=ultimaView)
            {
                constraint=ultimaView.findViewById(R.id.ocultar);
                flecha= ultimaView.findViewById(R.id.flecha);
                transition.addTarget(constraint.getId());
                TransitionManager.beginDelayedTransition((ViewGroup) constraint.getParent(), transition);

                constraint.setVisibility(View.GONE);
                flecha.setRotation(270);
            }
            ultimaView=view;

            if(listener != null)
            {
                listener.onClick(view);
            }

        }

    public static class ContaminantesViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTitulo;
        private Switch suscrito1,suscrito2,suscrito3,suscrito4;
        private View v;
        AndroidBaseDatos baseDatos;
        ArrayList<Suscripcion> suscripciones;

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

        }

        public void bindTitular(final String t) {
            txtTitulo.setText(t);
        }}

}
