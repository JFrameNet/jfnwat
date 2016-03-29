package jp.keio.jfn.wat.webreport.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the NoteType database table.
 *
 */
@Entity
@NamedQuery(name="NoteType.findAll", query="SELECT n FROM NoteType n")
public class NoteType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private byte id;

	private String name;

	//bi-directional many-to-one association to Note
	@OneToMany(mappedBy="noteType")
	private List<Note> notes;

	public NoteType() {
	}

	public byte getId() {
		return this.id;
	}

	public void setId(byte id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Note> getNotes() {
		return this.notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public Note addNote(Note note) {
		getNotes().add(note);
		note.setNoteType(this);

		return note;
	}

	public Note removeNote(Note note) {
		getNotes().remove(note);
		note.setNoteType(null);

		return note;
	}

}