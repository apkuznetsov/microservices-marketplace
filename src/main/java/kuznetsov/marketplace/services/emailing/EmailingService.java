package kuznetsov.marketplace.services.emailing;

public interface EmailingService {

  void send(String emailTo, String subject, String message);

}
