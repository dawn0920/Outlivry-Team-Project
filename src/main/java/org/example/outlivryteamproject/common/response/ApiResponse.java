package org.example.outlivryteamproject.common.response;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private String message;

    private T contents;

    public ApiResponse(String message, T contents) {
        this.message = message;
        this.contents = contents;
    }

    public ApiResponse(String message) {
        this.message = message;
    }
}
