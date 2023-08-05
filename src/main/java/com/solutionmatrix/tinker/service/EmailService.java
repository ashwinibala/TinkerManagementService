package com.solutionmatrix.tinker.service;

import com.solutionmatrix.tinker.model.entity.EmailDetails;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);

}
