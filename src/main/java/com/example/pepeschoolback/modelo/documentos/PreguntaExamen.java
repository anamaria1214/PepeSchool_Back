package com.example.pepeschoolback.modelo.documentos;

public class PreguntaExamen {
    private int id;
    private String enunciado;
    private String tipo;
    private int peso;

    public PreguntaExamen(int id, String enunciado, String tipo, int peso) {
        this.id = id;
        this.enunciado = enunciado;
        this.tipo = tipo;
        this.peso = peso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }
}
