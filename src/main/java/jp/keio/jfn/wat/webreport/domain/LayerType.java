package jp.keio.jfn.wat.webreport.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the LayerType database table.
 *
 */
@Entity
@NamedQuery(name="LayerType.findAll", query="SELECT l FROM LayerType l")
public class LayerType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private byte allowsApositional;

	private String definition;

	private byte isAnnotation;

	private String LGroup;

	private String name;

	//bi-directional many-to-one association to LabelType
	@OneToMany(mappedBy="layerType")
	private List<LabelType> labelTypes;

	//bi-directional many-to-one association to Layer
	@OneToMany(mappedBy="layerType")
	private List<Layer> layers;

	public LayerType() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getAllowsApositional() {
		return this.allowsApositional;
	}

	public void setAllowsApositional(byte allowsApositional) {
		this.allowsApositional = allowsApositional;
	}

	public String getDefinition() {
		return this.definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public byte getIsAnnotation() {
		return this.isAnnotation;
	}

	public void setIsAnnotation(byte isAnnotation) {
		this.isAnnotation = isAnnotation;
	}

	public String getLGroup() {
		return this.LGroup;
	}

	public void setLGroup(String LGroup) {
		this.LGroup = LGroup;
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
		labelType.setLayerType(this);

		return labelType;
	}

	public LabelType removeLabelType(LabelType labelType) {
		getLabelTypes().remove(labelType);
		labelType.setLayerType(null);

		return labelType;
	}

	public List<Layer> getLayers() {
		return this.layers;
	}

	public void setLayers(List<Layer> layers) {
		this.layers = layers;
	}

	public Layer addLayer(Layer layer) {
		getLayers().add(layer);
		layer.setLayerType(this);

		return layer;
	}

	public Layer removeLayer(Layer layer) {
		getLayers().remove(layer);
		layer.setLayerType(null);

		return layer;
	}

}