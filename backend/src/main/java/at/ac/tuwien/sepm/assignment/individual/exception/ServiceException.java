package at.ac.tuwien.sepm.assignment.individual.exception;

public class ServiceException extends RuntimeException{
    public ServiceException(String message) {

        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
