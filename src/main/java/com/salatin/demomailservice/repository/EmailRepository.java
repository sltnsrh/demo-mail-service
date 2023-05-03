package com.salatin.demomailservice.repository;

import com.salatin.demomailservice.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, Integer> {
}
