package jp.keio.jfn.jfnwat.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the MetaRelationType database table.
 *
 */
@Entity
@NamedQuery(name="MetaRelationType.findAll", query="SELECT m FROM MetaRelationType m")
public class MetaRelationType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private byte id;

	private String a_RelationName;

	private String b_RelationName;

	private String description;

	private String name;

	//bi-directional many-to-one association to MetaRelation
	@OneToMany(mappedBy="metaRelationType")
	private List<MetaRelation> metaRelations;

	//bi-directional many-to-one association to RelationType
	@ManyToOne
	@JoinColumn(name="RelationType_Ref")
	private RelationType relationType;

	public MetaRelationType() {
	}

	public byte getId() {
		return this.id;
	}

	public void setId(byte id) {
		this.id = id;
	}

	public String getA_RelationName() {
		return this.a_RelationName;
	}

	public void setA_RelationName(String a_RelationName) {
		this.a_RelationName = a_RelationName;
	}

	public String getB_RelationName() {
		return this.b_RelationName;
	}

	public void setB_RelationName(String b_RelationName) {
		this.b_RelationName = b_RelationName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MetaRelation> getMetaRelations() {
		return this.metaRelations;
	}

	public void setMetaRelations(List<MetaRelation> metaRelations) {
		this.metaRelations = metaRelations;
	}

	public MetaRelation addMetaRelation(MetaRelation metaRelation) {
		getMetaRelations().add(metaRelation);
		metaRelation.setMetaRelationType(this);

		return metaRelation;
	}

	public MetaRelation removeMetaRelation(MetaRelation metaRelation) {
		getMetaRelations().remove(metaRelation);
		metaRelation.setMetaRelationType(null);

		return metaRelation;
	}

	public RelationType getRelationType() {
		return this.relationType;
	}

	public void setRelationType(RelationType relationType) {
		this.relationType = relationType;
	}

}