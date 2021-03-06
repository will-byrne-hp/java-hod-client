/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */
package com.hp.autonomy.hod.client.api;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.api.queryprofile.QueryProfile;
import com.hp.autonomy.hod.client.api.queryprofile.QueryProfileRequestBuilder;
import com.hp.autonomy.hod.client.api.queryprofile.QueryProfileService;
import com.hp.autonomy.hod.client.api.queryprofile.QueryProfileServiceImpl;
import com.hp.autonomy.hod.client.error.HodErrorException;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Helper for testing query profile functionality.
 */
@Slf4j
public abstract class AbstractQueryProfileIntegrationTest extends AbstractHodClientIntegrationTest {

    private List<String> createdQueryProfiles;
    // Not under test
    private QueryProfileService queryProfileService;

    public AbstractQueryProfileIntegrationTest(final Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    public void setUp() {
        super.setUp();
        queryProfileService = new QueryProfileServiceImpl(getConfig());
        createdQueryProfiles = new ArrayList<>();
    }

    /**
     * Deletes all created query profiles at the end of the test.
     */
    @After
    public void tearDown() {
        for (final String queryProfileName : createdQueryProfiles) {
            try {
                // Try/catch as QueryProfiles might not have been created
                queryProfileService.deleteQueryProfile(getTokenProxy(), queryProfileName);
            } catch (final HodErrorException e) {
                log.error("Error deleting query profile", e);
            }
        }
    }

    /**
     * Creates a Query Profile in HP Haven OnDemand.  Note that the nameSuffix passed in will not be the same as the
     * name HP Haven OnDemand uses.  See the response object for that.
     * @param nameSuffix  Suffix to append to the query profile name.
     * @return  A QueryProfile with the name and config used.
     * @throws HodErrorException
     */
    protected QueryProfile createQueryProfile(final String nameSuffix) throws HodErrorException {
        final String profileName = "iod_java_client_query_profile_test_" + nameSuffix;
        createdQueryProfiles.add(profileName);

        final boolean promotionsEnabled = true;
        final boolean promotionsIdentified = false;

        final QueryProfileRequestBuilder requestBuilder = new QueryProfileRequestBuilder()
            .setPromotionsEnabled(promotionsEnabled)
            .setPromotionsIdentified(promotionsIdentified)
            .addPromotionCategories("Promotions");

        queryProfileService.createQueryProfile(getTokenProxy(), profileName, getQueryManipulationIndex(), requestBuilder);

        return new QueryProfile.Builder()
            .setName(profileName)
            .setQueryManipulationIndex(getQueryManipulationIndex())
            .setPromotionsEnabled(promotionsEnabled)
            .setPromotionsIdentified(promotionsIdentified)
            .setPromotionCategories(Collections.singletonList("promotions")) // HOD lowercases these
            .build();
    }
}
