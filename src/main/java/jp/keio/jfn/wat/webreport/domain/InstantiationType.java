package jp.keio.jfn.wat.webreport.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the InstantiationType database table.
 *
 */
@Entity
@NamedQuery(name="InstantiationType.findAll", query="SELECT i FROM InstantiationType i")
public class InstantiationType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private byte id;

	private String description;

	private String display;

	private String name;

	//bi-directional many-to-one association to Label
	@OneToMany(mappedBy="instantiationType")
	private List<Label> labels;

	public InstantiationType() {
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

	public String getDisplay() {
		return this.display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Label> getLabels() {
		return this.labels;
	}

	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}

	public Label addLabel(Label label) {
		getLabels().add(label);
		label.setInstantiationType(this);

		return label;
	}

	public Label removeLabel(Label label) {
		getLabels().remove(label);
		label.setInstantiationType(null);

		return label;
	}

}