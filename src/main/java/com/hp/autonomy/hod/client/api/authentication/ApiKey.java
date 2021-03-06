/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication;

import lombok.Data;

/**
 * An API Key which can be used to obtain a token from HP Haven OnDemand
 */
@Data
public class ApiKey {

    /**
     * @return The API key to be used
     */
    private final String apiKey;

    @Override
    public String toString() {
        return apiKey;
    }

}
