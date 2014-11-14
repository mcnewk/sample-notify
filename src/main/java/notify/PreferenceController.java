package notify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/preferences")
public class PreferenceController {

    @Autowired
    private PreferenceRepository repository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Preference getPreference(@PathVariable String id) {
        return repository.findOne(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public String create(@RequestBody Preference preference) {
        return repository.save(preference).getId();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public List<Preference> getPreferences() { return repository.findAll(); }


}
