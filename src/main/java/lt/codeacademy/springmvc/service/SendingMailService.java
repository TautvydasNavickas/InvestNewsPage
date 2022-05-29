package lt.codeacademy.springmvc.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lt.codeacademy.springmvc.exception.InvestAccountNotFoundException;
import lt.codeacademy.springmvc.repository.entity.EmailNotification;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
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
            messageHelper.setTo(emailNotification.getRecipient());
            messageHelper.setSubject(emailNotification.getSubject());
            messageHelper.setText(mailMessageContentService.build(emailNotification.getBody()));
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Activation email sent!!");
        } catch (MailException e) {
            throw new InvestAccountNotFoundException("Exception occurred when sending mail to " + emailNotification.getRecipient());
        }
    }
}
