package kuznetsov.marketplace.server.service;

public interface EmailingService {

    void send(String emailTo, String subject, String message);

}
