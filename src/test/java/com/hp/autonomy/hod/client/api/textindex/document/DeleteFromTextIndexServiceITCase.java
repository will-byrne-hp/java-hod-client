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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collections;
import java.util.concurrent.CountDownLatch;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;


@Slf4j
@RunWith(Parameterized.class)
public class DeleteFromTextIndexServiceITCase extends AbstractHodClientIntegrationTest {

    private DeleteFromTextIndexService deleteFromTextIndexService;
    private AddToTextIndexService addToTextIndexService;

    @Override
    @Before
    public void setUp() {
        super.setUp();

        deleteFromTextIndexService = new DeleteFromTextIndexPollingService(getConfig());
        addToTextIndexService = new AddToTextIndexPollingService(getConfig());
    }

    public DeleteFromTextIndexServiceITCase(final Endpoint endpoint) {
        super(endpoint);
    }

    @Test
    public void testDocumentDeletion() throws HodErrorException, InterruptedException {
        final String reference = "f3c2b5aa-820e-4553-97ce-0035e5694b0b";

        final Document document = new Document.Builder()
                .setReference(reference)
                .setContent("This document will be deleted")
                .setTitle("Title is required, for some reason")
                .build();

        final AddToTextIndexRequestBuilder params = new AddToTextIndexRequestBuilder()
                .setDuplicateMode(AddToTextIndexRequestBuilder.DuplicateMode.replace);

        final CountDownLatch latch = new CountDownLatch(1);

        final DeleteTestCallback callback = new DeleteTestCallback(latch, reference);
        addToTextIndexService.addJsonToTextIndex(getTokenProxy(), new Documents<>(document), getPrivateIndex(), params, callback);

        latch.await();

        final DeleteFromTextIndexResponse innerResult = callback.getInnerResult();
        assertThat(innerResult, is(notNullValue()));
        assertThat(innerResult.getIndex(), is(getPrivateIndex().getName()));
        assertThat(innerResult.getDocumentsDeleted(), is(1));
    }

    private class DeleteTestCallback extends TestCallback<AddToTextIndexResponse> {

        private final String reference;
        private final TestCallback<DeleteFromTextIndexResponse> callback;

        public DeleteTestCallback(final CountDownLatch latch, final String reference) {
            super(latch);
            this.reference = reference;
            callback = new TestCallback<>(latch);
        }

        public DeleteFromTextIndexResponse getInnerResult() {
            return callback.getResult();
        }

        @Override
        public void success(final AddToTextIndexResponse result) {
            log.debug("Document indexed successfully");

            try {
                deleteFromTextIndexService.deleteReferencesFromTextIndex(getTokenProxy(), getPrivateIndex(), Collections.singletonList(reference), callback);
            } catch (final HodErrorException e) {
                log.error("Error deleting document", e);

                latch.countDown();
            }
        }
    }

}
