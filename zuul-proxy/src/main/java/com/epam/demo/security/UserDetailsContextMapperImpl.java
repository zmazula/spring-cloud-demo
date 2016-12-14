package com.epam.demo.security;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;

import java.util.Collection;

/**
 * @author Zoltan Mazula
 */
public class UserDetailsContextMapperImpl extends LdapUserDetailsMapper {

  private static final String LDAP_TITLE = "title";
  private static final String LDAP_MAIL = "mail";
  private static final String LDAP_DISPLAYNAME = "displayName";
  private static final String LDAP_MEMBEROF = "memberOf";
  private static final String LDAP_PHYSICALDELIVERYOFFICENAME = "physicalDeliveryOfficeName";

  @Override
  public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {

    UserDetails userDetails= super.mapUserFromContext(ctx,username,authorities);
    ExtendedUserDetailsAdapter userDetailsAdapter = new ExtendedUserDetailsAdapter(userDetails);
    userDetailsAdapter.setAttribute(LDAP_TITLE, ctx.getStringAttribute(LDAP_TITLE));
    userDetailsAdapter.setAttribute(LDAP_MAIL, ctx.getStringAttribute(LDAP_MAIL));
    userDetailsAdapter.setAttribute(LDAP_DISPLAYNAME, ctx.getStringAttribute(LDAP_DISPLAYNAME));
    userDetailsAdapter.setAttributes(LDAP_MEMBEROF, ctx.getStringAttributes(LDAP_MEMBEROF));
    userDetailsAdapter.setAttribute(LDAP_PHYSICALDELIVERYOFFICENAME, ctx.getStringAttribute(LDAP_PHYSICALDELIVERYOFFICENAME));

    return userDetailsAdapter;
  }

}
