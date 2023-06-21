package de.thb.MACJEE.Exeption;

public class JobHasNoCompanyExeption extends RuntimeException{

    public JobHasNoCompanyExeption(String message) { super(message); }
    public JobHasNoCompanyExeption(String message, Throwable cause) { super(message, cause); }
}
