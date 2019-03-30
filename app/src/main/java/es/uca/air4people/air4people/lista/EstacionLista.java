package es.uca.air4people.air4people.lista;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import es.uca.air4people.air4people.Servicio.Prediccion;

public class EstacionLista {

    private String titulo;
    private List<Prediccion> predicciones;
    //private Drawable imagen;

    public EstacionLista(String titulo, List<Prediccion> predicciones) {
        super();
        this.titulo = titulo;
        this.predicciones = predicciones;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Prediccion> getPredicciones() {
        return predicciones;
    }

    public void setPredicciones(List<Prediccion> predicciones) {
        this.predicciones = predicciones;
    }
}