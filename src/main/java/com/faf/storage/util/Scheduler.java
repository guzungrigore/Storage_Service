package com.faf.storage.util;

import com.faf.storage.domain.UserReservation;
import com.faf.storage.repository.UserReservationRepository;
import com.faf.storage.service.MailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Scheduler {

    private final MailService mailService;

    private final UserReservationRepository userReservationRepository;


    public Scheduler(MailService mailService, UserReservationRepository userReservationRepository) {
        this.mailService = mailService;
        this.userReservationRepository = userReservationRepository;
    }

    @Scheduled(cron = "${storage.schedule.cron}")
    public void checkReservationUsage() {
        List<UserReservation> reservations = userReservationRepository.findAll();
        for (UserReservation reservation : reservations) {
            double usedPercentage = (double) reservation.getUsedSize() / reservation.getTotalSize();
            if (usedPercentage > 0.9) {
                mailService.sendStorageAlertEmail(reservation.getUser());
            }
        }
    }
}
