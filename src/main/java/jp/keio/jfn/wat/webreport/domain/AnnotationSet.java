package jp.keio.jfn.wat.webreport.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the AnnotationSet database table.
 *
 */
@Entity
@NamedQuery(name="AnnotationSet.findAll", query="SELECT a FROM AnnotationSet a")
public class AnnotationSet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String createdBy;

	private Timestamp createdDate;

	private Timestamp modifiedDate;

	//bi-directional many-to-one association to AnnotationStatus
	@ManyToOne
	@JoinColumn(name="CurrentAnnoStatus_Ref")
	private AnnotationStatus annotationStatus;

	//bi-directional many-to-one association to Construction
	@ManyToOne
	@JoinColumn(name="Construction_Ref")
	private Construction construction;

	//bi-directional many-to-one association to LexUnit
	@ManyToOne
	@JoinColumn(name="LexUnit_Ref")
	private LexUnit lexUnit;

	//bi-directional many-to-one association to Sentence
	@ManyToOne
	@JoinColumn(name="Sentence_Ref")
	private Sentence sentence;

	//bi-directional many-to-one association to SubCorpus
	@ManyToOne
	@JoinColumn(name="SubCorpus_Ref")
	private SubCorpus subCorpus;

	//bi-directional many-to-one association to Layer
	@OneToMany(mappedBy="annotationSet")
	private List<Layer> layers;

	//bi-directional many-to-one association to NoteLink
	@OneToMany(mappedBy="annotationSet")
	private List<NoteLink> noteLinks;

	//bi-directional many-to-one association to ReFraming
	@OneToMany(mappedBy="annotationSet")
	private List<ReFraming> reFramings;

	public AnnotationSet() {
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

	public AnnotationStatus getAnnotationStatus() {
		return this.annotationStatus;
	}

	public void setAnnotationStatus(AnnotationStatus annotationStatus) {
		this.annotationStatus = annotationStatus;
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

	public Sentence getSentence() {
		return this.sentence;
	}

	public void setSentence(Sentence sentence) {
		this.sentence = sentence;
	}

	public SubCorpus getSubCorpus() {
		return this.subCorpus;
	}

	public void setSubCorpus(SubCorpus subCorpus) {
		this.subCorpus = subCorpus;
	}

	public List<Layer> getLayers() {
		return this.layers;
	}

	public void setLayers(List<Layer> layers) {
		this.layers = layers;
	}

	public Layer addLayer(Layer layer) {
		getLayers().add(layer);
		layer.setAnnotationSet(this);

		return layer;
	}

	public Layer removeLayer(Layer layer) {
		getLayers().remove(layer);
		layer.setAnnotationSet(null);

		return layer;
	}

	public List<NoteLink> getNoteLinks() {
		return this.noteLinks;
	}

	public void setNoteLinks(List<NoteLink> noteLinks) {
		this.noteLinks = noteLinks;
	}

	public NoteLink addNoteLink(NoteLink noteLink) {
		getNoteLinks().add(noteLink);
		noteLink.setAnnotationSet(this);

		return noteLink;
	}

	public NoteLink removeNoteLink(NoteLink noteLink) {
		getNoteLinks().remove(noteLink);
		noteLink.setAnnotationSet(null);

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
		reFraming.setAnnotationSet(this);

		return reFraming;
	}

	public ReFraming removeReFraming(ReFraming reFraming) {
		getReFramings().remove(reFraming);
		reFraming.setAnnotationSet(null);

		return reFraming;
	}

}