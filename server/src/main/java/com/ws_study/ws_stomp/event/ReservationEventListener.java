package com.ws_study.ws_stomp.event;

import com.ws_study.ws_stomp.dto.response.ReservedDatesResponseDto;
import com.ws_study.ws_stomp.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ReservationEventListener {
    private final SimpMessagingTemplate messagingTemplate;
    private final ReservationService reservationService;

    @TransactionalEventListener(phase= TransactionPhase.AFTER_COMMIT)
    public void publish(ReservationEvent event){
        //캘린더
        ReservedDatesResponseDto reservedDates = reservationService.getReservedDates();
        messagingTemplate.convertAndSend("/topic/reservation", reservedDates);
    }
}
