package es.uca.air4people.air4people.fragments;

import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import es.uca.air4people.air4people.R;

public class GestosMapas extends  GestureDetector.SimpleOnGestureListener{

    // Minimal x and y axis swipe distance.
    private static int MIN_SWIPE_DISTANCE_X = 200;
    private static int MIN_SWIPE_DISTANCE_Y = 200;

    // Maximal x and y axis swipe distance.
    private static int MAX_SWIPE_DISTANCE_X = 1000;
    private static int MAX_SWIPE_DISTANCE_Y = 1000;

    private int marca;
    private ConstraintLayout reglas;

    public GestosMapas(ConstraintLayout reglas) {
        this.reglas = reglas;
        marca=1200;
    }

    public void abajo(){
        marca=0;
        cambiar();
    }

    private void arriba(){
        if(marca<1500)
        {
            marca=1500;
            cambiar();
        }

    }

    private void arriba2(){
        if(marca<2000)
        {
            marca=2000;
            cambiar();
        }

    }

    private void cambiar(){
        ConstraintSet set=new ConstraintSet();
        set.clone(reglas);
        set.setMargin(R.id.principal,ConstraintSet.BOTTOM,marca);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(reglas);
        }
        set.applyTo(reglas);

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
                if(marca<1500)
                    arriba();
                else if(marca==1500)
                    arriba2();
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
