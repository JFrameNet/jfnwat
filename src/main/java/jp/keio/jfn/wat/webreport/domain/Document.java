package jp.keio.jfn.wat.webreport.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the Document database table.
 *
 */
@Entity
@NamedQuery(name="Document.findAll", query="SELECT d FROM Document d")
public class Document implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String author;

	private String createdBy;

	private Timestamp createdDate;

	private String description;

	private Timestamp modifiedDate;

	private String name;

	//bi-directional many-to-one association to Corpus
	@ManyToOne
	@JoinColumn(name="Corpus_Ref")
	private Corpus corpus;

	//bi-directional many-to-many association to Genre
	@ManyToMany
	@JoinTable(
			name="Document_Genre"
			, joinColumns={
			@JoinColumn(name="Document_Ref")
	}
			, inverseJoinColumns={
			@JoinColumn(name="Genre_Ref")
	}
	)
	private List<Genre> genres;

	//bi-directional many-to-one association to NoteLink
	@OneToMany(mappedBy="document")
	private List<NoteLink> noteLinks;

	//bi-directional many-to-one association to Paragraph
	@OneToMany(mappedBy="document")
	private List<Paragraph> paragraphs;

	public Document() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Corpus getCorpus() {
		return this.corpus;
	}

	public void setCorpus(Corpus corpus) {
		this.corpus = corpus;
	}

	public List<Genre> getGenres() {
		return this.genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}

	public List<NoteLink> getNoteLinks() {
		return this.noteLinks;
	}

	public void setNoteLinks(List<NoteLink> noteLinks) {
		this.noteLinks = noteLinks;
	}

	public NoteLink addNoteLink(NoteLink noteLink) {
		getNoteLinks().add(noteLink);
		noteLink.setDocument(this);

		return noteLink;
	}

	public NoteLink removeNoteLink(NoteLink noteLink) {
		getNoteLinks().remove(noteLink);
		noteLink.setDocument(null);

		return noteLink;
	}

	public List<Paragraph> getParagraphs() {
		return this.paragraphs;
	}

	public void setParagraphs(List<Paragraph> paragraphs) {
		this.paragraphs = paragraphs;
	}

	public Paragraph addParagraph(Paragraph paragraph) {
		getParagraphs().add(paragraph);
		paragraph.setDocument(this);

		return paragraph;
	}

	public Paragraph removeParagraph(Paragraph paragraph) {
		getParagraphs().remove(paragraph);
		paragraph.setDocument(null);

		return paragraph;
	}

}