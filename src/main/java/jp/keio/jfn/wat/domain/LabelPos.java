package jp.keio.jfn.wat.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the LabelPos database table.
 *
 */
@Entity
@Table(name="LabelPos")
@NamedQuery(name="LabelPos.findAll", query="SELECT l FROM LabelPos l")
public class LabelPos implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private int endChar;

	private int startChar;

	//bi-directional many-to-one association to Label
	@ManyToOne
	@JoinColumn(name="Label_Ref")
	private Label label;

	public LabelPos() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEndChar() {
		return this.endChar;
	}

	public void setEndChar(int endChar) {
		this.endChar = endChar;
	}

	public int getStartChar() {
		return this.startChar;
	}

	public void setStartChar(int startChar) {
		this.startChar = startChar;
	}

	public Label getLabel() {
		return this.label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

}