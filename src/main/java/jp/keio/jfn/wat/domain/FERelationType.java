package jp.keio.jfn.wat.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the FERelationType database table.
 *
 */
@Entity
@NamedQuery(name="FERelationType.findAll", query="SELECT f FROM FERelationType f")
public class FERelationType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private byte id;

	@Lob
	private String description;

	private String name;

	//bi-directional many-to-one association to FERelation
	@OneToMany(mappedBy="ferelationType")
	private List<FERelation> ferelations;

	//bi-directional many-to-one association to RelationType
	@ManyToOne
	@JoinColumn(name="RelationType_Ref")
	private RelationType relationType;

	public FERelationType() {
	}

	public byte getId() {
		return this.id;
	}

	public void setId(byte id) {
		this.id = id;
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

	public List<FERelation> getFerelations() {
		return this.ferelations;
	}

	public void setFerelations(List<FERelation> ferelations) {
		this.ferelations = ferelations;
	}

	public FERelation addFerelation(FERelation ferelation) {
		getFerelations().add(ferelation);
		ferelation.setFerelationType(this);

		return ferelation;
	}

	public FERelation removeFerelation(FERelation ferelation) {
		getFerelations().remove(ferelation);
		ferelation.setFerelationType(null);

		return ferelation;
	}

	public RelationType getRelationType() {
		return this.relationType;
	}

	public void setRelationType(RelationType relationType) {
		this.relationType = relationType;
	}

}