package jp.keio.jfn.jfnwat.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the FERelation database table.
 *
 */
@Entity
@NamedQuery(name="FERelation.findAll", query="SELECT f FROM FERelation f")
public class FERelation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String createdBy;

	private Timestamp createdDate;

	private Timestamp modifiedDate;

	//bi-directional many-to-one association to FERelationType
	@ManyToOne
	@JoinColumn(name="FERelationType_Ref")
	private FERelationType ferelationType;

	//bi-directional many-to-one association to FrameElement
	@ManyToOne
	@JoinColumn(name="SuperFrameElement_Ref")
	private FrameElement frameElement1;

	//bi-directional many-to-one association to FrameElement
	@ManyToOne
	@JoinColumn(name="SubFrameElement_Ref")
	private FrameElement frameElement2;

	//bi-directional many-to-one association to FrameRelation
	@ManyToOne
	@JoinColumn(name="FrameRelation_Ref")
	private FrameRelation frameRelation;

	public FERelation() {
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

	public FERelationType getFerelationType() {
		return this.ferelationType;
	}

	public void setFerelationType(FERelationType ferelationType) {
		this.ferelationType = ferelationType;
	}

	public FrameElement getFrameElement1() {
		return this.frameElement1;
	}

	public void setFrameElement1(FrameElement frameElement1) {
		this.frameElement1 = frameElement1;
	}

	public FrameElement getFrameElement2() {
		return this.frameElement2;
	}

	public void setFrameElement2(FrameElement frameElement2) {
		this.frameElement2 = frameElement2;
	}

	public FrameRelation getFrameRelation() {
		return this.frameRelation;
	}

	public void setFrameRelation(FrameRelation frameRelation) {
		this.frameRelation = frameRelation;
	}

}