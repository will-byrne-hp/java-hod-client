/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication.tokeninformation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import lombok.Data;

import java.io.Serializable;

/**
 * Information about the application component of an authentication token.
 */
@Data
public class ApplicationInformation implements Serializable {
    private static final long serialVersionUID = 5476059811637118050L;

    /**
     * @return The name of the application
     */
    private final String name;

    /**
     * @return The domain of the application
     */
    private final String domain;

    /**
     * @return Information about the mechanism used to authenticate the application
     */
    private final AuthenticationInformation authentication;

    public ApplicationInformation(
        @JsonProperty("name") final String name,
        @JsonProperty("domain") final String domain,
        @JsonProperty("auth") final AuthenticationInformation authentication
    ) {
        this.name = name;
        this.domain = domain;
        this.authentication = authentication;
    }

    public ResourceIdentifier getIdentifier() {
        return new ResourceIdentifier(domain, name);
    }
}
