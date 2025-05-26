package com.example.pepeschoolback.modelo.documentos;

public class PreguntaExamen {
    private int id;
    private String enunciado;
    private String respuesta;
    private int peso;

    public PreguntaExamen(int id, String enunciado, String respuesta, int peso) {
        this.id = id;
        this.enunciado = enunciado;
        this.respuesta = respuesta;
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

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }
}
