package jp.keio.jfn.wat.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the Frame database table.
 *
 */
@Entity
@Table(name = "Frame")
@NamedQuery(name="Frame.findAll", query="SELECT f FROM Frame f")
public class Frame implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //changed from int

	@Column(name = "CreatedBy")
	private String createdBy;

	@Column(name = "CreatedDate")
	private Timestamp createdDate;

	private String definition;

	@Lob
	private byte[] image;

	@Column(name = "ModifiedDate")
	private Timestamp modifiedDate;

	private String name;

	@Column(name = "SymbolicRep")
	private String symbolicRep;

	//bi-directional many-to-one association to FrameElement
	@OneToMany(mappedBy="frame")
	private List<FrameElement> frameElements;

	//bi-directional many-to-one association to FrameRelation
	@OneToMany(mappedBy="frame1")
	private List<FrameRelation> frameRelations1;

	//bi-directional many-to-one association to FrameRelation
	@OneToMany(mappedBy="frame2")
	private List<FrameRelation> frameRelations2;

	//bi-directional many-to-one association to LexUnit
	@OneToMany(mappedBy="frame")
	private List<LexUnit> lexUnits;

	//bi-directional many-to-one association to NoteLink
	@OneToMany(mappedBy="frame")
	private List<NoteLink> noteLinks;

	//bi-directional many-to-many association to SemanticType
	@ManyToMany(mappedBy="frames")
	private List<SemanticType> semanticTypes;

	public Frame() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
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

	public List<FrameElement> getFrameElements() {
		return this.frameElements;
	}

	public void setFrameElements(List<FrameElement> frameElements) {
		this.frameElements = frameElements;
	}

	public FrameElement addFrameElement(FrameElement frameElement) {
		getFrameElements().add(frameElement);
		frameElement.setFrame(this);

		return frameElement;
	}

	public FrameElement removeFrameElement(FrameElement frameElement) {
		getFrameElements().remove(frameElement);
		frameElement.setFrame(null);

		return frameElement;
	}

	public List<FrameRelation> getFrameRelations1() {
		return this.frameRelations1;
	}

	public void setFrameRelations1(List<FrameRelation> frameRelations1) {
		this.frameRelations1 = frameRelations1;
	}

	public FrameRelation addFrameRelations1(FrameRelation frameRelations1) {
		getFrameRelations1().add(frameRelations1);
		frameRelations1.setFrame1(this);

		return frameRelations1;
	}

	public FrameRelation removeFrameRelations1(FrameRelation frameRelations1) {
		getFrameRelations1().remove(frameRelations1);
		frameRelations1.setFrame1(null);

		return frameRelations1;
	}

	public List<FrameRelation> getFrameRelations2() {
		return this.frameRelations2;
	}

	public void setFrameRelations2(List<FrameRelation> frameRelations2) {
		this.frameRelations2 = frameRelations2;
	}

	public FrameRelation addFrameRelations2(FrameRelation frameRelations2) {
		getFrameRelations2().add(frameRelations2);
		frameRelations2.setFrame2(this);

		return frameRelations2;
	}

	public FrameRelation removeFrameRelations2(FrameRelation frameRelations2) {
		getFrameRelations2().remove(frameRelations2);
		frameRelations2.setFrame2(null);

		return frameRelations2;
	}

	public List<LexUnit> getLexUnits() {
		return this.lexUnits;
	}

	public void setLexUnits(List<LexUnit> lexUnits) {
		this.lexUnits = lexUnits;
	}

	public LexUnit addLexUnit(LexUnit lexUnit) {
		getLexUnits().add(lexUnit);
		lexUnit.setFrame(this);

		return lexUnit;
	}

	public LexUnit removeLexUnit(LexUnit lexUnit) {
		getLexUnits().remove(lexUnit);
		lexUnit.setFrame(null);

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
		noteLink.setFrame(this);

		return noteLink;
	}

	public NoteLink removeNoteLink(NoteLink noteLink) {
		getNoteLinks().remove(noteLink);
		noteLink.setFrame(null);

		return noteLink;
	}

	public List<SemanticType> getSemanticTypes() {
		return this.semanticTypes;
	}

	public void setSemanticTypes(List<SemanticType> semanticTypes) {
		this.semanticTypes = semanticTypes;
	}

}