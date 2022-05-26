package lt.codeacademy.springmvc.repository.entity;

import lombok.Data;

@Data
public class EmailNotification {
    private String subject;
    private String recipient;
    private String body;
}
