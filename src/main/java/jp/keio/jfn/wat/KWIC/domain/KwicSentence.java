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

    private String fileName;

    private String corpusName;

    @CreatedDate
    @Column(name="created_at")
    private Timestamp createdDate;

    @LastModifiedDate
    @Column(name="updated_at")
    private Timestamp modifiedDate;

    private int sentencePlace;

    @OneToMany(mappedBy = "kwicSentence", cascade = CascadeType.ALL)
    private Set<Kwics> kwics =  new HashSet<Kwics>();


    protected KwicSentence(){
    }

    public  int getId(){return id;}

    public String getSentence() {return this.sentence;}

    public void setSentence(String sentence) {this.sentence = sentence;}

    public String getFileNname() {return this.fileName;}

    public void setFileNname(String fileNname) {this.fileName = fileNname;}

    public String getCorpusName() {return this.corpusName;}

    public void setCorpusName(String corpusName) {this.corpusName = corpusName;}

    public Timestamp getCreatedDate() {return this.createdDate;}

    public void setCreatedDate(Timestamp createdDate) {this.createdDate = createdDate;}

    public Timestamp getModifiedDate() {return this.modifiedDate;}

    public void setModifiedDate(Timestamp modifiedDate) {this.modifiedDate = modifiedDate;}

    public int getSentencePlace() {return this.sentencePlace;}

    public void setSentencePlace(int sentencePlace) {this.sentencePlace = sentencePlace;}



    public Set<Kwics> getKwics() {
        return kwics;
    }


}
