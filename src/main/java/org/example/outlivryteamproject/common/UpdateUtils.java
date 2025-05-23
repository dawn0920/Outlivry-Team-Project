package org.example.outlivryteamproject.common;

import java.time.LocalTime;
import java.util.function.Consumer;
import org.springframework.util.StringUtils;

// 기존 값을 수정할때 새로운 값이 있으면 수행하고 없다면 기존 값을 유지
public class UpdateUtils {

    public static void updateString(String value, Consumer<String> setter) {
        if(StringUtils.hasText(value)) {
            setter.accept(value);
        }
    }

    public static void updateLong(Long value, Consumer<Long> setter) {
        if(value != null) {
            setter.accept(value);
        }
    }

    public static void updateLocalTime(LocalTime value, Consumer<LocalTime> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }

    // 애 안쓰면 마지막에 배포 전 뺄것!
    public static void updateBoolean(Boolean value, Consumer<Boolean> setter) {
        if(value != null) {
            setter.accept(value);
        }
    }

}
