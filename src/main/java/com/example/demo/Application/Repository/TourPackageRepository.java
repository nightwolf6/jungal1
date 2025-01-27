package com.example.demo.Application.Repository;

import com.example.demo.Application.Model.TourPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourPackageRepository extends JpaRepository<TourPackage, Long> {
    // Puedes agregar métodos personalizados según tus necesidades de consulta
}
