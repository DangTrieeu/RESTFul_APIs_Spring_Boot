package vn.hoidanit.jobhunter.controller;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import vn.hoidanit.jobhunter.service.EmailService;
import vn.hoidanit.jobhunter.service.SubscriberService;
import vn.hoidanit.jobhunter.util.annotation.ApiMessage;

@RestController
@RequestMapping("/api/v1")
public class EmailController {
    private final EmailService emailService;
    private final SubscriberService subscriberService;

    public EmailController(EmailService emailService, SubscriberService subscriberService) {
        this.emailService = emailService;
        this.subscriberService = subscriberService;

    }

    @GetMapping("/email")
    @ApiMessage("Send email")
    @Scheduled(cron = "0/30 * * * * ?")
    @Transactional
    public String sendEmail() {
        // emailService.sendEmail();
        // this.emailService.sendEmailSync("dangtrieu.lb@gmail.com",
        // "Test from Spring Boot",
        // "<h1>Hello World! This is a test email from Spring Boot application.</h1>",
        // false,
        // true);
        System.out.println("Send email");
        this.subscriberService.sendSubscribersEmailJobs();
        return "ok";
    }
}
