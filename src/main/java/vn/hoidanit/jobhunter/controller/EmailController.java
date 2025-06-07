package vn.hoidanit.jobhunter.controller;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.jobhunter.service.EmailService;
import vn.hoidanit.jobhunter.util.annotation.ApiMessage;

@RestController
@RequestMapping("/api/v1")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;

    }

    @GetMapping("/email")
    @ApiMessage("Send email")
    public String sendEmail() {
        // emailService.sendEmail();
        // this.emailService.sendEmailSync("dangtrieu.lb@gmail.com",
        // "Test from Spring Boot",
        // "<h1>Hello World! This is a test email from Spring Boot application.</h1>",
        // false,
        // true);
        this.emailService.sendEmailFromTemplateSync("dangtrieu.lb@gmail.com",
                "Test from Spring Boot",
                "job");
        return "ok";
    }
}
