package niffler.jupiter.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import niffler.model.UserJson;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class UserDataConverter implements ArgumentConverter {
    private ClassLoader cl = this.getClass().getClassLoader();
    private ObjectMapper om = new ObjectMapper();

    @Override
    public Object convert(Object source, ParameterContext context) throws ArgumentConversionException {
        if (!(source instanceof String)) {
            throw new ArgumentConversionException("works only with strings");
        }
        try (InputStream is = cl.getResourceAsStream((String) source);
             InputStreamReader isr = new InputStreamReader(is);
        ) {
            return om.readValue(isr, UserJson.class);
        } catch (IOException e) {
            throw new ArgumentConversionException("Failed to convert", e);
        }
    }
}
