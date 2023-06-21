package de.thb.MACJEE.Exeption;

public class JobNotAvailableExeption extends RuntimeException{
    public JobNotAvailableExeption(String message) { super(message); }
    public JobNotAvailableExeption(String message, Throwable cause) { super(message, cause); }
}
