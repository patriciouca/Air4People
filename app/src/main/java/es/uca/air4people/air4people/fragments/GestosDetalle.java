package es.uca.air4people.air4people.fragments;

import android.graphics.Color;
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
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
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

    private Date fecha;
    private int dias;
    private String nombre;
    private DetalleEstacion.EncolarEstacionDia encolarEstacionDia;
    private ScrollView scroll;

    public GestosDetalle(String nombre, DetalleEstacion.EncolarEstacionDia encolarEstacionDia, ScrollView scroll) {
        this.nombre = nombre;
        this.encolarEstacionDia=encolarEstacionDia;
        dias=DetalleEstacion.AVANCEDEFECTO;
        this.scroll=scroll;
    }

    private void abajo(){

        for (int i=0;i<DetalleEstacion.AVANCEDEFECTO;i++)
        {
            dias++;
            encolarEstacionDia.anadirPrediccion(dias);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


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
                View view = (View) scroll.getChildAt(scroll.getChildCount() - 1);
                int diff = (view.getBottom() - (scroll.getHeight() + scroll.getScrollY()));

                Log.d("RARO",String.valueOf(diff));

                if (diff <= 21) {
                    abajo();
                }


            }else
            {

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
