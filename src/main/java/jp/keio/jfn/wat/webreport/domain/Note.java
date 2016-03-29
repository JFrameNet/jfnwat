package jp.keio.jfn.wat.webreport.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the Note database table.
 *
 */
@Entity
@NamedQuery(name="Note.findAll", query="SELECT n FROM Note n")
public class Note implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String createdBy;

	private Timestamp createdDate;

	private Timestamp modifiedDate;

	private String text;

	//bi-directional many-to-one association to NoteType
	@ManyToOne
	@JoinColumn(name="NoteType_Ref")
	private NoteType noteType;

	//bi-directional many-to-one association to NoteLink
	@OneToMany(mappedBy="note")
	private List<NoteLink> noteLinks;

	public Note() {
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

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public NoteType getNoteType() {
		return this.noteType;
	}

	public void setNoteType(NoteType noteType) {
		this.noteType = noteType;
	}

	public List<NoteLink> getNoteLinks() {
		return this.noteLinks;
	}

	public void setNoteLinks(List<NoteLink> noteLinks) {
		this.noteLinks = noteLinks;
	}

	public NoteLink addNoteLink(NoteLink noteLink) {
		getNoteLinks().add(noteLink);
		noteLink.setNote(this);

		return noteLink;
	}

	public NoteLink removeNoteLink(NoteLink noteLink) {
		getNoteLinks().remove(noteLink);
		noteLink.setNote(null);

		return noteLink;
	}

}