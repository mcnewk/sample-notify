package notify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository repository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Notification getNotification(@PathVariable String id) {
        return repository.findOne(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public String create(@RequestBody Notification notification) {
        return repository.save(notification).getId();
    }

}
