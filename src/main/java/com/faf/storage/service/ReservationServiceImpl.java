package com.faf.storage.service;

import com.faf.storage.dto.ReservationDto;
import com.faf.storage.dto.ResponseDto;
import com.faf.storage.service.api.ReservationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Override
    public List<ReservationDto> getAllReservations() {
        return null;
    }

    @Override
    public ReservationDto getReservationById(Long id) {
        return null;
    }

    @Override
    public ReservationDto getReservationByUserId(Long userId) {
        return null;
    }

    @Override
    public ResponseDto saveReservation(ReservationDto reservationDto) {
        return null;
    }

    @Override
    public ResponseDto updateReservation(Long id, ReservationDto reservationDto) {
        return null;
    }

    @Override
    public ResponseDto deleteReservation(Long id) {
        return null;
    }
}
