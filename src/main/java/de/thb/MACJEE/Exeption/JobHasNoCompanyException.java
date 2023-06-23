package de.thb.MACJEE.Exeption;

public class JobHasNoCompanyException extends RuntimeException{

    public JobHasNoCompanyException(String message) { super(message); }
    public JobHasNoCompanyException(String message, Throwable cause) { super(message, cause); }
}
