package com.faf.storage.service.api;

import com.faf.storage.dto.ReservationDto;
import com.faf.storage.dto.ResponseDto;

import java.util.List;

public interface ReservationService {

    List<ReservationDto> getAllReservations();

    ReservationDto getReservationById(Long id);

    ReservationDto getReservationByUserId(Long userId);

    ResponseDto saveReservation(ReservationDto reservationDto);

    ResponseDto updateReservation(Long id, ReservationDto reservationDto);

    ResponseDto deleteReservation(Long id);

}
