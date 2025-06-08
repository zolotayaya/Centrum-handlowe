package org.example.app;

public enum Colors{
        RESET("\u001B[0m"),
        RED("\u001B[31m"),
        GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"),
        BLUE("\u001B[34m"),
        PURPLE("\u001B[35m"),
        CYAN("\u001B[36m"),
        BOLD("\u001B[1m");

        private final String code;

        Colors(String code) {
            this.code = code;
        }

        public String get() {
            return code;
        }

        @Override
        public String toString() {
            return code;
        }
    }


