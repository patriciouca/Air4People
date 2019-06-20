package es.uca.air4people.air4people.ReciclerEstaciones;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import es.uca.air4people.air4people.BD.AndroidBaseDatos;
import es.uca.air4people.air4people.ComprobarContaminacion;
import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.Servicio.Medicion;
import es.uca.air4people.air4people.memoria.MemoriaAplicacion;

public class AdaptadorEstacionesMediciones
        extends RecyclerView.Adapter<AdaptadorEstacionesMediciones.EstacionesViewHolder>
        implements View.OnClickListener,View.OnLongClickListener{

    private ArrayList<EstacionLista> datos;
    private View.OnClickListener listener;
    boolean seleccionado;
    ArrayList<String> seleccionados;
    Activity c;

    public AdaptadorEstacionesMediciones(ArrayList<EstacionLista> datos,Activity act) {
        seleccionado=false;
        this.datos = datos;
        seleccionados=new ArrayList<>();
        this.c=act;
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

    public void borrarLista(View v)
    {

        AndroidBaseDatos baseDatos=((MemoriaAplicacion) c.getApplication()).getBase();
        for (int i = 0; i < seleccionados.size(); i++) {
            String elementoDelalista=seleccionados.get(0);

            int indice=-1;
            for (int j=0;j<datos.size();j++)
            {
                if(datos.get(j).getTitulo().equals(elementoDelalista))
                    indice=j;

            }
            if(indice!=-1)
            {
                datos.remove(indice);
                this.notifyItemRemoved(indice);
            }
            seleccionados.remove(elementoDelalista);
            baseDatos.deleteEstacion(elementoDelalista);
        }

    }

    @Override
    public boolean onLongClick(View v) {
        Activity host = (Activity) v.getContext();
        AppCompatActivity t=((AppCompatActivity) host);
        TextView tb=t.findViewById(R.id.tituloTool);
        Button b=t.findViewById(R.id.btnDelete);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarLista(v);
            }
        });
        TextView titulo=(TextView)(v.findViewById(R.id.titulo));
        String estacion=titulo.getText().toString();
        int indice=seleccionados.indexOf(estacion);
        Log.d("Raro",String.valueOf(indice));
        if(indice!=-1)
        {
            seleccionados.remove(indice);
            v.setBackgroundColor(Color.WHITE);

        }
        else{
            seleccionados.add(estacion);

            v.setBackgroundColor(Color.GRAY);
        }

        if(seleccionado && seleccionados.size()==0)
        {
            seleccionado=false;
            tb.setText("Inicio");
            b.setVisibility(View.GONE);
        }
        else{

            tb.setText("Editar");
            b.setVisibility(View.VISIBLE);

            //((AppCompatActivity) host).setSupportActionBar(appbar);
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
            LinearLayout vertical=null;

            for (Medicion a:t.getMediciones().get(0).getMediciones()){
                vertical=new LinearLayout(v.getContext());
                vertical.setOrientation(LinearLayout.VERTICAL);
                vertical.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                l.addView(vertical);

                View hijo = inflater.inflate(R.layout.prediccionvertical, null);
                TextView tmote=hijo.findViewById(R.id.tvT);
                TextView valor=hijo.findViewById(R.id.tvV);


                valor.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                tmote.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);


                ViewGroup.LayoutParams layoutParams = valor.getLayoutParams();
                layoutParams.width = 300;
                valor.setLayoutParams(layoutParams);

                layoutParams = tmote.getLayoutParams();
                layoutParams.width = 600;
                tmote.setLayoutParams(layoutParams);

                ProgressBar barra=hijo.findViewById(R.id.pB);
                ImageButton botoncito=hijo.findViewById(R.id.btInf);

                String diminutivo=ComprobarContaminacion.diminutivo(a.getDes_kind());
                if(diminutivo!="")
                    diminutivo="("+diminutivo+")";
                tmote.setText(a.getDes_kind()+" "+diminutivo);
                valor.setText(String.valueOf(a.getValue())+""+a.getUnit());
                int nivel=ComprobarContaminacion.comprobar(a.getDes_kind(),a.getValue());
                if(nivel!=0)
                    tmote.setTextColor(ComprobarContaminacion.getColor(nivel));
                barra.setVisibility(View.GONE);
                botoncito.setVisibility(View.GONE);
                vertical.addView(hijo);
            }
        }
    }

}