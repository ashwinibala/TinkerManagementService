package com.solutionmatrix.tinker.service;

import com.solutionmatrix.tinker.model.entity.EmailDetails;
import com.solutionmatrix.tinker.repository.ClientAvailabilityRepository;
import com.solutionmatrix.tinker.repository.ClientRepository;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import static org.junit.jupiter.api.Assertions.*;
import static org.powermock.api.mockito.PowerMockito.*;

class EmailServiceImplTest {
    @InjectMocks
    private EmailServiceImpl emailService;

    @MockBean
    private JavaMailSender javaMailSender;

    @MockBean
    private MimeMessage mimeMessage;

    @MockBean
    private MimeMessageHelper mimeMessageHelper;


    @BeforeEach
    void setUp() {
        javaMailSender = mock(JavaMailSender.class);
        mimeMessage = mock(MimeMessage.class);
        mimeMessageHelper = mock(MimeMessageHelper.class);

        emailService= new EmailServiceImpl( javaMailSender);

    }

    @Test
    void testSendSimpleMail_SuccessTest() {
        // Mock behavior for JavaMailSender
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient("recipient@example.com")
                .subject("Test Subject")
                .msgBody("Test Body")
                .build();

        try {
            // Test the method
            String result = emailService.sendSimpleMail(emailDetails);

            verify(javaMailSender).send(any(SimpleMailMessage.class));

            // Assertion
            assertEquals("Mail Sent Successfully...", result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}