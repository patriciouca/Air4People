package es.uca.air4people.air4people;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AndroidException;
import android.util.Log;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;

import es.uca.air4people.air4people.BD.AndroidBaseDatos;
import es.uca.air4people.air4people.Servicio.Estacion;
import es.uca.air4people.air4people.Servicio.Medicion;
import es.uca.air4people.air4people.Servicio.Mediciones;
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

    public static ArrayList<Estacion> quitarEstacion(ArrayList<Estacion> estaciones,String e){
        for (int i=0;i<estaciones.size();i++)
        {
            if(estaciones.get(i).getMote_name().equals(e))
                estaciones.remove(estaciones.get(i));
        }
        return estaciones;
    }

    public static boolean estaEstacion(ArrayList<Estacion> estaciones,String e){
        for (int i=0;i<estaciones.size();i++)
        {
            if(estaciones.get(i).getMote_name().equals(e))
                return true;
        }
        return false;
    }

    public static float getValorContaminante(String contaminante,List<Medicion> medicion){
        for(int i=0;i<medicion.size();i++)
        {

            Medicion m=medicion.get(i);
            if(contaminante.equals(m.getDes_kind()))
                return m.getValue();
        }
        return -1;
    }

    public static int[] getProblemas(List<Medicion> medicion){
        int[] devolver=new int[4];
        float o3=getValorContaminante("Ozono",medicion);
        float pm10=getValorContaminante("Particulas en suspension de 10 micras",medicion);
        float co=getValorContaminante("Monoxido de Carbono",medicion);
        float no2=getValorContaminante("Dioxido de Nitrogeno",medicion);
        float so2=getValorContaminante("Dioxido de Azufre",medicion);

        Log.d("senores O3","O3 "+String.valueOf(o3));
        Log.d("senores PM","PM "+String.valueOf(pm10));
        Log.d("senores SO","SO "+String.valueOf(so2));
        Log.d("senores NO2","NO "+String.valueOf(no2));


        //SenoresMayores
        if((o3<60 || o3==-1) && (pm10<25 || pm10==-1) && (so2<63 || so2==-1) && (no2<100 || no2==-1))
        {
            devolver[3]=1;
        }
        else {
            if((o3<120 || o3==-1) && (pm10<50 || pm10==-1) && (so2<125 || so2==-1) && (no2<200 || no2==-1))
            {
                devolver[3]=2;
            }
            else{
                devolver[3]=3;
            }

        }

        Log.d("senores",String.valueOf(devolver[3]));
        Log.d("senores",String.valueOf((o3<60 || o3==-1) && (pm10<25 || pm10==-1) && (so2<63 || so2==-1) && (no2<100 || no2==-1)));

        //NINOS
        if((o3<120 || o3==-1) && (so2<125 || so2==-1))
        {
            devolver[0]=1;
        }
        else {
            if((o3<180 || o3==-1) && (so2<188 || so2==-1))
            {
                devolver[0]=2;
            }
            else{
                devolver[0]=3;
            }

        }

        //AIRELIBRE
        if((o3<60 || o3==-1) && (pm10<25 || pm10==-1) && (co<5000 || co==-1) && (so2<125 || so2==-1) && (no2<200 || no2==-1))
        {
            devolver[2]=1;
        }
        else {
            if((o3<180 || o3==-1) && (pm10<75 || pm10==-1) && (co<15000 || co==-1) && (so2<188 || so2==-1) && (no2<300 || no2==-1))
            {
                devolver[2]=2;
            }
            else{
                devolver[2]=3;
            }

        }

        //CERRADO
        if((pm10<50 || pm10==-1) && (co<5000 || co==-1))
        {
            devolver[1]=1;
        }
        else {
            if((pm10<75 || pm10==-1) && (co<15000 || co==-1))
            {
                devolver[1]=2;
            }
            else{
                devolver[1]=3;
            }

        }

        return devolver;
    }

    public static String diminutivo(String contaminante)
    {
        switch(contaminante.toUpperCase())
        {
            case "MONOXIDO DE CARBONO":
                return "CO";
            case "OXIDO DE NITROGENO":
                return "NO";
            case "PARTICULAS EN SUSPENSION DE 2.5 MICRAS":
                return "PM2.5";
            case "PARTICULAS EN SUSPENSION DE 10 MICRAS":
                return "PM10";
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
            case "P-XILENO":
                return "C6H4";
            case "ACIDO SULFHIDRICO":
                return "H2S";
            case "ETIL-BENCENO":
                return "C8H10";
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

    public static void inferir(Context contexto){
        AndroidBaseDatos baseDatos=new AndroidBaseDatos(contexto);
        ArrayList<String> patologias=baseDatos.getPatologias();

        if(ContaminacionHelper.enList(patologias,"Enfermedades del corazón")!=-1)
        {
            baseDatos.addSuscripcion("Particulas en suspension de 10 micras",3);
            baseDatos.addSuscripcion("Particulas en suspension de 10 micras",4);

            baseDatos.addSuscripcion("Monoxido de Carbono",4);
            baseDatos.addSuscripcion("Monoxido de Carbono",3);
            baseDatos.addSuscripcion("Monoxido de Carbono",2);
        }

        if(ContaminacionHelper.enList(patologias,"Enfermedades pulmonares")!=-1)
        {
            baseDatos.addSuscripcion("Particulas en suspension de 10 micras",3);
            baseDatos.addSuscripcion("Particulas en suspension de 10 micras",4);

            baseDatos.addSuscripcion("Ozono",4);
            baseDatos.addSuscripcion("Ozono",3);
            baseDatos.addSuscripcion("Ozono",2);
        }

        if(ContaminacionHelper.enList(patologias,"Asma")!=-1)
        {
            baseDatos.addSuscripcion("Particulas en suspension de 10 micras",3);
            baseDatos.addSuscripcion("Particulas en suspension de 10 micras",4);

            baseDatos.addSuscripcion("Ozono",4);
            baseDatos.addSuscripcion("Ozono",3);
            baseDatos.addSuscripcion("Ozono",2);
        }

        if(ContaminacionHelper.enList(patologias,",Menor de 19 años")!=-1)
        {
            baseDatos.addSuscripcion("Particulas en suspension de 10 micras",4);

            baseDatos.addSuscripcion("Ozono",4);
            baseDatos.addSuscripcion("Ozono",3);
            baseDatos.addSuscripcion("Ozono",2);
        }

        if(ContaminacionHelper.enList(patologias,"Mayor de 60 años")!=-1)
        {
            baseDatos.addSuscripcion("Particulas en suspension de 10 micras",3);
            baseDatos.addSuscripcion("Particulas en suspension de 10 micras",4);

            baseDatos.addSuscripcion("Ozono",4);
            baseDatos.addSuscripcion("Ozono",3);
            baseDatos.addSuscripcion("Ozono",2);
        }

    }

    public static int enList(ArrayList<String> vector,String esta)
    {
        for (int i=0;i<vector.size();i++)
        {
            if(vector.get(i).equals(esta))
                return i;
        }
        return -1;
    }

}
