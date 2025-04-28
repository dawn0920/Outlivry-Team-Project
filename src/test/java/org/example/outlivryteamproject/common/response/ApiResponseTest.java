package org.example.outlivryteamproject.common.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ApiResponseTest {

    @Test
    void getMessage() {
        String message = "요청 성공";
        Integer contents = 123;

        // when
        ApiResponse<Integer> response = new ApiResponse<>(message, contents);

        // then
        assertThat(response.getMessage()).isEqualTo(message);
        assertThat(response.getContents()).isEqualTo(contents);
    }

    @Test
    void getContents() {
        String message = "요청 성공";

        // when
        ApiResponse<String> response = new ApiResponse<>(message);

        // then
        assertThat(response.getMessage()).isEqualTo(message);
        assertThat(response.getContents()).isNull();
    }
}