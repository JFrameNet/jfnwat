package jp.keio.jfn.wat;

import jp.keio.jfn.wat.domain.Principals;
import org.springframework.context.annotation.Role;
import org.springframework.security.core.authority.AuthorityUtils;

public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private Principals user;

    public CurrentUser(Principals user) {
        super(user.getUser(), user.getPassword(), AuthorityUtils.createAuthorityList("USER"));
        this.user = user;
    }

    public Principals getUser() {
        return user;
    }

    public String getUserName() {
        return user.getUser();
    }

    public Role getRole() {
        return null;
    }

}
