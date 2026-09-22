package com.aydsii.TP2_LAMOTH.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aydsii.TP2_LAMOTH.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    /*JpaRepository te da gratis los métodos básicos (save, findAll, findById, etc.), 
    y con solo declarar existsByEmail(String email) con ese nombre exacto, 
    Spring Data JPA genera solo la consulta SQL correspondiente
    */
    boolean existsByEmail(String email);
}
