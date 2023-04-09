package kuznetsov.marketplace.backend.proxy;

public interface EmailingService {

    void send(String emailTo, String subject, String message);

}
