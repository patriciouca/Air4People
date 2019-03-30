package es.uca.air4people.air4people.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import es.uca.air4people.air4people.R;
import es.uca.air4people.air4people.Servicio.EstacionService;
import es.uca.air4people.air4people.Servicio.Prediccion;
import es.uca.air4people.air4people.lista.AdapterEstacion;
import es.uca.air4people.air4people.lista.EstacionLista;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GestosDetalle extends  GestureDetector.SimpleOnGestureListener{

    // Minimal x and y axis swipe distance.
    private static int MIN_SWIPE_DISTANCE_X = 200;
    private static int MIN_SWIPE_DISTANCE_Y = 200;

    // Maximal x and y axis swipe distance.
    private static int MAX_SWIPE_DISTANCE_X = 1000;
    private static int MAX_SWIPE_DISTANCE_Y = 1000;

    private Date ultimafecha;
    private String nombre;

    public GestosDetalle(Date ultimafecha, String nombre) {
        this.ultimafecha = ultimafecha;
        this.nombre = nombre;
    }

    private void abajo(){
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://airservices.uca.es/Air4People/").
                addConverterFactory(GsonConverterFactory.create())
                .build();

        EstacionService estacionService = retrofit.create(EstacionService.class);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(ultimafecha);
        Call<List<Prediccion>> call = estacionService.getPrediccionFecha(nombre,formattedDate);
        final String titulo=nombre;
        call.enqueue(new Callback<List<Prediccion>>() {
            @Override
            public void onResponse(Call<List<Prediccion>> call, final Response<List<Prediccion>> response) {


            }

            @Override
            public void onFailure(Call<List<Prediccion>> call, Throwable t) {

            }
        });
    }


    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        // Get swipe delta value in x axis.
        float deltaX = e1.getX() - e2.getX();

        // Get swipe delta value in y axis.
        float deltaY = e1.getY() - e2.getY();

        // Get absolute value.
        float deltaXAbs = Math.abs(deltaX);
        float deltaYAbs = Math.abs(deltaY);

        // Only when swipe distance between minimal and maximal distance value then we treat it as effective swipe
        if((deltaXAbs >= MIN_SWIPE_DISTANCE_X) && (deltaXAbs <= MAX_SWIPE_DISTANCE_X))
        {
            if(deltaX > 0)
            {
                Log.d("RARO","Swipe to left");
            }else
            {
                Log.d("RARO","Swipe to RIGHT");
            }
        }

        if((deltaYAbs >= MIN_SWIPE_DISTANCE_Y) && (deltaYAbs <= MAX_SWIPE_DISTANCE_Y))
        {
            if(deltaY > 0)
            {
                Log.d("RARO","Swipe to UP");
            }else
            {
                abajo();
            }
        }


        return true;
    }

    // Invoked when single tap screen.
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.d("RARO","Swipe to PIM");
        return true;
    }

    // Invoked when double tap screen.
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d("RARO","Swipe to PAM");
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }


}
