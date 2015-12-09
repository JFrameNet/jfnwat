package jp.keio.jfn.wat.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the Construction database table.
 *
 */
@Entity
@NamedQuery(name="Construction.findAll", query="SELECT c FROM Construction c")
public class Construction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String createdBy;

	private Timestamp createdDate;

	private String definition;

	@Lob
	private byte[] image;

	private Timestamp modifiedDate;

	private String name;

	private String symbolicRep;

	//bi-directional many-to-one association to AnnotationSet
	@OneToMany(mappedBy="construction")
	private List<AnnotationSet> annotationSets;

	//bi-directional many-to-one association to ConstructElement
	@OneToMany(mappedBy="construction")
	private List<ConstructElement> constructElements;

	//bi-directional many-to-one association to NoteLink
	@OneToMany(mappedBy="construction")
	private List<NoteLink> noteLinks;

	//bi-directional many-to-one association to SubCorpus
	@OneToMany(mappedBy="construction")
	private List<SubCorpus> subCorpuses;

	public Construction() {
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

	public String getDefinition() {
		return this.definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public byte[] getImage() {
		return this.image;
	}

	public void setImage(byte[] image) {
		this.image = image;
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

	public String getSymbolicRep() {
		return this.symbolicRep;
	}

	public void setSymbolicRep(String symbolicRep) {
		this.symbolicRep = symbolicRep;
	}

	public List<AnnotationSet> getAnnotationSets() {
		return this.annotationSets;
	}

	public void setAnnotationSets(List<AnnotationSet> annotationSets) {
		this.annotationSets = annotationSets;
	}

	public AnnotationSet addAnnotationSet(AnnotationSet annotationSet) {
		getAnnotationSets().add(annotationSet);
		annotationSet.setConstruction(this);

		return annotationSet;
	}

	public AnnotationSet removeAnnotationSet(AnnotationSet annotationSet) {
		getAnnotationSets().remove(annotationSet);
		annotationSet.setConstruction(null);

		return annotationSet;
	}

	public List<ConstructElement> getConstructElements() {
		return this.constructElements;
	}

	public void setConstructElements(List<ConstructElement> constructElements) {
		this.constructElements = constructElements;
	}

	public ConstructElement addConstructElement(ConstructElement constructElement) {
		getConstructElements().add(constructElement);
		constructElement.setConstruction(this);

		return constructElement;
	}

	public ConstructElement removeConstructElement(ConstructElement constructElement) {
		getConstructElements().remove(constructElement);
		constructElement.setConstruction(null);

		return constructElement;
	}

	public List<NoteLink> getNoteLinks() {
		return this.noteLinks;
	}

	public void setNoteLinks(List<NoteLink> noteLinks) {
		this.noteLinks = noteLinks;
	}

	public NoteLink addNoteLink(NoteLink noteLink) {
		getNoteLinks().add(noteLink);
		noteLink.setConstruction(this);

		return noteLink;
	}

	public NoteLink removeNoteLink(NoteLink noteLink) {
		getNoteLinks().remove(noteLink);
		noteLink.setConstruction(null);

		return noteLink;
	}

	public List<SubCorpus> getSubCorpuses() {
		return this.subCorpuses;
	}

	public void setSubCorpuses(List<SubCorpus> subCorpuses) {
		this.subCorpuses = subCorpuses;
	}

	public SubCorpus addSubCorpus(SubCorpus subCorpus) {
		getSubCorpuses().add(subCorpus);
		subCorpus.setConstruction(this);

		return subCorpus;
	}

	public SubCorpus removeSubCorpus(SubCorpus subCorpus) {
		getSubCorpuses().remove(subCorpus);
		subCorpus.setConstruction(null);

		return subCorpus;
	}

}