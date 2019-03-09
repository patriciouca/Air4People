package es.uca.air4people.air4people.lista;

public class EstacionLista {

    private String titulo;
    private int numero;
    //private Drawable imagen;

    public EstacionLista(String titulo, int numero) {
        super();
        this.titulo = titulo;
        this.numero = numero;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}