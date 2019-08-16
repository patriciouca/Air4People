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
import android.widget.Toast;

import java.util.ArrayList;

import es.uca.air4people.air4people.ContaminacionHelper;
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
            try{

                txtFecha.setText(a.getFecha());
                Log.d("EARO","        1   ");
                for (Medicion t : a.getMediciones()) {

                    View hijo =vista.inflate(vista.getContext(),R.layout.prediccionvertical,null);
                    ConstraintLayout vertical = vista.findViewById(R.id.prediccionvertical);
                    txtMote = (TextView)hijo.findViewById(R.id.tvT);
                    txtvalor = (TextView)hijo.findViewById(R.id.tvV);
                    barra= (ProgressBar)hijo.findViewById(R.id.pB);
                    botoncito=hijo.findViewById(R.id.btInf);
                    txtMote.setText(t.getDes_kind());

                    txtvalor.setText(String.valueOf(t.getValue())+" "+t.getUnit());
                    int comprobacion = ContaminacionHelper.comprobar(t.getDes_kind(), t.getValue());

                    switch (comprobacion) {
                        case 1:
                            barra.setProgress(25);
                            barra.getProgressDrawable().setColorFilter(
                                    ContaminacionHelper.getColor(comprobacion), PorterDuff.Mode.SRC_IN);
                            barra.setVisibility(View.VISIBLE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                botoncito.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view)
                                    {
                                        Toast.makeText(view.getContext(), itemView.getContext().getString(R.string.x1), Toast.LENGTH_LONG).show();

                                    }
                                });
                            }
                            break;
                        case 2:
                            barra.setProgress(50);
                            barra.getProgressDrawable().setColorFilter(
                                    ContaminacionHelper.getColor(comprobacion), PorterDuff.Mode.SRC_IN);
                            barra.setVisibility(View.VISIBLE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    if(t.getDes_kind().toUpperCase().equals("PARTICULAS EN SUSPENSION DE 10 MICRAS"))
                                    {
                                        botoncito.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view)
                                            {
                                                Toast.makeText(view.getContext(), itemView.getContext().getString(R.string.pm102), Toast.LENGTH_LONG).show();

                                            }
                                        });
                                    }
                                    else if(t.getDes_kind().toUpperCase().equals("MONOXIDO DE CARBONO"))
                                    {
                                        botoncito.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view)
                                            {
                                                Toast.makeText(view.getContext(), itemView.getContext().getString(R.string.co2), Toast.LENGTH_LONG).show();

                                            }
                                        });
                                    }
                                    else if(t.getDes_kind().toUpperCase().equals("OZONO"))
                                    {
                                        botoncito.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view)
                                            {
                                                Toast.makeText(view.getContext(), itemView.getContext().getString(R.string.o32), Toast.LENGTH_LONG).show();

                                            }
                                        });
                                    }
                                    else if(t.getDes_kind().toUpperCase().equals("DIOXIDO DE AZUFRE"))
                                    {
                                        botoncito.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view)
                                            {
                                                Toast.makeText(view.getContext(), itemView.getContext().getString(R.string.so22), Toast.LENGTH_LONG).show();

                                            }
                                        });
                                    }
                                    else if(t.getDes_kind().toUpperCase().equals("DIOXIDO DE NITROGENO"))
                                    {
                                        botoncito.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view)
                                            {
                                                Toast.makeText(view.getContext(), itemView.getContext().getString(R.string.no22), Toast.LENGTH_LONG).show();

                                            }
                                        });
                                    }
                                    else
                                        botoncito.setVisibility(View.GONE);
                                }
                            break;
                        case 3:
                            barra.setProgress(75);
                            barra.getProgressDrawable().setColorFilter(
                                    ContaminacionHelper.getColor(comprobacion), PorterDuff.Mode.SRC_IN);
                            barra.setVisibility(View.VISIBLE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                if(t.getDes_kind().toUpperCase().equals("PARTICULAS EN SUSPENSION DE 10 MICRAS")){

                                    botoncito.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view)
                                        {
                                            Toast.makeText(view.getContext(), itemView.getContext().getString(R.string.pm103), Toast.LENGTH_LONG).show();

                                        }
                                    });
                                }
                                else if(t.getDes_kind().toUpperCase().equals("MONOXIDO DE CARBONO"))
                                {

                                    botoncito.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view)
                                        {
                                            Toast.makeText(view.getContext(), itemView.getContext().getString(R.string.co3), Toast.LENGTH_LONG).show();

                                        }
                                    });
                                }
                                else if(t.getDes_kind().toUpperCase().equals("OZONO"))
                                {
                                    botoncito.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view)
                                        {
                                            Toast.makeText(view.getContext(), itemView.getContext().getString(R.string.o33), Toast.LENGTH_LONG).show();

                                        }
                                    });
                                }
                                else if(t.getDes_kind().toUpperCase().equals("DIOXIDO DE AZUFRE"))
                                {
                                    botoncito.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view)
                                        {
                                            Toast.makeText(view.getContext(), itemView.getContext().getString(R.string.so23), Toast.LENGTH_LONG).show();

                                        }
                                    });
                                }
                                else if(t.getDes_kind().toUpperCase().equals("DIOXIDO DE NITROGENO"))
                                {
                                    botoncito.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view)
                                        {
                                            Toast.makeText(view.getContext(), itemView.getContext().getString(R.string.no23), Toast.LENGTH_LONG).show();

                                        }
                                    });
                                }
                                else
                                    botoncito.setVisibility(View.GONE);
                            }
                            break;
                        case 4:
                            barra.getProgressDrawable().setColorFilter(
                                    ContaminacionHelper.getColor(comprobacion), PorterDuff.Mode.SRC_IN);
                            barra.setProgress(100);
                            barra.setVisibility(View.VISIBLE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                if(t.getDes_kind().toUpperCase().equals("PARTICULAS EN SUSPENSION DE 10 MICRAS"))
                                {
                                    botoncito.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view)
                                        {
                                            Toast.makeText(view.getContext(), itemView.getContext().getString(R.string.pm104), Toast.LENGTH_LONG).show();

                                        }
                                    });
                                }
                                else if(t.getDes_kind().toUpperCase().equals("MONOXIDO DE CARBONO"))
                                {
                                    botoncito.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view)
                                        {
                                            Toast.makeText(view.getContext(), itemView.getContext().getString(R.string.co4), Toast.LENGTH_LONG).show();

                                        }
                                    });

                                }
                                else if(t.getDes_kind().toUpperCase().equals("OZONO"))
                                {
                                    botoncito.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view)
                                        {
                                            Toast.makeText(view.getContext(), itemView.getContext().getString(R.string.o34), Toast.LENGTH_LONG).show();

                                        }
                                    });
                                }
                                else if(t.getDes_kind().toUpperCase().equals("DIOXIDO DE AZUFRE"))
                                {
                                    botoncito.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view)
                                        {
                                            Toast.makeText(view.getContext(), itemView.getContext().getString(R.string.so24), Toast.LENGTH_LONG).show();

                                        }
                                    });
                                }
                                else if(t.getDes_kind().toUpperCase().equals("DIOXIDO DE NITROGENO"))
                                {
                                    botoncito.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view)
                                        {
                                            Toast.makeText(view.getContext(), itemView.getContext().getString(R.string.no24), Toast.LENGTH_LONG).show();

                                        }
                                    });
                                }
                                else
                                {
                                    botoncito.setVisibility(View.GONE);
                                }
                            }
                            break;
                        default:
                            botoncito.setVisibility(View.GONE);
                            break;
                    }
                    adjuntar.addView(hijo);
                }
            }catch (Exception e)
            {

            }

        }
    }

}
