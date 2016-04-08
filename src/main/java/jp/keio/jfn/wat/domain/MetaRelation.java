package jp.keio.jfn.wat.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the MetaRelation database table.
 *
 */
@Entity
@NamedQuery(name="MetaRelation.findAll", query="SELECT m FROM MetaRelation m")
public class MetaRelation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private int a_Relation_Ref;

	private int b_Relation_Ref;

	private String createdBy;

	private Timestamp createdDate;

	private byte directed;

	private Timestamp modifiedDate;

	//bi-directional many-to-one association to MetaRelationType
	@ManyToOne
	@JoinColumn(name="MetaRelationType_Ref")
	private MetaRelationType metaRelationType;

	public MetaRelation() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getA_Relation_Ref() {
		return this.a_Relation_Ref;
	}

	public void setA_Relation_Ref(int a_Relation_Ref) {
		this.a_Relation_Ref = a_Relation_Ref;
	}

	public int getB_Relation_Ref() {
		return this.b_Relation_Ref;
	}

	public void setB_Relation_Ref(int b_Relation_Ref) {
		this.b_Relation_Ref = b_Relation_Ref;
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

	public byte getDirected() {
		return this.directed;
	}

	public void setDirected(byte directed) {
		this.directed = directed;
	}

	public Timestamp getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public MetaRelationType getMetaRelationType() {
		return this.metaRelationType;
	}

	public void setMetaRelationType(MetaRelationType metaRelationType) {
		this.metaRelationType = metaRelationType;
	}

}