package jp.keio.jfn.wat.webreport.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the MiscLabel database table.
 *
 */
@Entity
@NamedQuery(name="MiscLabel.findAll", query="SELECT m FROM MiscLabel m")
public class MiscLabel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String definition;

	private String name;

	//bi-directional many-to-one association to LabelType
	@OneToMany(mappedBy="miscLabel")
	private List<LabelType> labelTypes;

	public MiscLabel() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDefinition() {
		return this.definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LabelType> getLabelTypes() {
		return this.labelTypes;
	}

	public void setLabelTypes(List<LabelType> labelTypes) {
		this.labelTypes = labelTypes;
	}

	public LabelType addLabelType(LabelType labelType) {
		getLabelTypes().add(labelType);
		labelType.setMiscLabel(this);

		return labelType;
	}

	public LabelType removeLabelType(LabelType labelType) {
		getLabelTypes().remove(labelType);
		labelType.setMiscLabel(null);

		return labelType;
	}

}