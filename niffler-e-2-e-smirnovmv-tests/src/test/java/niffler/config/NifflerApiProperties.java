package niffler.config;

import org.aeonbits.owner.ConfigFactory;

public class NifflerApiProperties {
    private static final NifflerApiConfig CONFIG = ConfigFactory.create(NifflerApiConfig.class);

    public static final String SPEND_API = CONFIG.spendUrl();
    public static final String USER_DATA_API = CONFIG.userDataUrl();
}
