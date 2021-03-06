package com.hp.autonomy.hod.client.util;

import com.hp.autonomy.hod.client.api.authentication.AuthenticationToken;
import com.hp.autonomy.hod.client.api.authentication.TokenType;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class HmacTest {
    private Hmac hmac;

    @Before
    public void initialise() {
        hmac = new Hmac();
    }

    @Test
    public void generatesToken() {
        final String tokenId = "DF7aRd8VEeSiCdSFZKbA7w";
        final String tokenSecret = "Ba90fFmxdioyouz06xr1fhn6Nxq4nB90jWEQ2UzDQr8";
        final AuthenticationToken token = new AuthenticationToken(123, tokenId, tokenSecret, "UNB:HMAC_SHA1", 456);

        final Map<String, List<String>> queryParameters = new HashMap<>();
        queryParameters.put("allowed_origins", Collections.singletonList("http://localhost:8080"));

        final Map<String, List<Object>> body = new HashMap<>();
        body.put("domain", Collections.<Object>singletonList("IOD-TEST-DOMAIN"));
        body.put("application", Collections.<Object>singletonList("IOD-TEST-APPLICATION"));
        body.put("token_type", Collections.<Object>singletonList(TokenType.simple));

        final Request<String, Object> request = new Request<>(Request.Verb.POST, "/2/authenticate/combined", queryParameters, body);
        final String hmacToken = hmac.generateToken(request, token);
        final String expectedHmacToken = "UNB:HMAC_SHA1:DF7aRd8VEeSiCdSFZKbA7w:-UTk_c6xZSCH2-MMLPiLJg:cPJN8CsxX6chmGif3TLdTLEpMd8";

        assertThat(hmacToken, is(expectedHmacToken));
    }
}
