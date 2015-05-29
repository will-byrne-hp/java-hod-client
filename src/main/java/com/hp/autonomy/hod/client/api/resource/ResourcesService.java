package com.hp.autonomy.hod.client.api.resource;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.QueryMap;

import java.util.Map;

/**
 * Interface representing the list resources API.
 */
public interface ResourcesService {

    String URL = "/2/api/sync/resource/v1";

    /**
     * Query HP Haven OnDemand for the list of resources using a token provided by a {@link retrofit.RequestInterceptor}.
     * @param parameters Request parameters (can be built using {@link ListResourcesRequestBuilder}
     * @return Public and private resources
     */
    @GET(URL)
    Resources list(@QueryMap Map<String, Object> parameters);

    /**
     * Query HP Haven OnDemand for the list of resources with the given token
     * @param token The authentication token
     * @param parameters Request parameters (can be built using {@link ListResourcesRequestBuilder}
     * @return Public and private resources
     */
    @GET(URL)
    Resources list(@Header("token") AuthenticationToken token, @QueryMap Map<String, Object> parameters);

}
