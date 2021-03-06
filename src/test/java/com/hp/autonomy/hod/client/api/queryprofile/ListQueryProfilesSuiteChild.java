/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.queryprofile;

import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.api.AbstractQueryProfileIntegrationTest;
import com.hp.autonomy.hod.client.error.HodErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class ListQueryProfilesSuiteChild extends AbstractQueryProfileIntegrationTest {

    // Service under test
    private QueryProfileService queryProfileService;

    public ListQueryProfilesSuiteChild(final Endpoint endpoint) {
        super(endpoint);
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();
        queryProfileService = new QueryProfileServiceImpl(getConfig());
    }

    @Test
    public void testSingleQueryProfile() throws HodErrorException {
        // Setup
        final QueryProfile queryProfile = createQueryProfile("001");
        final QueryProfiles queryProfiles = queryProfileService.listQueryProfiles(getTokenProxy());

        // Test response from getQueryProfiles
        final Set<String> queryProfilesNames = queryProfiles.getNames();
        assertThat(queryProfilesNames, hasSize(1));
        assertThat(queryProfilesNames, hasItem(queryProfile.getName()));
    }

    @Test
    public void testTwoQueryProfiles() throws HodErrorException {
        // Setup
        final QueryProfile qp1 = createQueryProfile("001");
        final QueryProfile qp2 = createQueryProfile("002");
        final QueryProfiles queryProfiles = queryProfileService.listQueryProfiles(getTokenProxy());

        // Test response from getQueryProfiles
        final Set<QueryProfileName> qps = queryProfiles.getQueryProfiles();
        assertThat(qps, hasSize(2));

        // Test response from getNames convenience method
        final Set<String> qpNames = queryProfiles.getNames();
        assertThat(qpNames, hasSize(2));
        assertThat(qpNames, containsInAnyOrder(qp1.getName(), qp2.getName()));
    }

    @Test
    public void testSeveralQueryProfiles() throws HodErrorException {
        // Setup
        final Set<QueryProfile> testProfiles = new HashSet<>();
        final Set<String> testNames = new HashSet<>();

        for (int i = 0; i < 5; i++) {
            final QueryProfile qp = createQueryProfile(String.valueOf(i));
            testProfiles.add(qp);
            testNames.add(qp.getName());
        }

        final QueryProfiles listQPsResponse = queryProfileService.listQueryProfiles(getTokenProxy());

        // Test response from getQueryProfiles
        final Set<QueryProfileName> qps = listQPsResponse.getQueryProfiles();
        assertThat(qps, hasSize(testProfiles.size()));

        // Test response from getNames convenience method
        final Set<String> qpNames = listQPsResponse.getNames();
        assertThat(qpNames, hasSize(testProfiles.size()));
        assertThat(qpNames, containsInAnyOrder(testNames.toArray(new String[testNames.size()])));
    }

    @Test
    public void testNoQueryProfiles() throws HodErrorException {
        final QueryProfiles listQPsResponse = queryProfileService.listQueryProfiles(getTokenProxy());

        // Test response from getQueryProfiles
        final Set<QueryProfileName> qps = listQPsResponse.getQueryProfiles();
        assertThat(qps, is(empty()));

        // Test response from getNames convenience method
        final Set<String> qpNames = listQPsResponse.getNames();
        assertThat(qpNames, is(empty()));
    }
}
