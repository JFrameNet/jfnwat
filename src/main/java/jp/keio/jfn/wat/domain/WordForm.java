package jp.keio.jfn.wat.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the WordForm database table.
 *
 */
@Entity
@NamedQuery(name="WordForm.findAll", query="SELECT w FROM WordForm w")
public class WordForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String createdBy;

	private Timestamp createdDate;

	private String form;

	private Timestamp modifiedDate;

	//bi-directional many-to-one association to Lexeme
	@ManyToOne
	@JoinColumn(name="Lexeme_Ref")
	private Lexeme lexeme;

	public WordForm() {
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

	public String getForm() {
		return this.form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public Timestamp getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Lexeme getLexeme() {
		return this.lexeme;
	}

	public void setLexeme(Lexeme lexeme) {
		this.lexeme = lexeme;
	}

}