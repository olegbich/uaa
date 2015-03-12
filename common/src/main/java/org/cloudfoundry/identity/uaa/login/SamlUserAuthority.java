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
package org.cloudfoundry.identity.uaa.login;

import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
public class SamlUserAuthority implements GrantedAuthority {

    private final String authority;

    @org.codehaus.jackson.annotate.JsonCreator
    @com.fasterxml.jackson.annotation.JsonCreator
    public SamlUserAuthority(@org.codehaus.jackson.annotate.JsonProperty("authority")
                                 @com.fasterxml.jackson.annotation.JsonProperty("authority") String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

}
