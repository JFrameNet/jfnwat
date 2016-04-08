package jp.keio.jfn.wat.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the LabelType database table.
 *
 */
@Entity
@NamedQuery(name="LabelType.findAll", query="SELECT l FROM LabelType l")
public class LabelType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String keystroke;

	//bi-directional many-to-one association to Label
	@OneToMany(mappedBy="labelType")
	private List<Label> labels;

	//bi-directional many-to-one association to Color
	@ManyToOne
	@JoinColumn(name="FgColorS_Ref")
	private Color color1;

	//bi-directional many-to-one association to Color
	@ManyToOne
	@JoinColumn(name="BgColorS_Ref")
	private Color color2;

	//bi-directional many-to-one association to Color
	@ManyToOne
	@JoinColumn(name="FgColorP_Ref")
	private Color color3;

	//bi-directional many-to-one association to Color
	@ManyToOne
	@JoinColumn(name="BgColorP_Ref")
	private Color color4;

	//bi-directional many-to-one association to ConstructElement
	@ManyToOne
	@JoinColumn(name="ConstructElement_Ref")
	private ConstructElement constructElement;

	//bi-directional many-to-one association to FrameElement
	@ManyToOne
	@JoinColumn(name="FrameElement_Ref")
	private FrameElement frameElement;

	//bi-directional many-to-one association to LayerType
	@ManyToOne
	@JoinColumn(name="LayerType_Ref")
	private LayerType layerType;

	//bi-directional many-to-one association to MiscLabel
	@ManyToOne
	@JoinColumn(name="MiscLabel_Ref")
	private MiscLabel miscLabel;

	public LabelType() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKeystroke() {
		return this.keystroke;
	}

	public void setKeystroke(String keystroke) {
		this.keystroke = keystroke;
	}

	public List<Label> getLabels() {
		return this.labels;
	}

	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}

	public Label addLabel(Label label) {
		getLabels().add(label);
		label.setLabelType(this);

		return label;
	}

	public Label removeLabel(Label label) {
		getLabels().remove(label);
		label.setLabelType(null);

		return label;
	}

	public Color getColor1() {
		return this.color1;
	}

	public void setColor1(Color color1) {
		this.color1 = color1;
	}

	public Color getColor2() {
		return this.color2;
	}

	public void setColor2(Color color2) {
		this.color2 = color2;
	}

	public Color getColor3() {
		return this.color3;
	}

	public void setColor3(Color color3) {
		this.color3 = color3;
	}

	public Color getColor4() {
		return this.color4;
	}

	public void setColor4(Color color4) {
		this.color4 = color4;
	}

	public ConstructElement getConstructElement() {
		return this.constructElement;
	}

	public void setConstructElement(ConstructElement constructElement) {
		this.constructElement = constructElement;
	}

	public FrameElement getFrameElement() {
		return this.frameElement;
	}

	public void setFrameElement(FrameElement frameElement) {
		this.frameElement = frameElement;
	}

	public LayerType getLayerType() {
		return this.layerType;
	}

	public void setLayerType(LayerType layerType) {
		this.layerType = layerType;
	}

	public MiscLabel getMiscLabel() {
		return this.miscLabel;
	}

	public void setMiscLabel(MiscLabel miscLabel) {
		this.miscLabel = miscLabel;
	}

}