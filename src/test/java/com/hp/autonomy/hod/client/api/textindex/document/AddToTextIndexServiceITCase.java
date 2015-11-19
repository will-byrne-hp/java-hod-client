/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.document;

import com.hp.autonomy.hod.client.AbstractHodClientIntegrationTest;
import com.hp.autonomy.hod.client.Endpoint;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.util.TestCallback;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@Slf4j
@RunWith(Parameterized.class)
public class AddToTextIndexServiceITCase extends AbstractHodClientIntegrationTest {

    private static final String REFERENCE = "3ac70cc2-606e-486a-97d0-511e762b2183";

    private AddToTextIndexPollingService addToTextIndexService;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        addToTextIndexService = new AddToTextIndexPollingService(getConfig());
    }

    @After
    public void tearDown() {
        addToTextIndexService.destroy();
    }

    public AddToTextIndexServiceITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Test
    public void testAddJsonToTextIndex() throws HodErrorException, InterruptedException {
        final Document document = new Document.Builder()
                .setReference(REFERENCE)
                .setTitle("TEST DOCUMENT")
                .setContent("I heartily endorse this event or product")
                .addField("coolstuff", "This is so cool!")
                .build();

        final AddToTextIndexRequestBuilder params = new AddToTextIndexRequestBuilder()
                .setDuplicateMode(AddToTextIndexRequestBuilder.DuplicateMode.replace);

        final CountDownLatch latch = new CountDownLatch(1);
        final TestCallback<AddToTextIndexResponse> callback = new TestCallback<>(latch);

        addToTextIndexService.addJsonToTextIndex(getTokenProxy(), new Documents<>(document), getPrivateIndex(), params, callback);

        latch.await();

        final AddToTextIndexResponse result = callback.getResult();

        assertThat(result, is(notNullValue()));
        assertThat(result.getIndex(), is(getPrivateIndex().getName()));
        assertThat(result.getReferences(), hasSize(1));

        final AddToTextIndexReference referenceObject = result.getReferences().get(0);

        assertThat(referenceObject.getReference(), is(REFERENCE));
    }

    @Test
    public void testAddFileToTextIndex() throws HodErrorException, InterruptedException {
        final File file = new File("src/test/resources/com/hp/autonomy/hod/client/api/textindex/the-end.txt");
        final String reference = "63edb67f-c930-4b7b-8c33-2cd28e5cc670";

        final Map<String, Object> additionalMetadata = new HashMap<>();
        additionalMetadata.put("reference", reference);
        additionalMetadata.put("title", "The End");

        final AddToTextIndexRequestBuilder params = new AddToTextIndexRequestBuilder()
                .setDuplicateMode(AddToTextIndexRequestBuilder.DuplicateMode.replace)
                .addAdditionalMetadata(additionalMetadata);

        final CountDownLatch latch = new CountDownLatch(1);
        final TestCallback<AddToTextIndexResponse> callback = new TestCallback<>(latch);

        addToTextIndexService.addFileToTextIndex(getTokenProxy(), file, getPrivateIndex(), params, callback);

        latch.await();

        final AddToTextIndexResponse result = callback.getResult();

        assertThat(result, is(notNullValue()));
        assertThat(result.getIndex(), is(getPrivateIndex().getName()));
        assertThat(result.getReferences(), hasSize(1));

        final AddToTextIndexReference referenceObject = result.getReferences().get(0);

        assertThat(referenceObject.getReference(), is(reference));
    }

}
