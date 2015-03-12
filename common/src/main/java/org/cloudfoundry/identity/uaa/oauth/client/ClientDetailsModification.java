package org.cloudfoundry.identity.uaa.oauth.client;

import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.ClientDetails;

@org.codehaus.jackson.map.annotate.JsonSerialize(include = org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_DEFAULT)
@org.codehaus.jackson.annotate.JsonIgnoreProperties(ignoreUnknown = true)
@com.fasterxml.jackson.databind.annotation.JsonSerialize(include = com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion.NON_DEFAULT)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class ClientDetailsModification extends BaseClientDetails {

    public static final String ADD = "add";
    public static final String UPDATE = "update";
    public static final String UPDATE_SECRET = "update,secret";
    public static final String DELETE = "delete";
    public static final String SECRET = "secret";
    public static final String NONE = "none";

    @org.codehaus.jackson.annotate.JsonProperty("action")
    @com.fasterxml.jackson.annotation.JsonProperty("action")
    private String action = NONE;
    @org.codehaus.jackson.annotate.JsonProperty("approvals_deleted")
    @com.fasterxml.jackson.annotation.JsonProperty("approvals_deleted")
    private boolean approvalsDeleted = false;

    public ClientDetailsModification() {
    }

    public ClientDetailsModification(String clientId, String resourceIds, String scopes, String grantTypes, String authorities, String redirectUris) {
        super(clientId, resourceIds, scopes, grantTypes, authorities, redirectUris);
    }

    public ClientDetailsModification(String clientId, String resourceIds, String scopes, String grantTypes, String authorities) {
        super(clientId, resourceIds, scopes, grantTypes, authorities);
    }

    public ClientDetailsModification(ClientDetails prototype) {
        super(prototype);
        if (prototype instanceof BaseClientDetails) {
            BaseClientDetails baseClientDetails = (BaseClientDetails)prototype;
            this.setAdditionalInformation(baseClientDetails.getAdditionalInformation());
            if (baseClientDetails.getAutoApproveScopes()!=null) {
                this.setAutoApproveScopes(baseClientDetails.getAutoApproveScopes());
            }
        }
        if (prototype instanceof ClientDetailsModification) {
            this.setAction(((ClientDetailsModification) prototype).getAction());
            this.setApprovalsDeleted(((ClientDetailsModification) prototype).isApprovalsDeleted());
        }
    }

    @org.codehaus.jackson.annotate.JsonIgnore
    @com.fasterxml.jackson.annotation.JsonIgnore
    public String getAction() {
        return action;
    }

    @org.codehaus.jackson.annotate.JsonIgnore
    @com.fasterxml.jackson.annotation.JsonIgnore
    public void setAction(String action) {
        if (valid(action)) {
            this.action = action;
        } else {
            throw new IllegalArgumentException("Invalid action:"+action);
        }
    }

    @org.codehaus.jackson.annotate.JsonIgnore
    @com.fasterxml.jackson.annotation.JsonIgnore
    public boolean isApprovalsDeleted() {
        return approvalsDeleted;
    }

    @org.codehaus.jackson.annotate.JsonIgnore
    @com.fasterxml.jackson.annotation.JsonIgnore
    public void setApprovalsDeleted(boolean approvalsDeleted) {
        this.approvalsDeleted = approvalsDeleted;
    }

    @org.codehaus.jackson.annotate.JsonIgnore
    @com.fasterxml.jackson.annotation.JsonIgnore
    private boolean valid(String action) {
        return (ADD.equals(action)
            ||  UPDATE.equals(action)
            || DELETE.equals(action)
            || UPDATE_SECRET.equals(action)
            || SECRET.equals(SECRET));
    }
}
