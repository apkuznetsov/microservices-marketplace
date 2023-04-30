package kuznetsov.marketplace.backend.service;

public interface EmailingService {

    void send(String emailTo, String subject, String message);

}
