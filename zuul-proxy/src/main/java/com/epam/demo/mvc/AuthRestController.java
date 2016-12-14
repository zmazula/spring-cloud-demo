package com.epam.demo.mvc;

import com.epam.demo.security.ExtendedUserDetailsAdapter;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Map;

/**
 * Created by zmazula on 08/11/16.
 */
@RestController
public class AuthRestController {

  @RequestMapping("/attributes")
  public Map getAttributes(Principal principal) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return ((ExtendedUserDetailsAdapter)auth.getPrincipal()).getAttributes();
  }

  @RequestMapping(value="/logout", method = RequestMethod.GET)
  public RedirectView logoutPage (HttpServletRequest request, HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    RedirectView redirectView = new RedirectView();
    redirectView.setUrl("/login.jsp");
    return redirectView;
  }
}
