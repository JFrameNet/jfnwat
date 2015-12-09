package jp.keio.jfn.wat.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the Label database table.
 *
 */
@Entity
@NamedQuery(name="Label.findAll", query="SELECT l FROM Label l")
public class Label implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String createdBy;

	private Timestamp createdDate;

	private int endChar;

	private Timestamp modifiedDate;

	private String multi;

	private int startChar;

	//bi-directional many-to-one association to InstantiationType
	@ManyToOne
	@JoinColumn(name="InstantiationType_Ref")
	private InstantiationType instantiationType;

	//bi-directional many-to-one association to LabelType
	@ManyToOne
	@JoinColumn(name="LabelType_Ref")
	private LabelType labelType;

	//bi-directional many-to-one association to Layer
	@ManyToOne
	@JoinColumn(name="Layer_Ref")
	private Layer layer;

	//bi-directional many-to-one association to LabelPo
	@OneToMany(mappedBy="label")
	private List<LabelPos> labelPos;

	//bi-directional many-to-one association to NoteLink
	@OneToMany(mappedBy="label")
	private List<NoteLink> noteLinks;

	public Label() {
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

	public int getEndChar() {
		return this.endChar;
	}

	public void setEndChar(int endChar) {
		this.endChar = endChar;
	}

	public Timestamp getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getMulti() {
		return this.multi;
	}

	public void setMulti(String multi) {
		this.multi = multi;
	}

	public int getStartChar() {
		return this.startChar;
	}

	public void setStartChar(int startChar) {
		this.startChar = startChar;
	}

	public InstantiationType getInstantiationType() {
		return this.instantiationType;
	}

	public void setInstantiationType(InstantiationType instantiationType) {
		this.instantiationType = instantiationType;
	}

	public LabelType getLabelType() {
		return this.labelType;
	}

	public void setLabelType(LabelType labelType) {
		this.labelType = labelType;
	}

	public Layer getLayer() {
		return this.layer;
	}

	public void setLayer(Layer layer) {
		this.layer = layer;
	}

	public List<LabelPos> getLabelPos() {
		return this.labelPos;
	}

	public void setLabelPos(List<LabelPos> labelPos) {
		this.labelPos = labelPos;
	}

	public LabelPos addLabelPo(LabelPos labelPos) {
		getLabelPos().add(labelPos);
		labelPos.setLabel(this);

		return labelPos;
	}

	public LabelPos removeLabelPo(LabelPos labelPos) {
		getLabelPos().remove(labelPos);
		labelPos.setLabel(null);

		return labelPos;
	}

	public List<NoteLink> getNoteLinks() {
		return this.noteLinks;
	}

	public void setNoteLinks(List<NoteLink> noteLinks) {
		this.noteLinks = noteLinks;
	}

	public NoteLink addNoteLink(NoteLink noteLink) {
		getNoteLinks().add(noteLink);
		noteLink.setLabel(this);

		return noteLink;
	}

	public NoteLink removeNoteLink(NoteLink noteLink) {
		getNoteLinks().remove(noteLink);
		noteLink.setLabel(null);

		return noteLink;
	}

}