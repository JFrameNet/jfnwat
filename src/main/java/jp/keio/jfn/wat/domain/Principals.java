package jp.keio.jfn.wat.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
/**
 * Created by jfn on 2/24/16.
 */

@Entity
@NamedQuery(name="Principals.findAll", query="SELECT p FROM Principals p")
public class Principals implements Serializable {

    private Integer anonID;

    @Id
    private String user;

    private String password;

    public Integer getAnonID() {
        return anonID;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void setAnonID(Integer anonID) {
        this.anonID = anonID;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
