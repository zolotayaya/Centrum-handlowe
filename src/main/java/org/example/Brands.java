package org.example;

/**
 * Enum reprezentujący dostępne marki lub działy w centrum handlowym.
 * Każda marka ma przypisany unikalny numer identyfikacyjny.
 */
public enum Brands {

    /** Dział z elektroniką. */
    Dział_elektroniki(1),

    /** Dział z urządzeniami gospodarstwa domowego. */
    Urządzenia_gospodarstwa_domowego(2),

    /** Sklep sportowy Decathlot. */
    Decathlot(3),

    /** Sklep z piłkami. */
    Sklep_z_piłkami(4),

    /** Dział z kwiatami. */
    Kwiaty(5),

    /** Sklep z owocami. */
    Sklep_z_owocami(6);

    /** Numer identyfikacyjny marki. */
    private final int name;

    /**
     * Konstruktor enumu przypisujący numer identyfikacyjny.
     *
     * @param name numer identyfikacyjny marki
     */
    Brands(int name) {
        this.name = name;
    }

    /**
     * Zwraca numer identyfikacyjny marki.
     *
     * @return numer marki
     */
    public int getNumber() {
        return name;
    }
}
