package uz.pdp.jwtemailauditapp.mailTrap;

import lombok.SneakyThrows;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.ValidationException;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    private EmailCfg emailCfg;

    public FeedbackController(EmailCfg emailCfg) {
        this.emailCfg = emailCfg;
    }

    @SneakyThrows
    @PostMapping
    public void sendFeedback(@RequestBody Feedback feedback, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new ValidationException("feedback is not valid");
        }



        JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
        mailSender.setHost(this.emailCfg.getHost());
        mailSender.setPort(this.emailCfg.getPort());
        mailSender.setPassword(this.emailCfg.getPassword());
        mailSender.setUsername(this.emailCfg.getUsername());


        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setFrom(feedback.getEmail());
        mailMessage.setTo("rc@deefback.com");
        mailMessage.setSubject("new feedback from"+feedback.getName());
        mailMessage.setText(feedback.getFeedback());

        mailSender.send(mailMessage);

    }
}
