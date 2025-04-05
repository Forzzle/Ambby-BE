package com.forzzle.hodeum.common.util;

public class DefaultValidator {

    public static void notBlank(String target, String targetName) {
        if (target == null || target.isBlank()) {
            throw new IllegalArgumentException(targetName + "은 공백일 수 없습니다.");
        }
    }


    public static void maxLength(String target, int i, String targetName) {
        if (target.length() > i) {
            throw new IllegalArgumentException(targetName + "은 " + i + "자 보다 길 수 없습니다.");
        }
    }
}
