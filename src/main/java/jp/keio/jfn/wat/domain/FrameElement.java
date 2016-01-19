package jp.keio.jfn.wat.domain;

import org.hibernate.annotations.Type;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the FrameElement database table.
 *
 */
@Entity
@NamedQuery(name="FrameElement.findAll", query="SELECT f FROM FrameElement f")
public class FrameElement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String abbrev;

	private String core;

	private String createdBy;

	private Timestamp createdDate;

	@Column(name = "Definition")
	@Type(type="text")
	private String definition;

	private Timestamp modifiedDate;

	private String name;

	private int semRoleRank;

	private String type;

	//bi-directional many-to-one association to FERelation
	@OneToMany(mappedBy="frameElement1")
	private List<FERelation> ferelations1;

	//bi-directional many-to-one association to FERelation
	@OneToMany(mappedBy="frameElement2")
	private List<FERelation> ferelations2;

	//bi-directional many-to-one association to Frame
	@ManyToOne
	@JoinColumn(name="Frame_Ref")
	private Frame frame;

	//bi-directional many-to-one association to LabelType
	@OneToMany(mappedBy="frameElement")
	private List<LabelType> labelTypes;

	//bi-directional many-to-one association to LexUnit
	@OneToMany(mappedBy="frameElement")
	private List<LexUnit> lexUnits;

	//bi-directional many-to-one association to NoteLink
	@OneToMany(mappedBy="frameElement")
	private List<NoteLink> noteLinks;

	//bi-directional many-to-many association to SemanticType
	@ManyToMany(mappedBy="frameElements")
	private List<SemanticType> semanticTypes;

	public FrameElement() {
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

	public String getCore() {
		return this.core;
	}

	public void setCore(String core) {
		this.core = core;
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
		String aux = this.definition.replaceAll("<ex>","\t");
		return aux.replaceAll("\n\n", "\n");
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

	public int getSemRoleRank() {
		return this.semRoleRank;
	}

	public void setSemRoleRank(int semRoleRank) {
		this.semRoleRank = semRoleRank;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<FERelation> getFerelations1() {
		return this.ferelations1;
	}

	public void setFerelations1(List<FERelation> ferelations1) {
		this.ferelations1 = ferelations1;
	}

	public FERelation addFerelations1(FERelation ferelations1) {
		getFerelations1().add(ferelations1);
		ferelations1.setFrameElement1(this);

		return ferelations1;
	}

	public FERelation removeFerelations1(FERelation ferelations1) {
		getFerelations1().remove(ferelations1);
		ferelations1.setFrameElement1(null);

		return ferelations1;
	}

	public List<FERelation> getFerelations2() {
		return this.ferelations2;
	}

	public void setFerelations2(List<FERelation> ferelations2) {
		this.ferelations2 = ferelations2;
	}

	public FERelation addFerelations2(FERelation ferelations2) {
		getFerelations2().add(ferelations2);
		ferelations2.setFrameElement2(this);

		return ferelations2;
	}

	public FERelation removeFerelations2(FERelation ferelations2) {
		getFerelations2().remove(ferelations2);
		ferelations2.setFrameElement2(null);

		return ferelations2;
	}

	public Frame getFrame() {
		return this.frame;
	}

	public void setFrame(Frame frame) {
		this.frame = frame;
	}

	public List<LabelType> getLabelTypes() {
		return this.labelTypes;
	}

	public void setLabelTypes(List<LabelType> labelTypes) {
		this.labelTypes = labelTypes;
	}

	public LabelType addLabelType(LabelType labelType) {
		getLabelTypes().add(labelType);
		labelType.setFrameElement(this);

		return labelType;
	}

	public LabelType removeLabelType(LabelType labelType) {
		getLabelTypes().remove(labelType);
		labelType.setFrameElement(null);

		return labelType;
	}

	public List<LexUnit> getLexUnits() {
		return this.lexUnits;
	}

	public void setLexUnits(List<LexUnit> lexUnits) {
		this.lexUnits = lexUnits;
	}

	public LexUnit addLexUnit(LexUnit lexUnit) {
		getLexUnits().add(lexUnit);
		lexUnit.setFrameElement(this);

		return lexUnit;
	}

	public LexUnit removeLexUnit(LexUnit lexUnit) {
		getLexUnits().remove(lexUnit);
		lexUnit.setFrameElement(null);

		return lexUnit;
	}

	public List<NoteLink> getNoteLinks() {
		return this.noteLinks;
	}

	public void setNoteLinks(List<NoteLink> noteLinks) {
		this.noteLinks = noteLinks;
	}

	public NoteLink addNoteLink(NoteLink noteLink) {
		getNoteLinks().add(noteLink);
		noteLink.setFrameElement(this);

		return noteLink;
	}

	public NoteLink removeNoteLink(NoteLink noteLink) {
		getNoteLinks().remove(noteLink);
		noteLink.setFrameElement(null);

		return noteLink;
	}

	public List<SemanticType> getSemanticTypes() {
		return this.semanticTypes;
	}

	public void setSemanticTypes(List<SemanticType> semanticTypes) {
		this.semanticTypes = semanticTypes;
	}

}