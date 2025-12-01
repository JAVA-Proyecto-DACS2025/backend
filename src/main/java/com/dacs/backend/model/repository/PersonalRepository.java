package com.dacs.backend.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dacs.backend.model.entity.Personal;


public interface PersonalRepository extends JpaRepository<Personal, Long> {


}
