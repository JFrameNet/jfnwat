package jp.keio.jfn.wat.config;


import jp.keio.jfn.wat.domain.Principals;
import jp.keio.jfn.wat.domain.Roles;
import jp.keio.jfn.wat.repository.PrincipalsRepository;
import jp.keio.jfn.wat.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurrentUserDetailsService implements UserDetailsService {
    private final PrincipalsRepository userService;

    @Autowired
    public CurrentUserDetailsService(PrincipalsRepository userService) {
        this.userService = userService;
    }

    @Autowired
    RolesRepository rolesRepository;

    @Override
    public CurrentUser loadUserByUsername(String userName) throws UsernameNotFoundException {
        Principals user = userService.findByUser(userName);
        return new CurrentUser(user, findRoles(userName));
    }

    private String[] findRoles (String name) {
        List<String> roles = new ArrayList<String>();
        for (Roles r : rolesRepository.findAll()) {
            if (r.getUser().equals(name)) {
                String role = r.getRole();
                if (role.equals("read")) {
                    roles.add("ROLE_READ");
                } else if (role.equals("vanguard")) {
                    roles.add("ROLE_VANGUARD");
                } else if (role.equals("annotate")) {
                    roles.add("ROLE_ANNOTATE");
                } else if (role.equals("admin")) {
                    roles.add("ROLE_ADMIN");
                }
            }
        }
        if (roles.isEmpty()) {
            roles.add("ROLE_READ");
        }
        String[] result = new String[roles.size()];
        return roles.toArray(result);
    }
}
