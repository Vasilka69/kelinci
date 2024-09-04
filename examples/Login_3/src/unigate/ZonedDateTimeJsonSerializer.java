package src.unigate;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.ZonedDateTime;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

public class ZonedDateTimeJsonSerializer extends JsonSerializer<ZonedDateTime> {

    @Override
    public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(ISO_INSTANT.format(value));
    }
}
