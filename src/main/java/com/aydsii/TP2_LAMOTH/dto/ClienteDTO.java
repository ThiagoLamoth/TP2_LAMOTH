package com.aydsii.TP2_LAMOTH.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ClienteDTO {
    @NotBlank(message = "no debe estar vacío")
    @Size(min = 2, message = "debe tener al menos 2 caracteres")
    private String nombre;

    @NotBlank(message = "no debe estar vacío")
    @Size(min = 2, message = "debe tener al menos 2 caracteres")
    private String apellido;

    @NotBlank(message = "no debe estar vacío")
    @Email(message = "debe ser un email válido")
    private String email;

    /*El @Pattern(regexp = "\\d*") con asterisco (no +) permite que el campo venga vacío o null sin fallar 
    — coincide con "opcional, pero si se informa, solo dígitos".
    */
    @Pattern(regexp = "\\d*", message = "solo debe contener dígitos")
    private String telefono;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

}
