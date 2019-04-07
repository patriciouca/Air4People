package es.uca.air4people.air4people.Servicio;

import java.util.List;

public class Mediciones {
    private List<Medicion> mediciones;
    private String fecha;

    public Mediciones(List<Medicion> mediciones) {
        this.mediciones = mediciones;
    }

    public Mediciones(List<Medicion> mediciones, String fecha) {
        this.mediciones = mediciones;
        this.fecha = fecha;
    }

    public List<Medicion> getMediciones() {
        return mediciones;
    }

    public void setMediciones(List<Medicion> mediciones) {
        this.mediciones = mediciones;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
