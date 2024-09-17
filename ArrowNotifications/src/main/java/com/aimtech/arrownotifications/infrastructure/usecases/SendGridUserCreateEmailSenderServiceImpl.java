package com.aimtech.arrownotifications.infrastructure.usecases;

import com.aimtech.arrownotifications.core.properties.AppProperties;
import com.aimtech.arrownotifications.domain.dto.request.UserCreateEmailSenderRequest;
import com.aimtech.arrownotifications.domain.usecases.UserCreateEmailSenderService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;

@RequiredArgsConstructor
public class SendGridUserCreateEmailSenderServiceImpl implements UserCreateEmailSenderService {

    private final Logger LOG = LoggerFactory.getLogger(SendGridUserCreateEmailSenderServiceImpl.class);
    private final AppProperties appProperties;
    private final SendGrid sendGrid;
    private final TemplateEngine templateEngine;

    private final String contentType = "text/html";
    private final String subject = "Novo Usuário";

    @Override
    public void execute(UserCreateEmailSenderRequest dto) {
        String senderFrom = this.appProperties.getEmailSender().getFrom();
        String friendlyName = this.appProperties.getEmailSender().getFriendlyName();
        Email from = new Email(senderFrom, friendlyName);
        Email to = new Email(dto.getUserEmail());

        Context context = new Context();
        context.setVariable("firstName", dto.getFirstname());
        context.setVariable("lastName", dto.getLastname());
        context.setVariable("username", dto.getUsername());
        context.setVariable("password", dto.getPassword());
        Content content = new Content(
                this.contentType,
                templateEngine.process(
                        "new_user_email_notification_pt-BR.html",
                        context
                )
        );

        Mail mail = new Mail(from, this.subject, to, content);

        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            LOG.info("Sending email to: {}", dto.getUserEmail());
            Response response = sendGrid.api(request);
            if (response.getStatusCode() >= 400) {
                LOG.error("Send email failed: {}", response.getBody());
            } else {
                LOG.info("Send email successful! Status: {}", response.getStatusCode());
            }

        } catch (IOException ex) {
            LOG.error("Send email is failed: {}", ex.getMessage());
            throw new RuntimeException("Failed to send email: " + ex.getMessage());
        }
    }
}
