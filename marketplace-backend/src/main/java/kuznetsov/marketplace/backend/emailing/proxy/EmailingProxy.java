package kuznetsov.marketplace.backend.emailing.proxy;

public interface EmailingProxy {

    void send(String to, String subject, String message);

}
