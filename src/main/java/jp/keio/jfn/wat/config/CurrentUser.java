package jp.keio.jfn.wat.config;

import jp.keio.jfn.wat.domain.Principals;
import org.springframework.context.annotation.Role;
import org.springframework.security.core.authority.AuthorityUtils;

public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private Principals user;

    public CurrentUser(Principals user, String[] roles) {
        super(user.getUser(), user.getPassword(), AuthorityUtils.createAuthorityList(roles));
        this.user = user;
    }

    public Principals getUser() {
        return user;
    }

    public String getUserName() {
        return user.getUser();
    }

}
