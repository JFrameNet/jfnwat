package jp.keio.jfn.wat.webreport.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the LexemeEntry database table.
 *
 */
@Entity
@NamedQuery(name="LexemeEntry.findAll", query="SELECT l FROM LexemeEntry l")
public class LexemeEntry implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private byte breakBefore;

	private String createdBy;

	private Timestamp createdDate;

	private byte headword;

	private byte lexemeOrder;

	private Timestamp modifiedDate;

	//bi-directional many-to-one association to Lemma
	@ManyToOne
	@JoinColumn(name="Lemma_Ref")
	private Lemma lemma;

	//bi-directional many-to-one association to Lexeme
	@ManyToOne
	@JoinColumn(name="Lexeme_Ref")
	private Lexeme lexeme;

	public LexemeEntry() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getBreakBefore() {
		return this.breakBefore;
	}

	public void setBreakBefore(byte breakBefore) {
		this.breakBefore = breakBefore;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public byte getHeadword() {
		return this.headword;
	}

	public void setHeadword(byte headword) {
		this.headword = headword;
	}

	public byte getLexemeOrder() {
		return this.lexemeOrder;
	}

	public void setLexemeOrder(byte lexemeOrder) {
		this.lexemeOrder = lexemeOrder;
	}

	public Timestamp getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Lemma getLemma() {
		return this.lemma;
	}

	public void setLemma(Lemma lemma) {
		this.lemma = lemma;
	}

	public Lexeme getLexeme() {
		return this.lexeme;
	}

	public void setLexeme(Lexeme lexeme) {
		this.lexeme = lexeme;
	}

}