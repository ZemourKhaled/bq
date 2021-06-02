package com.example.bq.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompteRepository extends JpaRepository<Compte, Integer> {
    List<Compte> findByUserId(Integer userId);
}