/*
 * Copyright 2015 Hewlett-Packard Development Company, L.P.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 */

package com.hp.autonomy.hod.client.api.textindex.query.parametric;

import com.hp.autonomy.hod.client.api.authentication.TokenType;
import com.hp.autonomy.hod.client.api.resource.ResourceIdentifier;
import com.hp.autonomy.hod.client.error.HodErrorException;
import com.hp.autonomy.hod.client.token.TokenProxy;

import java.util.Collection;

/**
 * Service representing the GetParametricValues API
 */
public interface GetParametricValuesService {

    /**
     * Query parametric values for the fieldName using a token proxy
     * provided by a {@link com.hp.autonomy.hod.client.token.TokenProxyService}
     * @param fieldNames A list of field names to return values for
     * @param indexes The indexes to get values from
     * @param params Additional parameters to be sent as part of the request
     * @return A list of field names with their parametric values
     * @throws NullPointerException If a TokenProxyService has not been defined
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    FieldNames getParametricValues(
        Collection<String> fieldNames,
        Collection<ResourceIdentifier> indexes,
        GetParametricValuesRequestBuilder params
    ) throws HodErrorException;

    /**
     * Get parametric values for the fieldName using the given token proxy
     * @param tokenProxy The token to use to authenticate the request
     * @param fieldNames A list of field names to return values for
     * @param indexes The indexes to get values from
     * @param params Additional parameters to be sent as part of the request
     * @return A list of field names with their parametric values
     * @throws com.hp.autonomy.hod.client.api.authentication.HodAuthenticationFailedException If the token associated
     * with the token proxy has expired
     */
    FieldNames getParametricValues(
        TokenProxy<?, TokenType.Simple> tokenProxy,
        Collection<String> fieldNames,
        Collection<ResourceIdentifier> indexes,
        GetParametricValuesRequestBuilder params
    ) throws HodErrorException;

}
