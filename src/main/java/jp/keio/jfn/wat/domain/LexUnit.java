package jp.keio.jfn.wat.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the LexUnit database table.
 *
 */
@Entity
@NamedQuery(name="LexUnit.findAll", query="SELECT l FROM LexUnit l")
public class LexUnit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String createdBy;

	private Timestamp createdDate;

	private byte importNum;

	private Timestamp modifiedDate;

	private String name;

	private String senseDesc;

	//bi-directional many-to-one association to AnnotationSet
	@OneToMany(mappedBy="lexUnit")
	private List<AnnotationSet> annotationSets;

	//bi-directional many-to-one association to Frame
	@ManyToOne
	@JoinColumn(name="Frame_Ref")
	private Frame frame;

	//bi-directional many-to-one association to FrameElement
	@ManyToOne
	@JoinColumn(name="IncorporatedFE")
	private FrameElement frameElement;

	//bi-directional many-to-one association to Lemma
	@ManyToOne
	@JoinColumn(name="Lemma_Ref")
	private Lemma lemma;

	//bi-directional many-to-one association to NoteLink
	@OneToMany(mappedBy="lexUnit")
	private List<NoteLink> noteLinks;

	//bi-directional many-to-one association to ReFraming
	@OneToMany(mappedBy="lexUnit")
	private List<ReFraming> reFramings;

	//bi-directional many-to-many association to SemanticType
	@ManyToMany(mappedBy="lexUnits")
	private List<SemanticType> semanticTypes;

	//bi-directional many-to-one association to Status
	@OneToMany(mappedBy="lexUnit")
	private List<Status> statuses;

	//bi-directional many-to-one association to SubCorpus
	@OneToMany(mappedBy="lexUnit")
	private List<SubCorpus> subCorpuses;

	public LexUnit() {
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

	public byte getImportNum() {
		return this.importNum;
	}

	public void setImportNum(byte importNum) {
		this.importNum = importNum;
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

	public String getSenseDesc() {
		return this.senseDesc;
	}

	public void setSenseDesc(String senseDesc) {
		this.senseDesc = senseDesc;
	}

	public List<AnnotationSet> getAnnotationSets() {
		return this.annotationSets;
	}

	public void setAnnotationSets(List<AnnotationSet> annotationSets) {
		this.annotationSets = annotationSets;
	}

	public AnnotationSet addAnnotationSet(AnnotationSet annotationSet) {
		getAnnotationSets().add(annotationSet);
		annotationSet.setLexUnit(this);

		return annotationSet;
	}

	public AnnotationSet removeAnnotationSet(AnnotationSet annotationSet) {
		getAnnotationSets().remove(annotationSet);
		annotationSet.setLexUnit(null);

		return annotationSet;
	}

	public Frame getFrame() {
		return this.frame;
	}

	public void setFrame(Frame frame) {
		this.frame = frame;
	}

	public FrameElement getFrameElement() {
		return this.frameElement;
	}

	public void setFrameElement(FrameElement frameElement) {
		this.frameElement = frameElement;
	}

	public Lemma getLemma() {
		return this.lemma;
	}

	public void setLemma(Lemma lemma) {
		this.lemma = lemma;
	}

	public List<NoteLink> getNoteLinks() {
		return this.noteLinks;
	}

	public void setNoteLinks(List<NoteLink> noteLinks) {
		this.noteLinks = noteLinks;
	}

	public NoteLink addNoteLink(NoteLink noteLink) {
		getNoteLinks().add(noteLink);
		noteLink.setLexUnit(this);

		return noteLink;
	}

	public NoteLink removeNoteLink(NoteLink noteLink) {
		getNoteLinks().remove(noteLink);
		noteLink.setLexUnit(null);

		return noteLink;
	}

	public List<ReFraming> getReFramings() {
		return this.reFramings;
	}

	public void setReFramings(List<ReFraming> reFramings) {
		this.reFramings = reFramings;
	}

	public ReFraming addReFraming(ReFraming reFraming) {
		getReFramings().add(reFraming);
		reFraming.setLexUnit(this);

		return reFraming;
	}

	public ReFraming removeReFraming(ReFraming reFraming) {
		getReFramings().remove(reFraming);
		reFraming.setLexUnit(null);

		return reFraming;
	}

	public List<SemanticType> getSemanticTypes() {
		return this.semanticTypes;
	}

	public void setSemanticTypes(List<SemanticType> semanticTypes) {
		this.semanticTypes = semanticTypes;
	}

	public List<Status> getStatuses() {
		return this.statuses;
	}

	public void setStatuses(List<Status> statuses) {
		this.statuses = statuses;
	}

	public Status addStatus(Status status) {
		getStatuses().add(status);
		status.setLexUnit(this);

		return status;
	}

	public Status removeStatus(Status status) {
		getStatuses().remove(status);
		status.setLexUnit(null);

		return status;
	}

	public List<SubCorpus> getSubCorpuses() {
		return this.subCorpuses;
	}

	public void setSubCorpuses(List<SubCorpus> subCorpuses) {
		this.subCorpuses = subCorpuses;
	}

	public SubCorpus addSubCorpus(SubCorpus subCorpus) {
		getSubCorpuses().add(subCorpus);
		subCorpus.setLexUnit(this);

		return subCorpus;
	}

	public SubCorpus removeSubCorpus(SubCorpus subCorpus) {
		getSubCorpuses().remove(subCorpus);
		subCorpus.setLexUnit(null);

		return subCorpus;
	}

}