package jp.keio.jfn.wat.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the Lexeme database table.
 *
 */
@Entity
@NamedQuery(name="Lexeme.findAll", query="SELECT l FROM Lexeme l")
public class Lexeme implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String createdBy;

	private Timestamp createdDate;

	private Timestamp modifiedDate;

	private String name;

	//bi-directional many-to-one association to PartOfSpch
	@ManyToOne
	@JoinColumn(name="PartOfSpch_Ref")
	private PartOfSpch partOfSpch;

	//bi-directional many-to-one association to LexemeEntry
	@OneToMany(mappedBy="lexeme")
	private List<LexemeEntry> lexemeEntries;

	//bi-directional many-to-one association to NoteLink
	@OneToMany(mappedBy="lexeme1")
	private List<NoteLink> noteLinks1;

	//bi-directional many-to-one association to NoteLink
	@OneToMany(mappedBy="lexeme2")
	private List<NoteLink> noteLinks2;

	//bi-directional many-to-one association to WordForm
	@OneToMany(mappedBy="lexeme")
	private List<WordForm> wordForms;

	public Lexeme() {
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PartOfSpch getPartOfSpch() {
		return this.partOfSpch;
	}

	public void setPartOfSpch(PartOfSpch partOfSpch) {
		this.partOfSpch = partOfSpch;
	}

	public List<LexemeEntry> getLexemeEntries() {
		return this.lexemeEntries;
	}

	public void setLexemeEntries(List<LexemeEntry> lexemeEntries) {
		this.lexemeEntries = lexemeEntries;
	}

	public LexemeEntry addLexemeEntry(LexemeEntry lexemeEntry) {
		getLexemeEntries().add(lexemeEntry);
		lexemeEntry.setLexeme(this);

		return lexemeEntry;
	}

	public LexemeEntry removeLexemeEntry(LexemeEntry lexemeEntry) {
		getLexemeEntries().remove(lexemeEntry);
		lexemeEntry.setLexeme(null);

		return lexemeEntry;
	}

	public List<NoteLink> getNoteLinks1() {
		return this.noteLinks1;
	}

	public void setNoteLinks1(List<NoteLink> noteLinks1) {
		this.noteLinks1 = noteLinks1;
	}

	public NoteLink addNoteLinks1(NoteLink noteLinks1) {
		getNoteLinks1().add(noteLinks1);
		noteLinks1.setLexeme1(this);

		return noteLinks1;
	}

	public NoteLink removeNoteLinks1(NoteLink noteLinks1) {
		getNoteLinks1().remove(noteLinks1);
		noteLinks1.setLexeme1(null);

		return noteLinks1;
	}

	public List<NoteLink> getNoteLinks2() {
		return this.noteLinks2;
	}

	public void setNoteLinks2(List<NoteLink> noteLinks2) {
		this.noteLinks2 = noteLinks2;
	}

	public NoteLink addNoteLinks2(NoteLink noteLinks2) {
		getNoteLinks2().add(noteLinks2);
		noteLinks2.setLexeme2(this);

		return noteLinks2;
	}

	public NoteLink removeNoteLinks2(NoteLink noteLinks2) {
		getNoteLinks2().remove(noteLinks2);
		noteLinks2.setLexeme2(null);

		return noteLinks2;
	}

	public List<WordForm> getWordForms() {
		return this.wordForms;
	}

	public void setWordForms(List<WordForm> wordForms) {
		this.wordForms = wordForms;
	}

	public WordForm addWordForm(WordForm wordForm) {
		getWordForms().add(wordForm);
		wordForm.setLexeme(this);

		return wordForm;
	}

	public WordForm removeWordForm(WordForm wordForm) {
		getWordForms().remove(wordForm);
		wordForm.setLexeme(null);

		return wordForm;
	}

}