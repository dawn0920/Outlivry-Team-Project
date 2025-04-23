package org.example.outlivryteamproject.common;

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

    public static void updateBoolean(Boolean value, Consumer<Boolean> setter) {
        if(value != null) {
            setter.accept(value);
        }
    }

}
