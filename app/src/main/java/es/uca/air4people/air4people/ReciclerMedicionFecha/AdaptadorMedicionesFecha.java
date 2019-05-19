package es.uca.air4people.air4people.ReciclerMedicionFecha;

import android.graphics.PorterDuff;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.uca.air4people.air4people.ComprobarContaminacion;
import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.Servicio.Medicion;
import es.uca.air4people.air4people.Servicio.Mediciones;

public class AdaptadorMedicionesFecha extends RecyclerView.Adapter<AdaptadorMedicionesFecha.MedicionesFechaViewHolder>  {

    private ArrayList<Mediciones> datos;

    public AdaptadorMedicionesFecha() {
        this.datos = new ArrayList<Mediciones>();
    }
    public AdaptadorMedicionesFecha(ArrayList<Mediciones> datos) {
        this.datos = datos;
    }

    @Override
    public MedicionesFechaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.medicionlistafecha, viewGroup, false);

        MedicionesFechaViewHolder tvh = new MedicionesFechaViewHolder(itemView);

        return tvh;
    }

    @Override
    public void onBindViewHolder(MedicionesFechaViewHolder viewHolder, int pos) {
        Mediciones item = datos.get(pos);


        viewHolder.bindTitular(item);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public static class MedicionesFechaViewHolder
            extends RecyclerView.ViewHolder {

        private TextView txtFecha;
        private TextView txtMote;
        private TextView txtvalor;
        private ProgressBar barra;
        private ImageButton botoncito;
        private View vista;

        public MedicionesFechaViewHolder(View itemView) {
            super(itemView);
            txtFecha=itemView.findViewById(R.id.fecha);
            vista=itemView;
        }

        public void bindTitular(Mediciones a) {
            LinearLayout adjuntar = vista.findViewById(R.id.linear);
            txtFecha.setText(a.getFecha());

            for (Medicion t : a.getMediciones()) {
                View hijo =vista.inflate(vista.getContext(),R.layout.prediccionvertical,null);
                ConstraintLayout vertical = vista.findViewById(R.id.prediccionvertical);
                txtFecha = (TextView)hijo.findViewById(R.id.fecha);
                txtMote = (TextView)hijo.findViewById(R.id.tvT);
                txtvalor = (TextView)hijo.findViewById(R.id.tvV);
                barra= (ProgressBar)hijo.findViewById(R.id.pB);
                botoncito=hijo.findViewById(R.id.btInf);
                txtMote.setText(t.getDes_kind());
                txtvalor.setText(String.valueOf(t.getValue()));
                int comprobacion = ComprobarContaminacion.comprobar(t.getDes_kind(), t.getValue());

                switch (comprobacion) {
                    case 1:
                        barra.setProgress(25);
                        barra.getProgressDrawable().setColorFilter(
                                ComprobarContaminacion.getColor(comprobacion), PorterDuff.Mode.SRC_IN);
                        barra.setVisibility(View.VISIBLE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            botoncito.setTooltipText(vista.getContext().getResources().getString(R.string.x1));
                        }
                        break;
                    case 2:
                        barra.setProgress(50);
                        barra.getProgressDrawable().setColorFilter(
                                ComprobarContaminacion.getColor(comprobacion), PorterDuff.Mode.SRC_IN);
                        barra.setVisibility(View.VISIBLE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            if (t.getDes_kind().toUpperCase().equals("PARTICULAS EN SUSPENSION DE 10 MICRAS"))
                                botoncito.setTooltipText(vista.getContext().getResources().getString(R.string.pm102));
                            else if (t.getDes_kind().toUpperCase().equals("MONOXIDO DE CARBONO"))
                                botoncito.setTooltipText(vista.getContext().getResources().getString(R.string.co2));
                            else
                                botoncito.setVisibility(View.GONE);
                        }
                        break;
                    case 3:
                        barra.setProgress(75);
                        barra.getProgressDrawable().setColorFilter(
                                ComprobarContaminacion.getColor(comprobacion), PorterDuff.Mode.SRC_IN);
                        barra.setVisibility(View.VISIBLE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            if (t.getDes_kind().toUpperCase().equals("PARTICULAS EN SUSPENSION DE 10 MICRAS"))
                                botoncito.setTooltipText(vista.getContext().getResources().getString(R.string.pm103));
                            else if (t.getDes_kind().toUpperCase().equals("MONOXIDO DE CARBONO"))
                                botoncito.setTooltipText(vista.getContext().getResources().getString(R.string.co3));
                            else
                                botoncito.setVisibility(View.GONE);
                        }
                        break;
                    case 4:
                        barra.getProgressDrawable().setColorFilter(
                                ComprobarContaminacion.getColor(comprobacion), PorterDuff.Mode.SRC_IN);
                        barra.setProgress(100);
                        barra.setVisibility(View.VISIBLE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            if (t.getDes_kind().toUpperCase().equals("PARTICULAS EN SUSPENSION DE 10 MICRAS"))
                                botoncito.setTooltipText(vista.getContext().getResources().getString(R.string.pm104));
                            else if (t.getDes_kind().toUpperCase().equals("MONOXIDO DE CARBONO"))
                                botoncito.setTooltipText(vista.getContext().getResources().getString(R.string.co4));
                            else
                                botoncito.setVisibility(View.GONE);
                        }
                        break;
                    default:
                        botoncito.setVisibility(View.GONE);
                        break;
                }
            adjuntar.addView(hijo);
            }
        }
    }

}
