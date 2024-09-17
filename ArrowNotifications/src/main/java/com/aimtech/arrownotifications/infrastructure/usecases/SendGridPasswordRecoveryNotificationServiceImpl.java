package com.aimtech.arrownotifications.infrastructure.usecases;

import com.aimtech.arrownotifications.core.properties.AppProperties;
import com.aimtech.arrownotifications.domain.usecases.PasswordRecoveryNotificationService;
import com.aimtech.arrownotifications.domain.usecases.UserCreateEmailSenderService;
import com.aimtech.arrownotifications.infrastructure.messaging.message.PasswordRecoveryNotificationMessage;
import com.aimtech.arrownotifications.infrastructure.messaging.message.UserCreateEmailSenderMessage;
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
public class SendGridPasswordRecoveryNotificationServiceImpl implements PasswordRecoveryNotificationService {

    private final Logger LOG = LoggerFactory.getLogger(SendGridPasswordRecoveryNotificationServiceImpl.class);
    private final AppProperties appProperties;
    private final SendGrid sendGrid;
    private final TemplateEngine templateEngine;

    private final String contentType = "text/html";
    private final String subject = "Recuperação de Senha";

    @Override
    public void execute(PasswordRecoveryNotificationMessage dto) {
        String senderFrom = this.appProperties.getEmailSender().getFrom();
        String friendlyName = this.appProperties.getEmailSender().getFriendlyName();
        Email from = new Email(senderFrom, friendlyName);
        Email to = new Email(dto.getEmail());

        Context context = new Context();
        context.setVariable("fistName", dto.getFistName());
        context.setVariable("lastName", dto.getLastName());
        context.setVariable("tenantDomain", dto.getTenantDomain());
        context.setVariable("redirectUrl", dto.getRedirectUrl());
        context.setVariable("expirationDate", dto.getExpirationDate());
        Content content = new Content(
                this.contentType,
                templateEngine.process(
                        "password_recovery_email_notification_pt-BR.html",
                        context
                )
        );

        Mail mail = new Mail(from, this.subject, to, content);

        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            LOG.info("Sending email to: {}", dto.getEmail());
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
