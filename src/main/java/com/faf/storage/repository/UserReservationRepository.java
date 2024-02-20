package com.faf.storage.repository;

import com.faf.storage.domain.User;
import com.faf.storage.domain.UserReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserReservationRepository extends JpaRepository<UserReservation, Long> {

    void deleteByUser(User user);

    Optional<UserReservation> findByUser(User user);
}
