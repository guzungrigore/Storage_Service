package com.faf.storage.controller;

import com.faf.storage.dto.ReservationDto;
import com.faf.storage.dto.ResponseDto;
import com.faf.storage.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllUsers() {
        return ResponseEntity.status(OK).body(reservationService.getAllReservations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> getReservationById(@PathVariable Long id) {
        return ResponseEntity.status(OK).body(reservationService.getReservationById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ReservationDto> getReservationByUserId(@PathVariable Long userId) {
        return ResponseEntity.status(OK).body(reservationService.getReservationByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<ResponseDto> saveReservation(@RequestBody @Valid ReservationDto reservationDto) {
        return ResponseEntity.status(CREATED).body(reservationService.saveReservation(reservationDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateReservation(@PathVariable Long id,
                                                  @RequestBody @Valid ReservationDto reservationDto) {
        return ResponseEntity.status(OK).body(reservationService.updateReservation(id, reservationDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteReservation(@PathVariable Long id) {
        return ResponseEntity.status(OK).body(reservationService.deleteReservation(id));
    }
}
