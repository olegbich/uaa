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
package org.cloudfoundry.identity.uaa.authorization.external;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xml.resolver.readers.OASISXMLCatalogReader;
import org.cloudfoundry.identity.uaa.authentication.Origin;
import org.cloudfoundry.identity.uaa.authorization.ExternalGroupMappingAuthorizationManager;
import org.cloudfoundry.identity.uaa.ldap.extension.LdapAuthority;
import org.cloudfoundry.identity.uaa.scim.ScimGroupExternalMember;
import org.cloudfoundry.identity.uaa.scim.ScimGroupExternalMembershipManager;
import org.cloudfoundry.identity.uaa.scim.ScimGroupProvisioning;
import org.cloudfoundry.identity.uaa.zone.IdentityZoneHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LdapGroupMappingAuthorizationManager implements ExternalGroupMappingAuthorizationManager {

    private ScimGroupExternalMembershipManager extMbrMgr;

    private ScimGroupProvisioning scimGroupProvisioning;

    private static final Log logger = LogFactory.getLog(LdapGroupMappingAuthorizationManager.class);

    @Override
    public Set<? extends GrantedAuthority> findScopesFromAuthorities(Set<? extends GrantedAuthority> authorities) {
        Set<GrantedAuthority> result = new HashSet<>();
        for (GrantedAuthority a : authorities) {
            if (a instanceof LdapAuthority) {
                LdapAuthority la = (LdapAuthority)a;
                List<ScimGroupExternalMember> members = extMbrMgr.getExternalGroupMapsByExternalGroup(la.getDn(), Origin.LDAP);
                for (ScimGroupExternalMember member : members) {
                    SimpleGrantedAuthority mapped = new SimpleGrantedAuthority(member.getDisplayName());
                    result.add(mapped);
                    if (logger.isDebugEnabled()) {
                        logger.debug("Ldap Group Mapped[dn="+la.getDn()+" scope:"+mapped.getAuthority());
                    }
                }
            } else {
                result.add(a);
            }
        }
        return result;
    }

    public void setExternalMembershipManager(ScimGroupExternalMembershipManager externalMembershipManager) {
        this.extMbrMgr = externalMembershipManager;
    }

    public void setScimGroupProvisioning(ScimGroupProvisioning scimGroupProvisioning) {
        this.scimGroupProvisioning = scimGroupProvisioning;
    }

}
