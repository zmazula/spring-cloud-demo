package com.epam.demo.mvc;

import com.epam.demo.security.WebSecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

/**
 * Created by zmazula on 08/11/16.
 */
@RestController
public class DummyController {

  @RequestMapping("/attributes")
  public Map getAttributes(Principal principal) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return ((WebSecurityConfig.ExtendedUserDetailsAdapter)auth.getPrincipal()).getAttributes();
  }


//  @SuppressWarnings("unchecked")
//  @RequestMapping(value = "/attributes", method = RequestMethod.GET)
//  public String attributes(ModelMap model, Principal principal) {
//
//    class UserAttributesMapper implements AttributesMapper {
//
//      @Override
//      public Object mapFromAttributes(Attributes attributes)
//              throws NamingException {
//        Map<String, String> map = new HashMap<String, String>();
//        String fullname = (String) attributes.get("displayName").get();
//        String email = (String) attributes.get("mail").get();
//        String title = (String) attributes.get("title").get();
//
//        map.put("fullname", fullname);
//        map.put("email", email);
//        map.put("title", title);
//        return map;
//      }
//    }
//
//    Map<String, String> results = new HashMap<String, String>();
//    String objectClass = "sAMAccountName=" + principal.getName();
//    System.out.println(principal.getName());
//
//    LinkedList<Map<String, String>> list = (LinkedList<Map<String, String>>) template
//            .search("OU=All Users,DC=SEA,DC=CORP,DC=EXPECN,DC=com", objectClass,
//                    new UserAttributesMapper());
//    results = list.get(0);
//    model.addAttribute("userinfo", results.toString());
//    return results.toString();
//  }

}
