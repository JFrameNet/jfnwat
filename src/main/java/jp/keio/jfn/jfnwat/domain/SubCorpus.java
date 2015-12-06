package jp.keio.jfn.jfnwat.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the SubCorpus database table.
 *
 */
@Entity
@NamedQuery(name="SubCorpus.findAll", query="SELECT s FROM SubCorpus s")
public class SubCorpus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String createdBy;

	private Timestamp createdDate;

	private Timestamp modifiedDate;

	private String name;

	private byte rank;

	//bi-directional many-to-one association to AnnotationSet
	@OneToMany(mappedBy="subCorpus")
	private List<AnnotationSet> annotationSets;

	//bi-directional many-to-one association to NoteLink
	@OneToMany(mappedBy="subCorpus")
	private List<NoteLink> noteLinks;

	//bi-directional many-to-one association to Construction
	@ManyToOne
	@JoinColumn(name="Construction_Ref")
	private Construction construction;

	//bi-directional many-to-one association to LexUnit
	@ManyToOne
	@JoinColumn(name="LexUnit_Ref")
	private LexUnit lexUnit;

	public SubCorpus() {
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

	public byte getRank() {
		return this.rank;
	}

	public void setRank(byte rank) {
		this.rank = rank;
	}

	public List<AnnotationSet> getAnnotationSets() {
		return this.annotationSets;
	}

	public void setAnnotationSets(List<AnnotationSet> annotationSets) {
		this.annotationSets = annotationSets;
	}

	public AnnotationSet addAnnotationSet(AnnotationSet annotationSet) {
		getAnnotationSets().add(annotationSet);
		annotationSet.setSubCorpus(this);

		return annotationSet;
	}

	public AnnotationSet removeAnnotationSet(AnnotationSet annotationSet) {
		getAnnotationSets().remove(annotationSet);
		annotationSet.setSubCorpus(null);

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
		noteLink.setSubCorpus(this);

		return noteLink;
	}

	public NoteLink removeNoteLink(NoteLink noteLink) {
		getNoteLinks().remove(noteLink);
		noteLink.setSubCorpus(null);

		return noteLink;
	}

	public Construction getConstruction() {
		return this.construction;
	}

	public void setConstruction(Construction construction) {
		this.construction = construction;
	}

	public LexUnit getLexUnit() {
		return this.lexUnit;
	}

	public void setLexUnit(LexUnit lexUnit) {
		this.lexUnit = lexUnit;
	}

}