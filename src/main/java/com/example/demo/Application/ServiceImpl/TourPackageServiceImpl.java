package com.example.demo.Application.ServiceImpl;

import com.example.demo.Application.Model.TourPackage;
import com.example.demo.Application.Repository.TourPackageRepository;
import com.example.demo.Application.Service.TourPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourPackageServiceImpl implements TourPackageService {

    private final TourPackageRepository tourPackageRepository;

    @Autowired
    public TourPackageServiceImpl(TourPackageRepository tourPackageRepository) {
        this.tourPackageRepository = tourPackageRepository;
    }

    @Override
    public List<TourPackage> getAllTourPackages() {
        return tourPackageRepository.findAll();
    }

    @Override
    public Optional<TourPackage> getTourPackageById(Long id) {
        return tourPackageRepository.findById(id);
    }

    @Override
    public TourPackage saveTourPackage(TourPackage tourPackage) {
        return tourPackageRepository.save(tourPackage);
    }

    @Override
    public void deleteTourPackage(Long id) {
        tourPackageRepository.deleteById(id);
    }

    @Override
    public TourPackage createTourPackage(TourPackage tourPackage) {
        // Implementar lógica para la creación de nuevos paquetes turísticos
        // Por ejemplo, validar y asignar valores por defecto
        return tourPackageRepository.save(tourPackage);
    }

    @Override
    public TourPackage updateTourPackage(TourPackage tourPackage) {
        // Implementar lógica para la actualización de paquetes turísticos
        // Por ejemplo, validar y realizar la actualización en la base de datos
        return tourPackageRepository.save(tourPackage);
    }
}