package org.example.outlivryteamproject.exception;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CustomExceptionResponse {

    private String message;
    private String path;
    private LocalDateTime dateTime;

}
