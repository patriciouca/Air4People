package es.uca.air4people.air4people;
public class ComprobarContaminacion {

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
}
