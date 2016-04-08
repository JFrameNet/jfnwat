package jp.keio.jfn.wat.KWIC.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by jfn on 3/22/16.
 */

@Entity
@Table(name = "Kwics")
public class Kwics implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="word_id")
    private KwicWord word;

    @ManyToOne
    @JoinColumn(name="sentence_id")
    private KwicSentence kwicSentence;



//    @Column(name = "sentence_id", insertable = false, updatable = false)
//    private int sentenceID;


    private int place;

    @CreatedDate
    @Column(name="created_at", insertable = true, updatable = false)
    private Timestamp createdDate;

    @LastModifiedDate
    @Column(name="updated_at", insertable = false, updatable = true)
    private Timestamp modifiedDate;

    protected Kwics() {
    }


    public int getId() {return this.id;}

    public void setId(int id) {this.id = id;}

    public KwicWord getWord() {return this.word;}

    public void setWord(KwicWord word) {this.word = word;}

    public KwicSentence getKwicSentence() {return this.kwicSentence;}

    public void setKwicSentence(KwicSentence kwicSentence) {this.kwicSentence = kwicSentence;}

    public int getSentenceID() {
  //      return sentenceID;
        return kwicSentence.getId();
    }

    public int getPlace() {return this.place;}

    public void setPlace(int place) {this.place = place;}

    public Timestamp getCreatedDate() {return this.createdDate;}

    public void setCreatedDate(Timestamp createdDate) {this.createdDate = createdDate;}

    public Timestamp getModifiedDate() {return this.modifiedDate;}

    public void setModifiedDate(Timestamp modifiedDate) {this.modifiedDate = modifiedDate;}
}
