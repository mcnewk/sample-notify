package notify;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import utils.DateTimeDeserializer;
import utils.DateTimeSerializer;

import java.util.List;

@Document
public class Notification {

    @Id
    private String id;
    private String title;
    private String message;
    private List<String> recipients;
    @JsonProperty(value = "ccs")
    private List<String> carbonCopies;
    private String from;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime created;
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime delivered;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    public List<String> getCarbonCopies() {
        return carbonCopies;
    }

    public void setCarbonCopies(List<String> carbonCopies) {
        this.carbonCopies = carbonCopies;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public DateTime getCreated() {
        return created;
    }

    public void setCreated(DateTime created) {
        this.created = created;
    }

    public DateTime getDelivered() {
        return delivered;
    }

    public void setDelivered(DateTime delivered) {
        this.delivered = delivered;
    }
}
