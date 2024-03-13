package com.for_comprehension.function;

import com.for_comprehension.function.framework.Getter;

import java.util.Optional;

class L2_Monad {

    public static void main(String[] args) {
        Getter getter = Getter.instance();
    }

    public static void declarativeOptionalityHandling(String[] args) {
        String result = findUser2()
          .map(String::toUpperCase)
          .map(String::toLowerCase)
          .orElse("default");
    }

    public static void imperativeOptionalityHandling(String[] args) {
        String u1 = findUser1();
        if (u1 != null) {
            String uppercased = u1.toUpperCase();
            if (uppercased != null) {
                String lowercased = uppercased.toLowerCase();
                if (lowercased != null) {
                    u1 = lowercased;
                } else {
                    u1 = "default";
                }
            }
        }
    }

    public static String findUser1() {
        return "John";
    }

    public static Optional<String> findUser2() {
        return Optional.of("John");
    }

    private static class GetterOptionalAdapter<T> {
        private final Getter<T> getter;

        GetterOptionalAdapter(Getter getter) {
            this.getter = getter;
        }

        Optional<T> get() {
            return Optional.ofNullable(getter.get());
        }
    }
}
