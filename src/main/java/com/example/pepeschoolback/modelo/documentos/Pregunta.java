package com.example.pepeschoolback.modelo.documentos;

public class Pregunta {
    private int id;
    private String enunciado;
    private String tipo;

    public Pregunta(int id, String enunciado, String tipo) {
        this.id = id;
        this.enunciado = enunciado;
        this.tipo = tipo;
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
}
