package jp.keio.jfn.wat.KWIC.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jfn on 3/22/16.
 */

@Entity
@Table(name = "Kwicword")
public class KwicWord implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String word;

    private String pos;

    @CreatedDate
    @Column(name="created_at")
    private Timestamp createdDate;

    @LastModifiedDate
    @Column(name="updated_at")
    private Timestamp modifiedDate;


    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL)
    private List<Kwics> kwics =  new ArrayList<Kwics>();


    protected KwicWord(){
    }


    public int getId() {return this.id;}

    public void setId(int id) {this.id = id;}

    public String getWord() {return this.word;}

    public void setWord(String word) {this.word = word;}

    public String getPos() {return this.pos;}

    public void setPos(String pos) {this.pos = pos;}

    public Timestamp getCreatedDate() {return this.createdDate;}

    public void setCreatedDate(Timestamp createdDate) {this.createdDate = createdDate;}

    public Timestamp getModifiedDate() {return this.modifiedDate;}

    public void setModifiedDate(Timestamp modifiedDate) {this.modifiedDate = modifiedDate;}



    public List<Kwics> getKwics() {
        return kwics;
    }

}
