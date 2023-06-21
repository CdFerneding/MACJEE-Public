package de.thb.MACJEE.Exeption;

public class JobNotFoundExeption extends RuntimeException{
    public JobNotFoundExeption(String message) { super(message); }
    public JobNotFoundExeption(String message, Throwable cause) { super(message, cause); }
}
