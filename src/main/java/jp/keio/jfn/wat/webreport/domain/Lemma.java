package jp.keio.jfn.wat.webreport.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the Lemma database table.
 *
 */
@Entity
@NamedQuery(name="Lemma.findAll", query="SELECT l FROM Lemma l")
public class Lemma implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String createdBy;

	private Timestamp createdDate;

	private Timestamp modifiedDate;

	//bi-directional many-to-one association to PartOfSpch
	@ManyToOne
	@JoinColumn(name="PartOfSpch_Ref")
	private PartOfSpch partOfSpch;

	//bi-directional many-to-one association to LexUnit
	@OneToMany(mappedBy="lemma")
	private List<LexUnit> lexUnits;

	//bi-directional many-to-one association to LexemeEntry
	@OneToMany(mappedBy="lemma")
	private List<LexemeEntry> lexemeEntries;

	public Lemma() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Timestamp getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public PartOfSpch getPartOfSpch() {
		return this.partOfSpch;
	}

	public void setPartOfSpch(PartOfSpch partOfSpch) {
		this.partOfSpch = partOfSpch;
	}

	public List<LexUnit> getLexUnits() {
		return this.lexUnits;
	}

	public void setLexUnits(List<LexUnit> lexUnits) {
		this.lexUnits = lexUnits;
	}

	public LexUnit addLexUnit(LexUnit lexUnit) {
		getLexUnits().add(lexUnit);
		lexUnit.setLemma(this);

		return lexUnit;
	}

	public LexUnit removeLexUnit(LexUnit lexUnit) {
		getLexUnits().remove(lexUnit);
		lexUnit.setLemma(null);

		return lexUnit;
	}

	public List<LexemeEntry> getLexemeEntries() {
		return this.lexemeEntries;
	}

	public void setLexemeEntries(List<LexemeEntry> lexemeEntries) {
		this.lexemeEntries = lexemeEntries;
	}

	public LexemeEntry addLexemeEntry(LexemeEntry lexemeEntry) {
		getLexemeEntries().add(lexemeEntry);
		lexemeEntry.setLemma(this);

		return lexemeEntry;
	}

	public LexemeEntry removeLexemeEntry(LexemeEntry lexemeEntry) {
		getLexemeEntries().remove(lexemeEntry);
		lexemeEntry.setLemma(null);

		return lexemeEntry;
	}

}