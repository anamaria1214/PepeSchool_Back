package com.example.pepeschoolback.modelo.documentos;

public class Grupo {

    private String materia;
    private String grupo;
    private String diaClase;
    private String horaClase;

    public Grupo(String materia, String grupo, String diaClase, String horaClase) {
        this.materia = materia;
        this.grupo = grupo;
        this.diaClase = diaClase;
        this.horaClase = horaClase;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getDiaClase() {
        return diaClase;
    }

    public void setDiaClase(String diaClase) {
        this.diaClase = diaClase;
    }

    public String getHoraClase() {
        return horaClase;
    }

    public void setHoraClase(String horaClase) {
        this.horaClase = horaClase;
    }
}
