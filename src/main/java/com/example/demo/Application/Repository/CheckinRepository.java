package com.example.demo.Application.Repository;

import com.example.demo.Application.Model.Checkin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CheckinRepository extends JpaRepository<Checkin, Long> {

}
