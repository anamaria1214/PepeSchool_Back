package com.example.pepeschoolback.config;
import java.util.HashMap;
import java.util.Map;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UsuarioActivo {
    // Instancia única inicializada directamente
    private static final UsuarioActivo INSTANCE = new UsuarioActivo();

    private String username;
    private Integer userType;
    private Integer userId;
    private final Map<String, Object> attributes = new HashMap<>();

    // Constructor privado para evitar instanciación
    private UsuarioActivo() {
        logout(); // Inicializar en estado "no logueado"
    }

    // Método estático para acceder a la instancia
    public static UsuarioActivo getInstance() {
        return INSTANCE;
    }

    // Métodos principales
    public void login(String username, Integer userType, Integer userId) {
        this.username = Objects.requireNonNull(username);
        this.userType = Objects.requireNonNull(userType);
        this.userId = Objects.requireNonNull(userId);
    }

    public void logout() {
        this.username = null;
        this.userType = null;
        this.userId = null;
        attributes.clear();
    }

    public boolean isLoggedIn() {
        return username != null;
    }

    // Getters con verificación de login
    public String getUsername() {
        checkLogin();
        return username;
    }

    public Integer getUserType() {
        checkLogin();
        return userType;
    }

    public Integer getUserId() {
        checkLogin();
        return userId;
    }

    // Métodos para atributos adicionales
    public void setAttribute(String key, Object value) {
        attributes.put(Objects.requireNonNull(key), value);
    }

    public Object getAttribute(String key) {
        return attributes.get(Objects.requireNonNull(key));
    }

    public void removeAttribute(String key) {
        attributes.remove(Objects.requireNonNull(key));
    }

    // Método privado para verificar login
    private void checkLogin() {
        if (!isLoggedIn()) {
            throw new IllegalStateException("Usuario no está logueado");
        }
    }
}