package org.example.outlivryteamproject.config.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.example.outlivryteamproject.domain.order.dto.responseDto.OrderResponseDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class OrderStatusLoggingAspect {

    @AfterReturning(
            pointcut = "@annotation(org.example.outlivryteamproject.config.aop.annotaion.OrderStatusLogger)",
            returning = "result"
    )
    public void LogOrderStatus(Object result) throws Throwable {

        if (result instanceof OrderResponseDto responseDto) {
            Long storeId = responseDto.getStoreId();
            Long orderId = responseDto.getOrderId();
            LocalDateTime time = LocalDateTime.now();

            log.info("주문 상태가 변경되었습니다 - 가게 ID: {}, 주문 ID: {}, 요청 시각: {}", storeId, orderId, time);

        } else {
            log.warn("");
        }
    }
}
