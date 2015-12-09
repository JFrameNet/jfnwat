package jp.keio.jfn.wat.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the FrameRelation database table.
 *
 */
@Entity
@NamedQuery(name="FrameRelation.findAll", query="SELECT f FROM FrameRelation f")
public class FrameRelation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String createdBy;

	private Timestamp createdDate;

	private Timestamp modifiedDate;

	private String name;

	private byte profiled;

	//bi-directional many-to-one association to FERelation
	@OneToMany(mappedBy="frameRelation")
	private List<FERelation> ferelations;

	//bi-directional many-to-one association to Frame
	@ManyToOne
	@JoinColumn(name="SuperFrame_Ref")
	private Frame frame1;

	//bi-directional many-to-one association to Frame
	@ManyToOne
	@JoinColumn(name="SubFrame_Ref")
	private Frame frame2;

	//bi-directional many-to-one association to RelationType
	@ManyToOne
	@JoinColumn(name="RelationType_Ref")
	private RelationType relationType;

	//bi-directional many-to-one association to ReFraming
	@OneToMany(mappedBy="frameRelation")
	private List<ReFraming> reFramings;

	public FrameRelation() {
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getProfiled() {
		return this.profiled;
	}

	public void setProfiled(byte profiled) {
		this.profiled = profiled;
	}

	public List<FERelation> getFerelations() {
		return this.ferelations;
	}

	public void setFerelations(List<FERelation> ferelations) {
		this.ferelations = ferelations;
	}

	public FERelation addFerelation(FERelation ferelation) {
		getFerelations().add(ferelation);
		ferelation.setFrameRelation(this);

		return ferelation;
	}

	public FERelation removeFerelation(FERelation ferelation) {
		getFerelations().remove(ferelation);
		ferelation.setFrameRelation(null);

		return ferelation;
	}

	public Frame getFrame1() {
		return this.frame1;
	}

	public void setFrame1(Frame frame1) {
		this.frame1 = frame1;
	}

	public Frame getFrame2() {
		return this.frame2;
	}

	public void setFrame2(Frame frame2) {
		this.frame2 = frame2;
	}

	public RelationType getRelationType() {
		return this.relationType;
	}

	public void setRelationType(RelationType relationType) {
		this.relationType = relationType;
	}

	public List<ReFraming> getReFramings() {
		return this.reFramings;
	}

	public void setReFramings(List<ReFraming> reFramings) {
		this.reFramings = reFramings;
	}

	public ReFraming addReFraming(ReFraming reFraming) {
		getReFramings().add(reFraming);
		reFraming.setFrameRelation(this);

		return reFraming;
	}

	public ReFraming removeReFraming(ReFraming reFraming) {
		getReFramings().remove(reFraming);
		reFraming.setFrameRelation(null);

		return reFraming;
	}

}