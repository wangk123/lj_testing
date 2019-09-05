package com.lj.testing.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Component
public class MailServiceImpl {

    @Autowired
    private JavaMailSender mailSender;
    @Value("${mail.fromMail.addr}")
    private String from;

    public void sendHtmlMail(String[] to, String[] cc, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setCc(cc);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("邮件发送失败！", e);
        }
    }
}
