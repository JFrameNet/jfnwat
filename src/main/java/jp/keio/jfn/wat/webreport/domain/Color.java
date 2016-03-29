package jp.keio.jfn.wat.webreport.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Color database table.
 *
 */
@Entity
@NamedQuery(name="Color.findAll", query="SELECT c FROM Color c")
public class Color implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private byte id;

	private String name;

	private String rgb;

	//bi-directional many-to-one association to LabelType
	@OneToMany(mappedBy="color1")
	private List<LabelType> labelTypes1;

	//bi-directional many-to-one association to LabelType
	@OneToMany(mappedBy="color2")
	private List<LabelType> labelTypes2;

	//bi-directional many-to-one association to LabelType
	@OneToMany(mappedBy="color3")
	private List<LabelType> labelTypes3;

	//bi-directional many-to-one association to LabelType
	@OneToMany(mappedBy="color4")
	private List<LabelType> labelTypes4;

	public Color() {
	}

	public byte getId() {
		return this.id;
	}

	public void setId(byte id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRgb() {
		return this.rgb;
	}

	public void setRgb(String rgb) {
		this.rgb = rgb;
	}

	public List<LabelType> getLabelTypes1() {
		return this.labelTypes1;
	}

	public void setLabelTypes1(List<LabelType> labelTypes1) {
		this.labelTypes1 = labelTypes1;
	}

	public LabelType addLabelTypes1(LabelType labelTypes1) {
		getLabelTypes1().add(labelTypes1);
		labelTypes1.setColor1(this);

		return labelTypes1;
	}

	public LabelType removeLabelTypes1(LabelType labelTypes1) {
		getLabelTypes1().remove(labelTypes1);
		labelTypes1.setColor1(null);

		return labelTypes1;
	}

	public List<LabelType> getLabelTypes2() {
		return this.labelTypes2;
	}

	public void setLabelTypes2(List<LabelType> labelTypes2) {
		this.labelTypes2 = labelTypes2;
	}

	public LabelType addLabelTypes2(LabelType labelTypes2) {
		getLabelTypes2().add(labelTypes2);
		labelTypes2.setColor2(this);

		return labelTypes2;
	}

	public LabelType removeLabelTypes2(LabelType labelTypes2) {
		getLabelTypes2().remove(labelTypes2);
		labelTypes2.setColor2(null);

		return labelTypes2;
	}

	public List<LabelType> getLabelTypes3() {
		return this.labelTypes3;
	}

	public void setLabelTypes3(List<LabelType> labelTypes3) {
		this.labelTypes3 = labelTypes3;
	}

	public LabelType addLabelTypes3(LabelType labelTypes3) {
		getLabelTypes3().add(labelTypes3);
		labelTypes3.setColor3(this);

		return labelTypes3;
	}

	public LabelType removeLabelTypes3(LabelType labelTypes3) {
		getLabelTypes3().remove(labelTypes3);
		labelTypes3.setColor3(null);

		return labelTypes3;
	}

	public List<LabelType> getLabelTypes4() {
		return this.labelTypes4;
	}

	public void setLabelTypes4(List<LabelType> labelTypes4) {
		this.labelTypes4 = labelTypes4;
	}

	public LabelType addLabelTypes4(LabelType labelTypes4) {
		getLabelTypes4().add(labelTypes4);
		labelTypes4.setColor4(this);

		return labelTypes4;
	}

	public LabelType removeLabelTypes4(LabelType labelTypes4) {
		getLabelTypes4().remove(labelTypes4);
		labelTypes4.setColor4(null);

		return labelTypes4;
	}

}