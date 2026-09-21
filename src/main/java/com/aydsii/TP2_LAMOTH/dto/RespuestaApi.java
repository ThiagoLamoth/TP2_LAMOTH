package com.aydsii.TP2_LAMOTH.dto;

public class RespuestaApi<T> {
    private int status;
    private String messege; // asi lo pide la consigna, con ese "error" de tipeo incluido
    private T data;

    public RespuestaApi(int status, String messege, T data) {
        this.status = status;
        this.messege = messege;
        this.data = data;
    }

    // getters y setters (o usa Lombok con @Data)
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getMessege() { return messege; }
    public void setMessege(String messege) { this.messege = messege; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}