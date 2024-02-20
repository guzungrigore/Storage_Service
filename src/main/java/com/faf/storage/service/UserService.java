package com.faf.storage.service;

import com.faf.storage.domain.Authority;
import com.faf.storage.domain.User;
import com.faf.storage.domain.UserReservation;
import com.faf.storage.dto.request.SignUpRequest;
import com.faf.storage.exception.EmailAlreadyUsedException;
import com.faf.storage.exception.UsernameAlreadyUsedException;
import com.faf.storage.repository.AuthorityRepository;
import com.faf.storage.repository.UserRepository;
import com.faf.storage.repository.UserReservationRepository;
import com.faf.storage.security.AuthoritiesConstants;
import com.faf.storage.util.RandomUtil;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserReservationRepository reservationRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    public UserService(UserReservationRepository reservationRepository, UserRepository userRepository,
                       PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
    }

    public User signUp(SignUpRequest request) {
        userRepository
            .findOneByLogin(request.login().toLowerCase())
            .ifPresent(existingUser -> {
                boolean removed = removeNonActivatedUser(existingUser);
                if (!removed) {
                    throw new UsernameAlreadyUsedException();
                }
            });
        userRepository
            .findOneByEmailIgnoreCase(request.email())
            .ifPresent(existingUser -> {
                boolean removed = removeNonActivatedUser(existingUser);
                if (!removed) {
                    throw new EmailAlreadyUsedException();
                }
            });

        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(request.password());
        newUser.setLogin(request.login().toLowerCase());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(request.firstName());
        newUser.setLastName(request.lastName());
        if (request.email() != null) {
            newUser.setEmail(request.email().toLowerCase());
        }
        newUser.setImageUrl(request.imageUrl());
        newUser.setLangKey(request.langKey());
        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());

        newUser.setLangKey("en");

        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);

        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);

        UserReservation reservation = new UserReservation();
        reservation.setTotalSize(request.reservedSpace() * 1024 * 1024);
        reservation.setUsedSize(0L);
        reservation.setActivated(true);
        reservation.setUser(newUser);

        reservationRepository.save(reservation);


        return newUser;
    }

    private boolean removeNonActivatedUser(User existingUser) {
        if (existingUser.isActivated()) {
            return false;
        }

        UserReservation existingReservation = reservationRepository.findByUser(existingUser)
                .orElseThrow(EntityNotFoundException::new);

        reservationRepository.delete(existingReservation);
        userRepository.delete(existingUser);
        userRepository.flush();
        return true;
    }
}
