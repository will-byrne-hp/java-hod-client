/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.document;

import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.job.HodJobCallback;
import com.hp.autonomy.hod.client.token.TokenProxy;

import java.util.List;

/**
 * Service representing the DeleteFromTextIndex API
 */
public interface DeleteFromTextIndexService {
    /**
     * Deletes the documents with the given references using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param index The index to delete from
     * @param references The references of the documents to delete
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs with the request
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    void deleteReferencesFromTextIndex(
        ResourceIdentifier index,
        List<String> references,
        HodJobCallback<DeleteFromTextIndexResponse> callback
    ) throws HodErrorException;

    /**
     * Deletes the documents with the given references using the given token proxy
     * @param tokenProxy The token proxy to use
     * @param index The index to delete from
     * @param references The references of the documents to delete
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs with the request
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    void deleteReferencesFromTextIndex(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        ResourceIdentifier index,
        List<String> references,
        HodJobCallback<DeleteFromTextIndexResponse> callback
    ) throws HodErrorException;

    /**
     * Deletes all the documents from the given text index using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param index The index to delete from
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs with the request
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    void deleteAllDocumentsFromTextIndex(
        ResourceIdentifier index,
        HodJobCallback<DeleteFromTextIndexResponse> callback
    ) throws HodErrorException;

    /**
     * Deletes all the documents from the given text index using the given token proxy
     * @param tokenProxy The token to use to authenticate the request
     * @param index The index to delete from
     * @param callback Callback that will be called with the response
     * @throws HodErrorException If an error occurs with the request
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    void deleteAllDocumentsFromTextIndex(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        ResourceIdentifier index,
        HodJobCallback<DeleteFromTextIndexResponse> callback
    ) throws HodErrorException;
}
