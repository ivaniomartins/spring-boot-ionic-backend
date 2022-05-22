package com.olsystem.spring_curso.services;

import org.springframework.mail.SimpleMailMessage;

import com.olsystem.spring_curso.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

}
