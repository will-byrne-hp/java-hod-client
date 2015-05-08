/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindexing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.autonomy.hod.client.job.Status;
import com.hp.autonomy.hod.client.error.HodError;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.job.Action;
import com.hp.autonomy.hod.client.job.JobId;
import com.hp.autonomy.hod.client.job.JobStatus;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

import java.util.List;

public interface DeleteFromTextIndexService {

    String URL = "/1/api/async/deletefromtextindex/v1";

    /**
     * Deletes the documents with the given references using an API key provided by a {@link retrofit.RequestInterceptor}
     * @param index The index to delete from
     * @param references The references of the documents to delete
     * @return The job ID of the request
     * @throws HodErrorException If an error occurs with the request
     */
    @GET(URL)
    JobId deleteReferencesFromTextIndex(
            @Query("index") final String index,
            @Query("index_reference") final List<String> references
    ) throws HodErrorException;

    /**
     * Deletes the documents with the given references using the given API key
     * @param apiKey The API key to use to authenticate the request
     * @param index The index to delete from
     * @param references The references of the documents to delete
     * @return The job ID of the request
     * @throws HodErrorException If an error occurs with the request
     */
    @GET(URL)
    JobId deleteReferencesFromTextIndex(
            @Query("apiKey") final String apiKey,
            @Query("index") final String index,
            @Query("index_reference") final List<String> references
    ) throws HodErrorException;

    /**
     * Deletes all the documents from the given text index using an API key provided by a {@link retrofit.RequestInterceptor}
     * @param index The index to delete from
     * @return The job ID of the request
     * @throws HodErrorException If an error occurs with the request
     */
    @GET(URL + "?delete_all_documents=true")
    JobId deleteAllDocumentsFromTextIndex(
            @Query("index") final String index
    ) throws HodErrorException;

    /**
     * Deletes all the documents from the given text index using the given API key
     * @param apiKey The API key to use to authenticate the request
     * @param index The index to delete from
     * @return The job ID of the request
     * @throws HodErrorException If an error occurs with the request
     */
    @GET(URL + "?delete_all_documents=true")
    JobId deleteAllDocumentsFromTextIndex(
            @Query("apiKey") final String apiKey,
            @Query("index") final String index
    ) throws HodErrorException;

    /**
     * Get the status of an DeleteFromTextIndex job
     * @param apiKey The API key to use to authenticate the request
     * @param jobId The id of the job
     * @return An object containing the status of the job along with the result if the job has finished
     * @throws HodErrorException If an error occurred retrieving the status
     */
    @GET("/1/job/status/{jobId}")
    DeleteFromTextIndexJobStatus getJobStatus(
            @Query("apiKey") String apiKey,
            @Path("jobId") JobId jobId
    ) throws HodErrorException;

    /**
     * Get the status of an DeleteFromTextIndex job  using an API key provided by a {@link retrofit.RequestInterceptor}
     * @param jobId The id of the job
     * @return An object containing the status of the job along with the result if the job has finished
     * @throws HodErrorException If an error occurred retrieving the status
     */
    @GET("/1/job/status/{jobId}")
    DeleteFromTextIndexJobStatus getJobStatus(
            @Path("jobId") JobId jobId
    ) throws HodErrorException;

    /**
     * Get the result of an DeleteFromTextIndex job using an API key provided by a {@link retrofit.RequestInterceptor}
     * @param jobId The id of the job
     * @return An object containing the result of the job
     * @throws HodErrorException If an error occurred retrieving the result
     */
    @GET("/1/job/result/{jobId}")
    DeleteFromTextIndexJobStatus getJobResult(
            @Path("jobId") JobId jobId
    ) throws HodErrorException;

    /**
     * Get the result of an DeleteFromTextIndex job using the given API key
     * @param apiKey The API key to use to authenticate the request
     * @param jobId The id of the job
     * @return An object containing the result of the job
     * @throws HodErrorException If an error occurred retrieving the result
     */
    @GET("/1/job/result/{jobId}")
    DeleteFromTextIndexJobStatus getJobResult(
            @Query("apiKey") String apiKey,
            @Path("jobId") JobId jobId
    ) throws HodErrorException;

    /**
     * {@link JobStatus} subtype which encodes the generic type for JSON parsing
     */
    class DeleteFromTextIndexJobStatus extends JobStatus<DeleteFromTextIndexResponse> {

        public DeleteFromTextIndexJobStatus(
                @JsonProperty("jobID") final String jobId,
                @JsonProperty("status") final Status status,
                @JsonProperty("actions") final List<DeleteFromTextIndexJobStatusAction> actions
        ) {
            super(jobId, status, actions);
        }
    }

    /**
     * {@link Action} subtype which encodes the generic type for JSON parsing
     */
    class DeleteFromTextIndexJobStatusAction extends Action<DeleteFromTextIndexResponse> {
        // need these @JsonProperty or it doesn't work
        public DeleteFromTextIndexJobStatusAction(
                @JsonProperty("action") final String action,
                @JsonProperty("status") final Status status,
                @JsonProperty("errors") final List<HodError> errors,
                @JsonProperty("result") final DeleteFromTextIndexResponse result,
                @JsonProperty("version") final String version
        ) {
            super(action, status, errors, result, version);
        }
    }

}
