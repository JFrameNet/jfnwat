package jp.keio.jfn.wat.KWIC.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jfn on 3/22/16.
 */

@Entity
@Table(name ="KwicSentence")
public class KwicSentence implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="content")
    private String sentence;

    @Column
    private String fileName;

    @Column
    private String corpusName;

    @CreatedDate
    @Column(name="created_at")
    private Timestamp createdDate;

    @LastModifiedDate
    @Column(name="updated_at")
    private Timestamp modifiedDate;

    @Column
    private int sentencePlace;

    @OneToMany(mappedBy = "kwicSentence", cascade = CascadeType.ALL)
    private Set<Kwics> kwics =  new HashSet<Kwics>();


    @Transient
    public String beforeSearch ="";
    @Transient
    public String search ="";
    @Transient
    public String AfterSearch ="";


    protected KwicSentence(){
    }

    public  int getId(){return id;}

    public String getSentence() {return this.sentence;}

    public String getFileName() {return this.fileName;}

    public String getCorpusName() {return this.corpusName;}

    public Timestamp getCreatedDate() {return this.createdDate;}

    public void setCreatedDate(Timestamp createdDate) {this.createdDate = createdDate;}

    public Timestamp getModifiedDate() {return this.modifiedDate;}

    public void setModifiedDate(Timestamp modifiedDate) {this.modifiedDate = modifiedDate;}

    public int getSentencePlace() {return this.sentencePlace;}


    public String getBeforeSearch() {
        return beforeSearch;
    }

    public String getSearch() {
        return search;
    }

    public String getAfterSearch() {
        return AfterSearch;
    }

    public Set<Kwics> getKwics() {
        return kwics;
    }


}
