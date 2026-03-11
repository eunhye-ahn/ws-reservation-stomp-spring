package com.ws_study.ws_stomp.repository;

import com.ws_study.ws_stomp.domain.Reservation;
import com.ws_study.ws_stomp.dto.ReservationRequestDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    //증복체크
    @Query("select count(r)>0 from Reservation r where r.startAt = :startAt")
    boolean existsByStartAt(@Param("startAt") LocalDateTime startAt);



}
