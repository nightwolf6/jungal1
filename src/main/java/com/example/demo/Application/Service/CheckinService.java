package com.example.demo.Application.Service;

import com.example.demo.Application.Model.Checkin;

import java.util.List;
import java.util.Optional;

public interface CheckinService {

    Checkin createCheckin(Checkin checkin);

    Optional<Checkin> getCheckinById(Long id);

    boolean facialRecognitionApproved(Long checkinId);

    void updateFacialRecognitionStatus(Long checkinId, boolean approved);

    void deleteCheckin(Long id);

    List<Checkin> getAllCheckins();

}