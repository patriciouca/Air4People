package es.uca.air4people.air4people.Servicio;

import java.util.Date;

public class Medicion {

    private String mote_name,des_kind;
    private float value;
    private long time;
    private int percentage,kind;
    private String unit;

    public Medicion(String mote_name, String des_kind, float value, int percentage, int kind, int time, String unit) {
        this.mote_name = mote_name;
        this.des_kind = des_kind;
        this.value = value;
        this.percentage = percentage;
        this.kind = kind;
        this.time = time;
        this.unit = unit;
    }

    public Medicion() {
    }

    public String getMote_name() {
        return mote_name;
    }

    public void setMote_name(String mote_name) {
        this.mote_name = mote_name;
    }

    public String getDes_kind() {
        return des_kind;
    }

    public void setDes_kind(String des_kind) {
        this.des_kind = des_kind;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
