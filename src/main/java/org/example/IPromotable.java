package org.example;

/**
 * Interfejs definiujący zachowanie związane z promocjami.
 * Klasy implementujące ten interfejs powinny określać, kiedy produkt
 * lub usługa kwalifikuje się do promocji oraz jak promocja jest realizowana.
 */
public interface IPromotable {

    /**
     * Sprawdza, czy spełnione są warunki do zastosowania promocji.
     *
     * @return {@code true} jeśli warunki promocji są spełnione, w przeciwnym razie {@code false}
     */
    public boolean checkPromotionCondition();

    /**
     * Wykonuje działania związane z zastosowaniem promocji.
     *
     * @return {@code true} jeśli promocja została pomyślnie zastosowana, w przeciwnym razie {@code false}
     */
    public boolean executePromotion();
}
