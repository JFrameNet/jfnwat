package jp.keio.jfn.jfnwat.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the Sentence database table.
 *
 */
@Entity
@NamedQuery(name="Sentence.findAll", query="SELECT s FROM Sentence s")
public class Sentence implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private byte absolutePos;

	private String createdBy;

	private Timestamp createdDate;

	private String externalID;

	private Timestamp modifiedDate;

	private byte paragraphOrder;

	private String text;

	//bi-directional many-to-one association to AnnotationSet
	@OneToMany(mappedBy="sentence")
	private List<AnnotationSet> annotationSets;

	//bi-directional many-to-one association to NoteLink
	@OneToMany(mappedBy="sentence")
	private List<NoteLink> noteLinks;

	//bi-directional many-to-one association to Paragraph
	@ManyToOne
	@JoinColumn(name="Paragraph_Ref")
	private Paragraph paragraph;

	public Sentence() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getAbsolutePos() {
		return this.absolutePos;
	}

	public void setAbsolutePos(byte absolutePos) {
		this.absolutePos = absolutePos;
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

	public String getExternalID() {
		return this.externalID;
	}

	public void setExternalID(String externalID) {
		this.externalID = externalID;
	}

	public Timestamp getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public byte getParagraphOrder() {
		return this.paragraphOrder;
	}

	public void setParagraphOrder(byte paragraphOrder) {
		this.paragraphOrder = paragraphOrder;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<AnnotationSet> getAnnotationSets() {
		return this.annotationSets;
	}

	public void setAnnotationSets(List<AnnotationSet> annotationSets) {
		this.annotationSets = annotationSets;
	}

	public AnnotationSet addAnnotationSet(AnnotationSet annotationSet) {
		getAnnotationSets().add(annotationSet);
		annotationSet.setSentence(this);

		return annotationSet;
	}

	public AnnotationSet removeAnnotationSet(AnnotationSet annotationSet) {
		getAnnotationSets().remove(annotationSet);
		annotationSet.setSentence(null);

		return annotationSet;
	}

	public List<NoteLink> getNoteLinks() {
		return this.noteLinks;
	}

	public void setNoteLinks(List<NoteLink> noteLinks) {
		this.noteLinks = noteLinks;
	}

	public NoteLink addNoteLink(NoteLink noteLink) {
		getNoteLinks().add(noteLink);
		noteLink.setSentence(this);

		return noteLink;
	}

	public NoteLink removeNoteLink(NoteLink noteLink) {
		getNoteLinks().remove(noteLink);
		noteLink.setSentence(null);

		return noteLink;
	}

	public Paragraph getParagraph() {
		return this.paragraph;
	}

	public void setParagraph(Paragraph paragraph) {
		this.paragraph = paragraph;
	}

}