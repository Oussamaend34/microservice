package com.ensah.microservices.notificationService.service;

import com.ensah.microservices.orderService.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class NotificationService {
    private final JavaMailSender mailSender;

    @KafkaListener(
            topics = "order-placed",
            groupId = "notification-service"
    )
    public void listen(OrderPlacedEvent orderPlacedEvent) {
        log.info("Got message from orderPlaced topic {}", orderPlacedEvent);
        if (orderPlacedEvent.getEmail() == null){
            log.error("Email is null");
            return;
        }
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springshop@email.com");
            messageHelper.setTo(orderPlacedEvent.getEmail().toString());
            messageHelper.setSubject(String.format("Your order with OrderNumber %s has been placed is placed successfully!", orderPlacedEvent.getOrderNumber()));
            messageHelper.setText(String.format("""
                    Hi %s, %s
                    
                    Your order with OrderNumber %s has been placed is placed successfully!
                    
                    Best Regards
                    Spring Shop
                    """,orderPlacedEvent.getFirstName(),
                    orderPlacedEvent.getLastName(),
                    orderPlacedEvent.getOrderNumber()
            ));
        };
        try{
            mailSender.send(messagePreparator);
            log.info("Email sent successfully");
        } catch (MailException e) {
            log.error("Error sending email");
            throw new RuntimeException("Exception occurred when sending mail.", e);
        }
    }

}
