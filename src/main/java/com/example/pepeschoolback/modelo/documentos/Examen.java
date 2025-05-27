package com.example.pepeschoolback.modelo.documentos;

import java.time.LocalDateTime;
import java.util.Date;

public class Examen {
    private String nombre;
    private String descripcion;
    private int cantpreguntas;
    private Date fechaPresentacion;
    private String materia;

    public Examen(String nombre, String descripcion, int cantpreguntas, Date fechaPresentacion, String materia) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantpreguntas = cantpreguntas;
        this.fechaPresentacion = fechaPresentacion;
        this.materia = materia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantpreguntas() {
        return cantpreguntas;
    }

    public void setCantpreguntas(int cantpreguntas) {
        this.cantpreguntas = cantpreguntas;
    }

    public Date getFechaPresentacion() {
        return fechaPresentacion;
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        this.fechaPresentacion = fechaPresentacion;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    @Override
    public String toString() {
        return "Examen{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", cantpreguntas=" + cantpreguntas +
                ", fechaPresentacion=" + fechaPresentacion +
                ", materia='" + materia + '\'' +
                '}';
    }
}
