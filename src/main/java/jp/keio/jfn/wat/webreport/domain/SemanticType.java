package jp.keio.jfn.wat.webreport.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the SemanticType database table.
 *
 */
@Entity
@NamedQuery(name="SemanticType.findAll", query="SELECT s FROM SemanticType s")
public class SemanticType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private byte id;

	private String abbrev;

	private String createdby;

	private Timestamp createdDate;

	private String description;

	private Timestamp modifiedDate;

	private String name;

	//bi-directional many-to-one association to NoteLink
	@OneToMany(mappedBy="semanticType")
	private List<NoteLink> noteLinks;

	//bi-directional many-to-many association to Frame
	@ManyToMany
	@JoinTable(
			name="SemanticType_Frame"
			, joinColumns={
			@JoinColumn(name="SemanticType_Ref")
	}
			, inverseJoinColumns={
			@JoinColumn(name="Frame_Ref")
	}
	)
	private List<Frame> frames;

	//bi-directional many-to-many association to FrameElement
	@ManyToMany
	@JoinTable(
			name="SemanticType_FrameElement"
			, joinColumns={
			@JoinColumn(name="SemanticType_Ref")
	}
			, inverseJoinColumns={
			@JoinColumn(name="FrameElement_Ref")
	}
	)
	private List<FrameElement> frameElements;

	//bi-directional many-to-many association to LexUnit
	@ManyToMany
	@JoinTable(
			name="SemanticType_LexUnit"
			, joinColumns={
			@JoinColumn(name="SemanticType_Ref")
	}
			, inverseJoinColumns={
			@JoinColumn(name="LexUnit_Ref")
	}
	)
	private List<LexUnit> lexUnits;

	//bi-directional many-to-many association to SemanticType
	@ManyToMany
	@JoinTable(
			name="STInherit"
			, joinColumns={
			@JoinColumn(name="ChildST_Ref")
	}
			, inverseJoinColumns={
			@JoinColumn(name="ParentST_Ref")
	}
	)
	private List<SemanticType> semanticTypes1;

	//bi-directional many-to-many association to SemanticType
	@ManyToMany(mappedBy="semanticTypes1")
	private List<SemanticType> semanticTypes2;

	public SemanticType() {
	}

	public byte getId() {
		return this.id;
	}

	public void setId(byte id) {
		this.id = id;
	}

	public String getAbbrev() {
		return this.abbrev;
	}

	public void setAbbrev(String abbrev) {
		this.abbrev = abbrev;
	}

	public String getCreatedby() {
		return this.createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public List<NoteLink> getNoteLinks() {
		return this.noteLinks;
	}

	public void setNoteLinks(List<NoteLink> noteLinks) {
		this.noteLinks = noteLinks;
	}

	public NoteLink addNoteLink(NoteLink noteLink) {
		getNoteLinks().add(noteLink);
		noteLink.setSemanticType(this);

		return noteLink;
	}

	public NoteLink removeNoteLink(NoteLink noteLink) {
		getNoteLinks().remove(noteLink);
		noteLink.setSemanticType(null);

		return noteLink;
	}

	public List<Frame> getFrames() {
		return this.frames;
	}

	public void setFrames(List<Frame> frames) {
		this.frames = frames;
	}

	public List<FrameElement> getFrameElements() {
		return this.frameElements;
	}

	public void setFrameElements(List<FrameElement> frameElements) {
		this.frameElements = frameElements;
	}

	public List<LexUnit> getLexUnits() {
		return this.lexUnits;
	}

	public void setLexUnits(List<LexUnit> lexUnits) {
		this.lexUnits = lexUnits;
	}

	public List<SemanticType> getSemanticTypes1() {
		return this.semanticTypes1;
	}

	public void setSemanticTypes1(List<SemanticType> semanticTypes1) {
		this.semanticTypes1 = semanticTypes1;
	}

	public List<SemanticType> getSemanticTypes2() {
		return this.semanticTypes2;
	}

	public void setSemanticTypes2(List<SemanticType> semanticTypes2) {
		this.semanticTypes2 = semanticTypes2;
	}

}