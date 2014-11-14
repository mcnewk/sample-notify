package notify;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository repository;

    @Autowired
    private MailSender mailSender;


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Notification getNotification(@PathVariable String id) {
        return repository.findOne(id);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public List<Notification> getNotifications() { return repository.findAll(); }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public String send(@RequestBody Notification notification) {
        notification.setCreated(DateTime.now());
        Notification saved = repository.save(notification);
        sendEmail(notification);
        return saved.getId();
    }

    @Async
    private void sendEmail(Notification notification) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(notification.getFrom());
        message.setCc(notification.getCarbonCopies().toArray(new String[0]));
        message.setSubject(notification.getTitle());
        message.setText(notification.getMessage());
        message.setTo(notification.getRecipients().toArray(new String[0]));
        mailSender.send(message);
    }

}
