package com.cdac.keycloak;

import lombok.Builder;
import lombok.Data;

/**
 * @author Ramu Parupalli 
 * 
 * @version 0.0.1
 * @since 0.0.1
 */
@Data
@Builder
public class KeycloakAdminClientConfig {

    private String serverUrl;
    private String realm;
    private String clientId;
    private String clientSecret;
}
