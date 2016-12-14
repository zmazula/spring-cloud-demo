package com.epam.demo.security;

import org.apache.commons.collections.map.MultiValueMap;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Zoltan Mazula
 */
public class ExtendedUserDetailsAdapter implements UserDetails {

  UserDetails userDetails;
  Map<String, String> ldapAttributes = new MultiValueMap();

  public ExtendedUserDetailsAdapter(UserDetails userDetails) {
    this.userDetails = userDetails;
  }

  public void setAttribute(String key, String value) {
    ldapAttributes.put(key, value);
  }

  public void setAttributes(String key, String[] values) {
    Stream.of(values).forEach(s -> ldapAttributes.put(key, s));
  }

  public String getAttribute(String key) {
    return ldapAttributes.get(key);
  }

  public Map<String, String> getAttributes() {
    return ldapAttributes;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return userDetails.getAuthorities();
  }

  @Override
  public String getPassword() {
    return userDetails.getPassword();
  }

  @Override
  public String getUsername() {
    return userDetails.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return userDetails.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return userDetails.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return userDetails.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    return userDetails.isEnabled();
  }
}
