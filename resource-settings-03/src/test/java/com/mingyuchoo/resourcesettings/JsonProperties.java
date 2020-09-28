package com.mingyuchoo.resourcesettings;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;

@Component
@TestPropertySource(locations = "/configprops.yml")
public class JsonProperties {
    @Value("${port}")
    private int port;

    @Value("${resend}")
    private boolean resend;

    @Value("${host}")
    private String host;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public boolean isResend() {
        return resend;
    }
}
