package es.uca.air4people.air4people.ReciclerEstaciones;

import java.util.List;

import es.uca.air4people.air4people.Servicio.Medicion;

public class EstacionLista {

    private String titulo;
    private List<Medicion> predicciones;

    public EstacionLista(String titulo) {
        super();
        this.titulo = titulo;
    }

    public EstacionLista(String titulo, List<Medicion> predicciones) {
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

    public List<Medicion> getPredicciones() {
        return predicciones;
    }

    public void setPredicciones(List<Medicion> predicciones) {
        this.predicciones = predicciones;
    }
}