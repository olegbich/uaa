/*******************************************************************************
 *     Cloud Foundry
 *     Copyright (c) [2009-2014] Pivotal Software, Inc. All Rights Reserved.
 *
 *     This product is licensed to you under the Apache License, Version 2.0 (the "License").
 *     You may not use this product except in compliance with the License.
 *
 *     This product includes a number of subcomponents with
 *     separate copyright notices and license terms. Your use of these
 *     subcomponents is subject to the terms and conditions of the
 *     subcomponent's license, as noted in the LICENSE file.
 *******************************************************************************/
package org.cloudfoundry.identity.uaa.login.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.util.HtmlUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MockMvcTestClient {

    //TODO - nullify?
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public MockMvcTestClient(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        objectMapper = new ObjectMapper();
    }

    public String getOAuthAccessToken(String username, String password, String grantType, String scope)
                    throws Exception {
        String basicDigestHeaderValue = "Basic "
                        + new String(Base64.encodeBase64((username + ":" + password).getBytes()));
        MockHttpServletRequestBuilder oauthTokenPost = post("/oauth/token")
                        .header("Authorization", basicDigestHeaderValue)
                        .param("grant_type", grantType)
                        .param("client_id", username)
                        .param("scope", scope);
        MvcResult result = mockMvc.perform(oauthTokenPost).andExpect(status().isOk()).andReturn();
        OAuthToken oauthToken = objectMapper.readValue(result.getResponse().getContentAsByteArray(), OAuthToken.class);
        return oauthToken.accessToken;
    }

    @org.codehaus.jackson.annotate.JsonIgnoreProperties(ignoreUnknown = true)
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
    public static class OAuthToken {
        @org.codehaus.jackson.annotate.JsonProperty("access_token")
        @com.fasterxml.jackson.annotation.JsonProperty("access_token")
        public String accessToken;

        public OAuthToken() {
        }
    }

    public String extractLink(String messageBody) {
        Pattern linkPattern = Pattern.compile("<a href=\"(.*?)\">.*?</a>");
        Matcher matcher = linkPattern.matcher(messageBody);
        matcher.find();
        String encodedLink = matcher.group(1);
        return HtmlUtils.htmlUnescape(encodedLink);
    }
}
