package com.example.pepeschoolback.modelo.documentos;

public class Pregunta {
    private int id;
    private String descripcion;
    private String respuesta;
    private Integer peso;
    private String tipo;

    public Pregunta(int id, String descripcion, String respuesta, Integer peso, String tipo) {
        this.id = id;
        this.descripcion = descripcion;
        this.respuesta = respuesta;
        this.peso=peso;
        this.tipo=tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
