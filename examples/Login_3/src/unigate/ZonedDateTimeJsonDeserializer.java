package src.unigate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.ZonedDateTime;

public class ZonedDateTimeJsonDeserializer extends JsonDeserializer<ZonedDateTime> {

    @Override
    public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        try {
            return ZonedDateTime.parse(jsonParser.getValueAsString());
        } catch (IOException ex) {
            return null;
        }
    }
}
