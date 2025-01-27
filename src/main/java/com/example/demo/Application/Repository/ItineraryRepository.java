package com.example.demo.Application.Repository;

import com.example.demo.Application.Model.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {

    List<Itinerary> findByTourPackage_PackageId(Long tourPackageId);

    // Puedes agregar métodos personalizados según tus necesidades de consulta
}