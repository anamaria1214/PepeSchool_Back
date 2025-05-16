package com.example.pepeschoolback.config;
import java.util.HashMap;
import java.util.Map;

public class UsuarioActivo {
    private static UsuarioActivo instance;
    private String username;
    private String userType;
    private Integer userId;
    private Map<String, Object> attributes;

    // Constructor privado para evitar instanciación
    private UsuarioActivo() {
        attributes = new HashMap<>();
    }

    // Método para obtener la instancia (patrón Singleton)
    public static UsuarioActivo getInstance() {
        if (instance == null) {
            synchronized (UsuarioActivo.class) {
                if (instance == null) {
                    instance = new UsuarioActivo();
                }
            }
        }
        return instance;
    }

    public void login(String username, String userType, Integer userId) {
        this.username = username;
        this.userType = userType;
        this.userId = userId;
    }

    public void logout() {
        this.username = null;
        this.userType = null;
        this.userId = null;
        this.attributes.clear();
    }

    public boolean isLoggedIn() {
        return username != null;
    }

    public String getUsername() {
        return username;
    }

    public String getUserType() {
        return userType;
    }

    public Integer getUserId() {
        return userId;
    }
    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public void removeAttribute(String key) {
        attributes.remove(key);
    }

    // Método para limpiar la instancia (útil para tests)
    public static void cleanSession() {
        instance = null;
    }
}