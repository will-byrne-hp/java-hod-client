/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client;

/**
 * Enum type representing the possible endpoints for HP Haven OnDemand
 */
public enum Endpoint {
    DEVELOPMENT("https://api.dev.idolondemand.com", System.getProperty("hp.dev.hod.apiKey")),
    STAGING("https://api.staging.idolondemand.com", System.getProperty("hp.staging.hod.apiKey")),
    PRODUCTION("https://api.idolondemand.com", System.getProperty("hp.hod.apiKey"));

    private final String url;
    private final String apiKey;

    Endpoint(final String url, final String apiKey) {
        this.url = url;
        this.apiKey = apiKey;
    }

    public String getUrl() {
        return url;
    }

    public String getApiKey() {
        return apiKey;
    }

    @Override
    public String toString() {
        return url;
    }
}
