package com.example.demo.Application.Service;

import com.example.demo.Application.Model.Itinerary;
import java.util.List;
import java.util.Optional;

public interface ItineraryService {

    List<Itinerary> getAllItineraries();

    Optional<Itinerary> getItineraryById(Long id);

    List<Itinerary> getItinerariesByTourPackage_PackageId(Long packageId);

    Itinerary saveItinerary(Itinerary itinerary);

    void deleteItinerary(Long id);

    Itinerary createItinerary(Itinerary itinerary);

    Itinerary updateItinerary(Itinerary itinerary);

    List<Itinerary> findByTourPackage_PackageId(Long tourPackageId);
}