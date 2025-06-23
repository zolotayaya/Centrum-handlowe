package org.example.app;

/**
 * Enum {@code Colors} zawiera kody ANSI służące do zmiany koloru i stylu tekstu w konsoli.
 * Każdy element reprezentuje określony kolor lub styl (np. pogrubienie).
 * <p>
 * Używamy tych kodów do kolorowania tekstu wypisywanego w terminalu.
 */
public enum Colors {
    /**
     * Resetuje kolor i styl tekstu.
     */
    RESET("\u001B[0m"),

    /**
     * Czerwony kolor tekstu.
     */
    RED("\u001B[31m"),

    /**
     * Zielony kolor tekstu.
     */
    GREEN("\u001B[32m"),

    /**
     * Żółty kolor tekstu.
     */
    YELLOW("\u001B[33m"),

    /**
     * Niebieski kolor tekstu.
     */
    BLUE("\u001B[34m"),

    /**
     * Fioletowy kolor tekstu.
     */
    PURPLE("\u001B[35m"),

    /**
     * Cyjanowy kolor tekstu.
     */
    CYAN("\u001B[36m"),

    /**
     * Pogrubienie tekstu.
     */
    BOLD("\u001B[1m");

    /**
     * Prywatne pole przechowujące kod ANSI dla danego stylu lub koloru.
     */
    private final String code;

    /**
     * Konstruktor enumu przypisujący kod ANSI do danego elementu.
     *
     * @param code kod ANSI
     */
    Colors(String code) {
        this.code = code;
    }

    /**
     * Zwraca kod ANSI przypisany do danego elementu.
     *
     * @return kod ANSI jako {@code String}
     */
    public String get() {
        return code;
    }

    /**
     * Nadpisuje metodę {@code toString}, aby zwracała kod ANSI.
     * Dzięki temu możliwe jest bezpośrednie użycie elementów enumu w wypisywaniu tekstu.
     *
     * @return kod ANSI jako {@code String}
     */
    @Override
    public String toString() {
        return code;
    }
}



