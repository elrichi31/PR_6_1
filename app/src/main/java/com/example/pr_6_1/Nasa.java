package com.example.pr_6_1;
public class Nasa {
    private String titulo;
    private String http;
    private String url;

    public Nasa(String titulo, String http, String url) {
        this.titulo = titulo;
        this.http = http;
        this.url = url;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getHttp() {
        return http;
    }

    public String getUrl() {
        return url;
    }
}
