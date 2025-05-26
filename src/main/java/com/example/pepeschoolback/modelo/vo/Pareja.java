package com.example.pepeschoolback.modelo.vo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Pareja {
    private final StringProperty conceptoA;
    private final StringProperty conceptoB;

    public Pareja(String conceptoA, String conceptoB) {
        this.conceptoA = new SimpleStringProperty(conceptoA);
        this.conceptoB = new SimpleStringProperty(conceptoB);
    }

    // Getters y properties
    public String getConceptoA() { return conceptoA.get(); }
    public StringProperty conceptoAProperty() { return conceptoA; }

    public String getConceptoB() { return conceptoB.get(); }
    public StringProperty conceptoBProperty() { return conceptoB; }
}
