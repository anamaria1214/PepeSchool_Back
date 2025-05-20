package com.example.pepeschoolback.modelo.documentos;

public class Pregunta {
    private int id;
    private String enunciado;
    private int tipo;

    public Pregunta(int id, String enunciado, int tipo) {
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

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Pregunta{" +
                "id=" + id +
                ", enunciado='" + enunciado + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}
