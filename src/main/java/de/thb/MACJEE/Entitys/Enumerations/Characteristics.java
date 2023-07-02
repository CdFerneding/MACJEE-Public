package de.thb.MACJEE.Entitys.Enumerations;

public enum Characteristics {
    Mathe, Biologie, Physik, Deutsch, Chemie, Englisch, Sport, Informatik;
    // PE = PHYSICAL EDUCATION
    // CS = COMPUTER SCIENCE
    public static int getNumberOfCharacteristics() {
        return values().length;
    }
}
