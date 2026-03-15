package com.ws_study.ws_stomp.repository;

import com.ws_study.ws_stomp.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    //예약된 날짜 조회->
    // db에서 변환안한 이유 : cast결과가 java.sql.Date로 반환되는데 LocalDate로 자동변환이 안돼서
//    @Query("select distinct cast(r.startAt as date) from Reservation r")
//    List<LocalDate> findAllReservedDates();


    //전체 예약 조회 -> 전체 예약 데이터를 가져오고 그걸 localdate로 변환하여 반환해줌
    List<Reservation> findAll();
}
