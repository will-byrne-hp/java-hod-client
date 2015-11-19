/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.authentication.tokeninformation;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationType;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ApplicationInformationTest {
    @Test
    public void buildsResourceIdentifier() {
        final AuthenticationInformation authentication = new AuthenticationInformation(UUID.randomUUID(), AuthenticationType.LEGACY_API_KEY);
        final ApplicationInformation information = new ApplicationInformation("app_name", "domain_name", authentication);

        final ResourceIdentifier identifier = information.getIdentifier();
        assertThat(identifier.getDomain(), is("domain_name"));
        assertThat(identifier.getName(), is("app_name"));
    }
}