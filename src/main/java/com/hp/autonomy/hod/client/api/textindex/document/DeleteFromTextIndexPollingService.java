/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.document;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.job.AbstractPollingService;
import com.hp.autonomy.hod.client.job.HodJobCallback;
import com.hp.autonomy.hod.client.job.JobId;
import com.hp.autonomy.hod.client.job.JobStatus;
import com.hp.autonomy.hod.client.job.PollingJobStatusRunnable;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Service for managing jobs ids from the DeleteFromTextIndex API
 * <p/>
 * The destroy method should be called when the service is no longer needed.
 */
public class DeleteFromTextIndexPollingService extends AbstractPollingService {

    private final DeleteFromTextIndexBackend deleteFromTextIndexBackend;

    /**
     * Creates a new DeleteFromTextIndexPollingService
     * @param deleteFromTextIndexBackend The underlying service which will communicate with HP Haven OnDemand
     */
    public DeleteFromTextIndexPollingService(final DeleteFromTextIndexBackend deleteFromTextIndexBackend) {
        super();

        this.deleteFromTextIndexBackend = deleteFromTextIndexBackend;
    }

    /**
     * Creates a new DeleteFromTextIndexPollingService
     * @param deleteFromTextIndexBackend The underlying service which will communicate with HP Haven OnDemand
     * @param executorService The executor service to use while polling for status updates
     */
    public DeleteFromTextIndexPollingService(final DeleteFromTextIndexBackend deleteFromTextIndexBackend, final ScheduledExecutorService executorService) {
        super(executorService);

        this.deleteFromTextIndexBackend = deleteFromTextIndexBackend;
    }

    /**
     * Deletes the documents with the given references using an API key provided by a {@link retrofit.RequestInterceptor}
     * @param index The index to delete from
     * @param references The references of the documents to delete
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs with the request
     */
    public void deleteReferencesFromTextIndex(
            final String index,
            final List<String> references,
            final HodJobCallback<DeleteFromTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = deleteFromTextIndexBackend.deleteReferencesFromTextIndex(index, references);

        getExecutorService().submit(new DeleteFromTextIndexPollingStatusRunnable(jobId, callback));
    }

    /**
     * Deletes the documents with the given references
     * @param token The token to use to authenticate the request
     * @param index The index to delete from
     * @param references The references of the documents to delete
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs with the request
     */
    public void deleteReferencesFromTextIndex(
            final AuthenticationToken token,
            final String index,
            final List<String> references,
            final HodJobCallback<DeleteFromTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = deleteFromTextIndexBackend.deleteReferencesFromTextIndex(token, index, references);

        getExecutorService().submit(new DeleteFromTextIndexPollingStatusRunnable(token, jobId, callback));
    }

    /**
     * Deletes all the documents from the given text index using an API key provided by a {@link retrofit.RequestInterceptor}
     * @param index The index to delete from
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs with the request
     */
    public void deleteAllDocumentsFromTextIndex(
            final String index,
            final HodJobCallback<DeleteFromTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = deleteFromTextIndexBackend.deleteAllDocumentsFromTextIndex(index);

        getExecutorService().submit(new DeleteFromTextIndexPollingStatusRunnable(jobId, callback));
    }

    /**
     * Deletes all the documents from the given text index using the given API key
     * @param token The token to use to authenticate the request
     * @param index The index to delete from
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs with the request
     */
    public void deleteAllDocumentsFromTextIndex(
            final AuthenticationToken token,
            final String index,
            final HodJobCallback<DeleteFromTextIndexResponse> callback
    ) throws HodErrorException {
        final JobId jobId = deleteFromTextIndexBackend.deleteAllDocumentsFromTextIndex(token, index);

        getExecutorService().submit(new DeleteFromTextIndexPollingStatusRunnable(token, jobId, callback));
    }

    private class DeleteFromTextIndexPollingStatusRunnable extends PollingJobStatusRunnable<DeleteFromTextIndexResponse> {

        public DeleteFromTextIndexPollingStatusRunnable(final JobId jobId, final HodJobCallback<DeleteFromTextIndexResponse> callback) {
            super(jobId, callback, getExecutorService());
        }

        public DeleteFromTextIndexPollingStatusRunnable(final AuthenticationToken token, final JobId jobId, final HodJobCallback<DeleteFromTextIndexResponse> callback) {
            super(token, jobId, callback, getExecutorService());
        }

        @Override
        public JobStatus<DeleteFromTextIndexResponse> getJobStatus(final JobId jobId) throws HodErrorException {
            return deleteFromTextIndexBackend.getJobStatus(jobId);
        }

        @Override
        public JobStatus<DeleteFromTextIndexResponse> getJobStatus(final AuthenticationToken token, final JobId jobId) throws HodErrorException {
            return deleteFromTextIndexBackend.getJobStatus(token, jobId);
        }
    }

}
