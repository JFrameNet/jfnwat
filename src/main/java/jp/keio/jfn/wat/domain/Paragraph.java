package jp.keio.jfn.wat.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the Paragraph database table.
 *
 */
@Entity
@NamedQuery(name="Paragraph.findAll", query="SELECT p FROM Paragraph p")
public class Paragraph implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String createdBy;

	private Timestamp createdDate;

	private int documentOrder;

	private Timestamp modifiedDate;

	//bi-directional many-to-one association to NoteLink
	@OneToMany(mappedBy="paragraph")
	private List<NoteLink> noteLinks;

	//bi-directional many-to-one association to Document
	@ManyToOne
	@JoinColumn(name="Document_Ref")
	private Document document;

	//bi-directional many-to-one association to Sentence
	@OneToMany(mappedBy="paragraph")
	private List<Sentence> sentences;

	public Paragraph() {
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

	public int getDocumentOrder() {
		return this.documentOrder;
	}

	public void setDocumentOrder(int documentOrder) {
		this.documentOrder = documentOrder;
	}

	public Timestamp getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public List<NoteLink> getNoteLinks() {
		return this.noteLinks;
	}

	public void setNoteLinks(List<NoteLink> noteLinks) {
		this.noteLinks = noteLinks;
	}

	public NoteLink addNoteLink(NoteLink noteLink) {
		getNoteLinks().add(noteLink);
		noteLink.setParagraph(this);

		return noteLink;
	}

	public NoteLink removeNoteLink(NoteLink noteLink) {
		getNoteLinks().remove(noteLink);
		noteLink.setParagraph(null);

		return noteLink;
	}

	public Document getDocument() {
		return this.document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public List<Sentence> getSentences() {
		return this.sentences;
	}

	public void setSentences(List<Sentence> sentences) {
		this.sentences = sentences;
	}

	public Sentence addSentence(Sentence sentence) {
		getSentences().add(sentence);
		sentence.setParagraph(this);

		return sentence;
	}

	public Sentence removeSentence(Sentence sentence) {
		getSentences().remove(sentence);
		sentence.setParagraph(null);

		return sentence;
	}

}