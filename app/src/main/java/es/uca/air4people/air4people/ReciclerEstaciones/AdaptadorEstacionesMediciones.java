package es.uca.air4people.air4people.ReciclerEstaciones;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import es.uca.air4people.air4people.ComprobarContaminacion;
import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.Servicio.Medicion;

public class AdaptadorEstacionesMediciones
        extends RecyclerView.Adapter<AdaptadorEstacionesMediciones.EstacionesViewHolder>
        implements View.OnClickListener,View.OnLongClickListener{

    private ArrayList<EstacionLista> datos;
    private View.OnClickListener listener;
    boolean seleccionado;

    public AdaptadorEstacionesMediciones(ArrayList<EstacionLista> datos) {
        seleccionado=false;
        this.datos = datos;
    }

    @Override
    public EstacionesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.itemlistaestaciones, viewGroup, false);

        EstacionesViewHolder tvh = new EstacionesViewHolder(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        return tvh;
    }

    @Override
    public void onBindViewHolder(EstacionesViewHolder viewHolder, int pos) {
        EstacionLista item = datos.get(pos);

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

    @Override
    public boolean onLongClick(View v) {
        Activity host = (Activity) v.getContext();
        if(seleccionado)
        {
            seleccionado=false;
            v.setBackgroundColor(Color.WHITE);
            ((AppCompatActivity) host).getSupportActionBar().setTitle("Inicio");
        }
        else{
            v.setBackgroundColor(Color.GRAY);

            android.support.v7.widget.Toolbar appbar = (Toolbar)v.findViewById(R.id.appbar3);
            ((AppCompatActivity) host).getSupportActionBar().setTitle("Editar");
            ((AppCompatActivity) host).setSupportActionBar(appbar);
            seleccionado=true;
        }

        return true;
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

        public void bindTitular(EstacionLista t) {
            txtTitulo.setText(t.getTitulo());

            LayoutInflater inflater = LayoutInflater.from(v.getContext());
            LinearLayout l=itemView.findViewById(R.id.anadir);
            LinearLayout horizontal=null;
            int contador=0;
            for (Medicion a:t.getMediciones().get(0).getMediciones()){
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
        }
    }

}