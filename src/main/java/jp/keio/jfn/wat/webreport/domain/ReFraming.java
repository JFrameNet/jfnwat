package jp.keio.jfn.wat.webreport.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the ReFraming database table.
 *
 */
@Entity
@NamedQuery(name="ReFraming.findAll", query="SELECT r FROM ReFraming r")
public class ReFraming implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String createdBy;

	private Timestamp createdDate;

	private Timestamp modifiedDate;

	//bi-directional many-to-one association to AnnotationSet
	@ManyToOne
	@JoinColumn(name="AnnotationSet_Ref")
	private AnnotationSet annotationSet;

	//bi-directional many-to-one association to FrameRelation
	@ManyToOne
	@JoinColumn(name="FrameRelation_Ref")
	private FrameRelation frameRelation;

	//bi-directional many-to-one association to LexUnit
	@ManyToOne
	@JoinColumn(name="LexUnit_Ref")
	private LexUnit lexUnit;

	public ReFraming() {
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

	public AnnotationSet getAnnotationSet() {
		return this.annotationSet;
	}

	public void setAnnotationSet(AnnotationSet annotationSet) {
		this.annotationSet = annotationSet;
	}

	public FrameRelation getFrameRelation() {
		return this.frameRelation;
	}

	public void setFrameRelation(FrameRelation frameRelation) {
		this.frameRelation = frameRelation;
	}

	public LexUnit getLexUnit() {
		return this.lexUnit;
	}

	public void setLexUnit(LexUnit lexUnit) {
		this.lexUnit = lexUnit;
	}

}