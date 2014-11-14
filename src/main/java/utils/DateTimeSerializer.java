package utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;

import java.io.IOException;

public class DateTimeSerializer extends JsonSerializer<DateTime> {

	@Override
	public void serialize(DateTime date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		String formattedDate = date.toString(Formats.DATETIME_FORMAT);
		jsonGenerator.writeString(formattedDate);
	}
}
