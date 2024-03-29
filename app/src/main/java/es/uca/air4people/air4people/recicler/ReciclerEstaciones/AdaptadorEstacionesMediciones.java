package es.uca.air4people.air4people.recicler.ReciclerEstaciones;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import es.uca.air4people.air4people.BD.AndroidBaseDatos;
import es.uca.air4people.air4people.ContaminacionHelper;
import es.uca.air4people.air4people.EstacionesActivity;
import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.Servicio.Estacion;
import es.uca.air4people.air4people.Servicio.Medicion;
import es.uca.air4people.air4people.fragments.ListaMisEstaciones;
import es.uca.air4people.air4people.memoria.MemoriaAplicacion;

public class AdaptadorEstacionesMediciones
        extends RecyclerView.Adapter<AdaptadorEstacionesMediciones.EstacionesViewHolder>
        implements View.OnClickListener,View.OnLongClickListener{

    private ArrayList<EstacionLista> datos;
    private View.OnClickListener listener;
    static View v1;
    static boolean seleccionado;
    static ArrayList<EstacionLista> seleccionados;
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

    public static boolean esSeleccionado(){
        return seleccionado;
    }

    public static void pulsarAtras(){
        EstacionesActivity.setTitulo("Inicio");
        seleccionado=false;
        seleccionados.clear();
        RecyclerView recycler= (RecyclerView) v1.getParent();
        for (int i=0;i<recycler.getChildCount();i++)
        {
            View v=recycler.getChildAt(i);
            v.setBackgroundColor(Color.WHITE);
        }
        Activity host = (Activity) v1.getContext();
        AppCompatActivity t=((AppCompatActivity) host);
        final ImageButton b=t.findViewById(R.id.btnDelete);
        b.setVisibility(View.GONE);
    }

    public void borrarLista(View v)
    {

        AndroidBaseDatos baseDatos=((MemoriaAplicacion) c.getApplication()).getBase();
        int tam=seleccionados.size();
        for (int i = 0; i < tam; i++) {
            EstacionLista elementoDelalista=seleccionados.get(0);
            int indice=-1;
            for (int j=0;j<datos.size();j++)
            {
                if(datos.get(j).getTitulo().equals(elementoDelalista.getTitulo()))
                    indice=j;

            }
            if(indice!=-1)
            {
                datos.remove(indice);
                this.notifyItemRemoved(indice);
            }
            seleccionados.remove(elementoDelalista);
            baseDatos.deleteEstacion(new Estacion(elementoDelalista.getId(),elementoDelalista.getTitulo()));

        }

    }

    @Override
    public boolean onLongClick(View v) {
        v1=v;
        Activity host = (Activity) v.getContext();
        AppCompatActivity t=((AppCompatActivity) host);
        final TextView tb=t.findViewById(R.id.tituloTool);
        final ImageButton b=t.findViewById(R.id.btnDelete);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarLista(v);
                seleccionado=false;
                tb.setText("Inicio");
                b.setVisibility(View.GONE);
                ListaMisEstaciones.permitido=true;
            }
        });
        TextView titulo=(TextView)(v.findViewById(R.id.titulo));
        TextView id=(TextView)(v.findViewById(R.id.idEstacion));
        EstacionLista estacion=new EstacionLista(Integer.parseInt(id.getText().toString()),titulo.getText().toString());
        int indice=-1;
        for(int i=0;i<seleccionados.size();i++)
        {
            if(seleccionados.get(i).getTitulo().equals(titulo.getText()))
                indice=i;
        }

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
            ListaMisEstaciones.permitido=true;
            seleccionado=false;
            EstacionesActivity.setTitulo("Inicio");
            b.setVisibility(View.GONE);
        }
        else{
            ListaMisEstaciones.permitido=false;
            EstacionesActivity.setTitulo("Editar");
            b.setVisibility(View.VISIBLE);
            seleccionado=true;
        }

        return true;
    }

    public static class EstacionesViewHolder
            extends RecyclerView.ViewHolder {

        private TextView txtTitulo;
        private TextView txtid;
        private View v;

        public EstacionesViewHolder(View itemView) {
            super(itemView);

            txtTitulo = (TextView)itemView.findViewById(R.id.titulo);
            txtid = (TextView)itemView.findViewById(R.id.idEstacion);
            v=itemView;


        }

        public void bindTitular(EstacionLista t) {
            txtTitulo.setText(t.getTitulo());
            txtid.setText(String.valueOf(t.getId()));
            LayoutInflater inflater = LayoutInflater.from(v.getContext());
            LinearLayout l=itemView.findViewById(R.id.anadir);
            LinearLayout vertical=null;

            for (Medicion a:t.getMediciones().get(0).getMediciones()){
                vertical=new LinearLayout(v.getContext());
                vertical.setOrientation(LinearLayout.VERTICAL);
                vertical.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                l.addView(vertical);

                View hijo = inflater.inflate(R.layout.prediccionverticalprinci, null);
                TextView tmote=hijo.findViewById(R.id.tvT);
                TextView valor=hijo.findViewById(R.id.tvV);
                ImageView uno=hijo.findViewById(R.id.i1);
                ImageView dos=hijo.findViewById(R.id.i2);
                ImageView tres=hijo.findViewById(R.id.i3);
                ImageView cuatro=hijo.findViewById(R.id.i4);

                ProgressBar barra=hijo.findViewById(R.id.pB);
                ImageButton botoncito=hijo.findViewById(R.id.btInf);

                String diminutivo= ContaminacionHelper.diminutivo(a.getDes_kind());
                if(diminutivo!="")
                    diminutivo="("+diminutivo+")";
                tmote.setText(a.getDes_kind()+" "+diminutivo);
                valor.setText(String.valueOf(a.getValue())+""+a.getUnit());
                int nivel= ContaminacionHelper.comprobar(a.getDes_kind(),a.getValue());
                uno.setVisibility(View.INVISIBLE);
                dos.setVisibility(View.INVISIBLE);
                tres.setVisibility(View.INVISIBLE);
                cuatro.setVisibility(View.INVISIBLE);
                if(nivel!=0)
                {
                    if(nivel>=1)
                    {
                        uno.setVisibility(View.VISIBLE);
                    }

                    if(nivel>=2)
                    {
                        dos.setVisibility(View.VISIBLE);
                    }

                    if(nivel>=3)
                    {
                        tres.setVisibility(View.VISIBLE);
                    }

                    if(nivel>=4)
                    {
                        cuatro.setVisibility(View.VISIBLE);
                    }
                }

                tmote.setTextColor(R.color.colorPrimary);
                //barra.setVisibility(View.GONE);
                //botoncito.setVisibility(View.GONE);
                vertical.addView(hijo);
            }
        }
    }

}