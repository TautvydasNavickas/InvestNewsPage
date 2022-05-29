package lt.codeacademy.springmvc.exception;

public class InvestAccountNotFoundException extends RuntimeException{

    public InvestAccountNotFoundException (String message) {
        super(message);
    }
}
