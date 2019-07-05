package es.uca.air4people.air4people;

import android.app.Activity;
import android.graphics.Color;
import android.util.AndroidException;
import android.util.Log;

import java.util.ArrayList;

import es.uca.air4people.air4people.BD.AndroidBaseDatos;
import es.uca.air4people.air4people.memoria.MemoriaAplicacion;

public class ContaminacionHelper {

    static String[] contaminantes={"DIOXIDO DE AZUFRE","OZONO","PARTICULAS EN SUSPENSION DE 10 MICRAS","DIOXIDO DE NITROGENO","MONOXIDO DE CARBONO"};
    static float[] valor1={0,63,125,188};
    static float[] valor2={0,60,120,180};
    static float[] valor3={0,25,50,75};
    static float[] valor4={0,100,200,300};
    static float[] valor5={0,5000,10000,15000};
    static  float[][] comprobaciones={valor1,valor2,valor3,valor4,valor5};

    public static int comprobar(String contaminante,float valor)
    {
        for (int contador=0;contador<contaminantes.length;contador++){
            if(contaminante.toUpperCase().contains(contaminantes[contador])){
                if(valor<=comprobaciones[contador][1])
                    return 1;
                else if(valor<=comprobaciones[contador][2])
                    return 2;
                else if(valor<=comprobaciones[contador][3])
                    return 3;
                else
                    return 4;
            }
        }
        return 0;
    }

    public static String diminutivoS(String suscripcion)
    {
        switch(suscripcion)
        {
            case "Enfermedades pulmonares":
                return "ep";
            case "Enfermedades del corazón":
                return "ec";
            case "Enfermedades genéticas":
                return "eg";
            case "Falta de Omega3 y vitaminas":
                return "fov";
            case "Asma":
                return "a";
            case "Menor de 19 años":
                return "m1";
            case "Mayor de 60 años":
                return "m6";
            default:
                return null;
        }
    }

    public static String diminutivoNivel(int nivel)
    {
        switch(nivel)
        {
            case 1:
                return "B";
            case 2:
                return "M";
            case 3:
                return "A";
            case 4:
                return "MA";
            default:
                return null;
        }
    }

    public static String diminutivo(String contaminante)
    {
        switch(contaminante.toUpperCase())
        {
            case "MONOXIDO DE CARBONO":
                return "CO";
            case "OXIDO DE NITROGENO":
                return "NO";
            case "DIOXIDO DE NITROGENO":
                return "NO2";
            case "OXIDOS DE NITROGENO GENERICO":
                return "NOX";
            case "OZONO":
                return "O3";
            case "DIOXIDO DE AZUFRE":
                return "SO2";
            case "BENCENO":
                return "C6H6";
            case "TOLUENO":
                return "C6H5CH3";
            default:
                return null;
        }
    }

    public static int getColor(int nivel)
    {
        switch (nivel)
        {
            case 1:
                return Color.GREEN;
            case 2:
                return Color.YELLOW;
            case 3:
                return Color.rgb(255,165,0);
            case 4:
                return Color.RED;
        }
        return 0;
    }
}
