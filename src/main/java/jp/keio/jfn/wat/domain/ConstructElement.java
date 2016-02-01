package jp.keio.jfn.wat.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the ConstructElement database table.
 *
 */
@Entity
@NamedQuery(name="ConstructElement.findAll", query="SELECT c FROM ConstructElement c")
public class ConstructElement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String abbrev;

	private String createdBy;

	private Timestamp createdDate;

	private String definition;

	private Timestamp modifiedDate;

	private String name;

	//bi-directional many-to-one association to Construction
	@ManyToOne
	@JoinColumn(name="Construction_Ref")
	private Construction construction;

	//bi-directional many-to-one association to LabelType
	@OneToMany(mappedBy="constructElement")
	private List<LabelType> labelTypes;

	//bi-directional many-to-one association to NoteLink
	@OneToMany(mappedBy="constructElement")
	private List<NoteLink> noteLinks;

	public ConstructElement() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAbbrev() {
		return this.abbrev;
	}

	public void setAbbrev(String abbrev) {
		this.abbrev = abbrev;
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

	public String getDefinition() {
		return this.definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
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

	public Construction getConstruction() {
		return this.construction;
	}

	public void setConstruction(Construction construction) {
		this.construction = construction;
	}

	public List<LabelType> getLabelTypes() {
		return this.labelTypes;
	}

	public void setLabelTypes(List<LabelType> labelTypes) {
		this.labelTypes = labelTypes;
	}

	public LabelType addLabelType(LabelType labelType) {
		getLabelTypes().add(labelType);
		labelType.setConstructElement(this);

		return labelType;
	}

	public LabelType removeLabelType(LabelType labelType) {
		getLabelTypes().remove(labelType);
		labelType.setConstructElement(null);

		return labelType;
	}

	public List<NoteLink> getNoteLinks() {
		return this.noteLinks;
	}

	public void setNoteLinks(List<NoteLink> noteLinks) {
		this.noteLinks = noteLinks;
	}

	public NoteLink addNoteLink(NoteLink noteLink) {
		getNoteLinks().add(noteLink);
		noteLink.setConstructElement(this);

		return noteLink;
	}

	public NoteLink removeNoteLink(NoteLink noteLink) {
		getNoteLinks().remove(noteLink);
		noteLink.setConstructElement(null);

		return noteLink;
	}

}