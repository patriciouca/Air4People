package es.uca.air4people.air4people.Servicio;

public class Estacion {

    private int mote_id;
    private double latitude,longitude;
    private String mote_name;

    public Estacion() {
    }

    public Estacion(int id, float latitud, float longitud, String mote_name) {
        this.mote_id = id;
        this.latitude = latitud;
        this.longitude = longitud;
        this.mote_name = mote_name;
    }

    public int getMote_id() {
        return mote_id;
    }

    public void setMote_id(int id) {
        this.mote_id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitud) {
        this.latitude = latitud;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitud) {
        this.longitude = longitud;
    }

    public String getMote_name() {
        return mote_name;
    }

    public void setMote_name(String mote_name) {
        this.mote_name = mote_name;
    }
}
