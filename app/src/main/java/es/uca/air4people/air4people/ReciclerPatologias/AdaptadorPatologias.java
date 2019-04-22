package es.uca.air4people.air4people.ReciclerPatologias;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import es.uca.air4people.air4people.R;

public class AdaptadorPatologias extends RecyclerView.Adapter<AdaptadorPatologias.ContaminantesViewHolder>
        implements View.OnClickListener{

        private ArrayList<String> datos;
        private View.OnClickListener listener;

        public AdaptadorPatologias(ArrayList<String> datos) {
            this.datos = datos;
        }

        @Override
        public AdaptadorPatologias.ContaminantesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.itemlistacontaminantes, viewGroup, false);

            AdaptadorPatologias.ContaminantesViewHolder tvh = new AdaptadorPatologias.ContaminantesViewHolder(itemView);
            itemView.setOnClickListener(this);

            return tvh;
        }


    @Override
        public void onBindViewHolder(final AdaptadorPatologias.ContaminantesViewHolder viewHolder, final int pos) {
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

    public static class ContaminantesViewHolder
            extends RecyclerView.ViewHolder {

        private TextView txtTitulo;
        private Switch suscrito;
        private View v;

        public ContaminantesViewHolder(final View itemView) {
            super(itemView);

            txtTitulo = (TextView)itemView.findViewById(R.id.txtContaminante);
            suscrito = (Switch)itemView.findViewById(R.id.isSuscrito);
            suscrito.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Toast.makeText(itemView.getContext(),
                            "Toast por defecto", Toast.LENGTH_SHORT);
                }
            });
            v=itemView;


        }

        public Switch getSuscrito() {
            return suscrito;
        }

        public void setSuscrito(Switch suscrito) {
            this.suscrito = suscrito;
        }

        public void bindTitular(String t) {
            txtTitulo.setText(t);

        }}
}
