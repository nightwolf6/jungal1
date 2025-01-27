package com.example.demo.Application.Service;

import com.example.demo.Application.Model.TourPackage;
import java.util.List;
import java.util.Optional;

public interface TourPackageService {

    List<TourPackage> getAllTourPackages();

    Optional<TourPackage> getTourPackageById(Long id);

    TourPackage saveTourPackage(TourPackage tourPackage);

    void deleteTourPackage(Long id);

    TourPackage createTourPackage(TourPackage tourPackage);

    TourPackage updateTourPackage(TourPackage tourPackage);
}