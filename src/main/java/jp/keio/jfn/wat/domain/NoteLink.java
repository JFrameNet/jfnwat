package jp.keio.jfn.wat.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the NoteLink database table.
 *
 */
@Entity
@NamedQuery(name="NoteLink.findAll", query="SELECT n FROM NoteLink n")
public class NoteLink implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	//bi-directional many-to-one association to AnnotationSet
	@ManyToOne
	@JoinColumn(name="AnnotationSet_Ref")
	private AnnotationSet annotationSet;

	//bi-directional many-to-one association to ConstructElement
	@ManyToOne
	@JoinColumn(name="ConstructionElement_Ref")
	private ConstructElement constructElement;

	//bi-directional many-to-one association to Construction
	@ManyToOne
	@JoinColumn(name="Construction_Ref")
	private Construction construction;

	//bi-directional many-to-one association to Corpus
	@ManyToOne
	@JoinColumn(name="Corpus_Ref")
	private Corpus corpus;

	//bi-directional many-to-one association to Document
	@ManyToOne
	@JoinColumn(name="Document_Ref")
	private Document document;

	//bi-directional many-to-one association to Frame
	@ManyToOne
	@JoinColumn(name="Frame_Ref")
	private Frame frame;

	//bi-directional many-to-one association to FrameElement
	@ManyToOne
	@JoinColumn(name="FrameElement_Ref")
	private FrameElement frameElement;

	//bi-directional many-to-one association to Label
	@ManyToOne
	@JoinColumn(name="Label_Ref")
	private Label label;

	//bi-directional many-to-one association to Layer
	@ManyToOne
	@JoinColumn(name="Layer_Ref")
	private Layer layer;

	//bi-directional many-to-one association to LexUnit
	@ManyToOne
	@JoinColumn(name="LexUnit_Ref")
	private LexUnit lexUnit;

	//bi-directional many-to-one association to Lexeme
	@ManyToOne
	@JoinColumn(name="Lemma_Ref")
	private Lexeme lexeme1;

	//bi-directional many-to-one association to Lexeme
	@ManyToOne
	@JoinColumn(name="Lexeme_Ref")
	private Lexeme lexeme2;

	//bi-directional many-to-one association to Note
	@ManyToOne
	@JoinColumn(name="Note_Ref")
	private Note note;

	//bi-directional many-to-one association to Paragraph
	@ManyToOne
	@JoinColumn(name="Paragraph_Ref")
	private Paragraph paragraph;

	//bi-directional many-to-one association to SemanticType
	@ManyToOne
	@JoinColumn(name="SemanticType_Ref")
	private SemanticType semanticType;

	//bi-directional many-to-one association to Sentence
	@ManyToOne
	@JoinColumn(name="Sentence_Ref")
	private Sentence sentence;

	//bi-directional many-to-one association to SubCorpus
	@ManyToOne
	@JoinColumn(name="SubCorpus_Ref")
	private SubCorpus subCorpus;

	public NoteLink() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AnnotationSet getAnnotationSet() {
		return this.annotationSet;
	}

	public void setAnnotationSet(AnnotationSet annotationSet) {
		this.annotationSet = annotationSet;
	}

	public ConstructElement getConstructElement() {
		return this.constructElement;
	}

	public void setConstructElement(ConstructElement constructElement) {
		this.constructElement = constructElement;
	}

	public Construction getConstruction() {
		return this.construction;
	}

	public void setConstruction(Construction construction) {
		this.construction = construction;
	}

	public Corpus getCorpus() {
		return this.corpus;
	}

	public void setCorpus(Corpus corpus) {
		this.corpus = corpus;
	}

	public Document getDocument() {
		return this.document;
	}

	public void setDocument(Document document) {
		this.document = document;
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

	public Label getLabel() {
		return this.label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	public Layer getLayer() {
		return this.layer;
	}

	public void setLayer(Layer layer) {
		this.layer = layer;
	}

	public LexUnit getLexUnit() {
		return this.lexUnit;
	}

	public void setLexUnit(LexUnit lexUnit) {
		this.lexUnit = lexUnit;
	}

	public Lexeme getLexeme1() {
		return this.lexeme1;
	}

	public void setLexeme1(Lexeme lexeme1) {
		this.lexeme1 = lexeme1;
	}

	public Lexeme getLexeme2() {
		return this.lexeme2;
	}

	public void setLexeme2(Lexeme lexeme2) {
		this.lexeme2 = lexeme2;
	}

	public Note getNote() {
		return this.note;
	}

	public void setNote(Note note) {
		this.note = note;
	}

	public Paragraph getParagraph() {
		return this.paragraph;
	}

	public void setParagraph(Paragraph paragraph) {
		this.paragraph = paragraph;
	}

	public SemanticType getSemanticType() {
		return this.semanticType;
	}

	public void setSemanticType(SemanticType semanticType) {
		this.semanticType = semanticType;
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

}