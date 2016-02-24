package jp.keio.jfn.wat;


import jp.keio.jfn.wat.domain.Principals;
import jp.keio.jfn.wat.repository.PrincipalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserDetailsService implements UserDetailsService {
    private final PrincipalsRepository userService;

    @Autowired
    public CurrentUserDetailsService(PrincipalsRepository userService) {
        this.userService = userService;
    }

    @Override
    public CurrentUser loadUserByUsername(String userName) throws UsernameNotFoundException {
        Principals user = userService.findByUser(userName);
        return new CurrentUser(user);
    }
}
