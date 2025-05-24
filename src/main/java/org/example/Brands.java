package org.example;

public enum Brands {
    Dział_elektroniki(1),
    Urządzenia_gospodarstwa_domowego(2),
    Decathlot(3),
    Sklep_z_piłkami(4),
    Kwiaty(5),
    Sklep_z_owocami(6)
    ;
     int name;
    Brands(int name) {
        this.name = name;
    }
    public int getNumber() {
        return name;
    }
}
