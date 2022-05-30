package lt.codeacademy.springmvc.repository.entity;

import lombok.Data;

@Data
public class EmailNotification {
    private String subject;
    private String recipient;
    private String body;

    public EmailNotification(String please_activate_your_account, String email, String message) {

    }
}
