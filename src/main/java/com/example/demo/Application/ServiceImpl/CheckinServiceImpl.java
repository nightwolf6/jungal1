package com.example.demo.Application.ServiceImpl;

import com.example.demo.Application.Model.Checkin;
import com.example.demo.Application.Repository.CheckinRepository;
import com.example.demo.Application.Service.CheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CheckinServiceImpl implements CheckinService {

    private final CheckinRepository checkinRepository;

    @Autowired
    public CheckinServiceImpl(CheckinRepository checkinRepository) {
        this.checkinRepository = checkinRepository;
    }

    @Override
    public Checkin createCheckin(Checkin checkin) {
        // Aquí puedes incluir cualquier lógica adicional antes de guardar el check-in
        return checkinRepository.save(checkin);
    }

    @Override
    public Optional<Checkin> getCheckinById(Long id) {
        return checkinRepository.findById(id);
    }

    @Override
    public boolean facialRecognitionApproved(Long checkinId) {
        // Busca el check-in por su ID y devuelve el estado de reconocimiento facial
        return checkinRepository.findById(checkinId)
                .map(Checkin::getFacialRecognitionApproved)
                .orElseThrow(() -> new RuntimeException("Check-in no encontrado con ID: " + checkinId));
    }

    @Override
    public void updateFacialRecognitionStatus(Long checkInId, boolean approved) {
        // Actualiza el estado del reconocimiento facial para el check-in
        Checkin checkin = checkinRepository.findById(checkInId)
                .orElseThrow(() -> new RuntimeException("Check-in no encontrado con ID: " + checkInId));

        checkin.setFacialRecognitionApproved(approved);
        checkinRepository.save(checkin);
    }

    @Override
    public void deleteCheckin(Long id) {
        checkinRepository.deleteById(id);
    }

    @Override
    public List<Checkin> getAllCheckins() {
        return checkinRepository.findAll();
    }
}

