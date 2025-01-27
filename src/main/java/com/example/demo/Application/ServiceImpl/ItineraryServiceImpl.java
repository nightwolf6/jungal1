package com.example.demo.Application.ServiceImpl;

import com.example.demo.Application.Model.Itinerary;
import com.example.demo.Application.Repository.ItineraryRepository;
import com.example.demo.Application.Service.ItineraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItineraryServiceImpl implements ItineraryService {

    private final ItineraryRepository itineraryRepository;

    @Autowired
    public ItineraryServiceImpl(ItineraryRepository itineraryRepository) {
        this.itineraryRepository = itineraryRepository;
    }

    @Override
    public List<Itinerary> getAllItineraries() {
        return itineraryRepository.findAll();
    }

    @Override
    public Optional<Itinerary> getItineraryById(Long id) {
        return itineraryRepository.findById(id);
    }

    @Override
    public List<Itinerary> getItinerariesByTourPackage_PackageId(Long tourPackageId) {
        // Implementar lógica para obtener itinerarios por ID de paquete turístico
        return itineraryRepository.findByTourPackage_PackageId(tourPackageId);
    }
    @Override
    public Itinerary saveItinerary(Itinerary itinerary) {
        return itineraryRepository.save(itinerary);
    }

    @Override
    public void deleteItinerary(Long id) {
        itineraryRepository.deleteById(id);
    }

    @Override
    public Itinerary createItinerary(Itinerary itinerary) {
        // Implementar lógica para la creación de nuevos itinerarios
        // Por ejemplo, validar y asignar valores por defecto
        return itineraryRepository.save(itinerary);
    }

    @Override
    public Itinerary updateItinerary(Itinerary itinerary) {
        // Implementar lógica para la actualización de itinerarios
        // Por ejemplo, validar y realizar la actualización en la base de datos
        return itineraryRepository.save(itinerary);
    }

    @Override
    public List<Itinerary> findByTourPackage_PackageId(Long tourPackageId) {
        return itineraryRepository.findByTourPackage_PackageId(tourPackageId);
    }
}