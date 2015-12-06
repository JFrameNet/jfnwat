package jp.keio.jfn.jfnwat.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the Layer database table.
 *
 */
@Entity
@NamedQuery(name="Layer.findAll", query="SELECT l FROM Layer l")
public class Layer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String createdBy;

	private Timestamp createdDate;

	private Timestamp modifiedDate;

	private byte rank;

	//bi-directional many-to-one association to Label
	@OneToMany(mappedBy="layer")
	private List<Label> labels;

	//bi-directional many-to-one association to AnnotationSet
	@ManyToOne
	@JoinColumn(name="AnnotationSet_Ref")
	private AnnotationSet annotationSet;

	//bi-directional many-to-one association to LayerType
	@ManyToOne
	@JoinColumn(name="LayerType_Ref")
	private LayerType layerType;

	//bi-directional many-to-one association to NoteLink
	@OneToMany(mappedBy="layer")
	private List<NoteLink> noteLinks;

	public Layer() {
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

	public byte getRank() {
		return this.rank;
	}

	public void setRank(byte rank) {
		this.rank = rank;
	}

	public List<Label> getLabels() {
		return this.labels;
	}

	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}

	public Label addLabel(Label label) {
		getLabels().add(label);
		label.setLayer(this);

		return label;
	}

	public Label removeLabel(Label label) {
		getLabels().remove(label);
		label.setLayer(null);

		return label;
	}

	public AnnotationSet getAnnotationSet() {
		return this.annotationSet;
	}

	public void setAnnotationSet(AnnotationSet annotationSet) {
		this.annotationSet = annotationSet;
	}

	public LayerType getLayerType() {
		return this.layerType;
	}

	public void setLayerType(LayerType layerType) {
		this.layerType = layerType;
	}

	public List<NoteLink> getNoteLinks() {
		return this.noteLinks;
	}

	public void setNoteLinks(List<NoteLink> noteLinks) {
		this.noteLinks = noteLinks;
	}

	public NoteLink addNoteLink(NoteLink noteLink) {
		getNoteLinks().add(noteLink);
		noteLink.setLayer(this);

		return noteLink;
	}

	public NoteLink removeNoteLink(NoteLink noteLink) {
		getNoteLinks().remove(noteLink);
		noteLink.setLayer(null);

		return noteLink;
	}

}