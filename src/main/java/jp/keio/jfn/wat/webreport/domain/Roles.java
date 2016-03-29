package jp.keio.jfn.wat.webreport.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by jfn on 3/9/16.
 */
@Entity
@NamedQuery(name="Roles.findAll", query="SELECT r FROM Roles r")
public class Roles implements Serializable {

    @Id
    @Column(name = "RoleID")
    private Integer roleID;

    @Column(name = "User")
    private String user;

    @Column(name = "Role")
    private String role;


    public String getRole() {
        return role;
    }

    public String getUser() {
        return user;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }
}
