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
package org.cloudfoundry.identity.uaa.codestore;

import java.sql.Timestamp;


@org.codehaus.jackson.map.annotate.JsonSerialize
@org.codehaus.jackson.map.annotate.JsonDeserialize
@com.fasterxml.jackson.databind.annotation.JsonSerialize
@com.fasterxml.jackson.databind.annotation.JsonDeserialize
public class ExpiringCode {

    private String code;

    private Timestamp expiresAt;

    private String data;

    public ExpiringCode() {
    }

    public ExpiringCode(String code, Timestamp expiresAt, String data) {
        this.code = code;
        this.expiresAt = expiresAt;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Timestamp getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Timestamp expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @org.codehaus.jackson.annotate.JsonIgnore
    @com.fasterxml.jackson.annotation.JsonIgnore
    public boolean isExpired() {
        if (expiresAt == null)
            return false;
        return expiresAt.getTime() < System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ExpiringCode))
            return false;

        ExpiringCode that = (ExpiringCode) o;

        if (code != null ? !code.equals(that.code) : that.code != null)
            return false;
        if (data != null ? !data.equals(that.data) : that.data != null)
            return false;
        if (expiresAt != null ? !expiresAt.equals(that.expiresAt) : that.expiresAt != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ExpiringCode [code=" + code + ", expiresAt=" + expiresAt + ", data=" + trimToLength(data, 1024) + "]";
    }

    private String trimToLength(String s, int length) {
        int min = Math.min(s.length(), length);
        if (min == s.length()) {
            return s;
        } else {
            return s.substring(0, min);
        }
    }

}
