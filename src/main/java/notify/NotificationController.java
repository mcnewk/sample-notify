package notify;

import org.codehaus.jackson.JsonNode;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MailSender mailSender;

    @Value("${identity.api.url}")
    private String identityApiUrl;


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
        message.setCc(resolveRecipientEmails(notification.getCarbonCopies()).toArray(new String[0]));
        message.setSubject(notification.getTitle());
        message.setText(notification.getMessage());
        message.setTo(resolveRecipientEmails(notification.getRecipients()).toArray(new String[0]));
        mailSender.send(message);
    }

    private List<String> resolveRecipientEmails(List<String> recipientIds) {
        List<String> recipientEmails = new ArrayList<String>();

        if (!recipientIds.isEmpty()) {

            for (String recipientId : recipientIds) {
                if (recipientId != null && !"".equals(recipientId)) {
                    EmailAddress emailAddress = restTemplate.getForObject(identityApiUrl + "/people/" + recipientId +
                            "/emailAddresses?primary=true", EmailAddress.class);

                    String recipientEmail = emailAddress.getEmailAddress();
                    if (recipientEmail != null && !"".equals(recipientEmail)) {
                        recipientEmails.add(recipientEmail);
                    }
                }
            }
        }

        return recipientEmails;
    }

}
