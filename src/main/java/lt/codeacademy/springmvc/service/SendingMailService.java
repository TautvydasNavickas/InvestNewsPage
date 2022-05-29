package lt.codeacademy.springmvc.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lt.codeacademy.springmvc.repository.entity.EmailNotification;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class SendingMailService {

    private final JavaMailSender mailSender;
    private final MailMessageContentService mailMessageContentService;


    void sendMail(EmailNotification emailNotification) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springreddit@email.com");
            messageHelper.setTo(EmailNotification.getRecipient());
            messageHelper.setSubject(EmailNotification.getSubject());
            messageHelper.setText(mailMessageContentServicer.build(EmailNotification.getBody()));
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Activation email sent!!");
        } catch (MailException e) {
            throw new SpringRedditException("Exception occurred when sending mail to " + notificationEmail.getRecipient());
        }
    }
}
