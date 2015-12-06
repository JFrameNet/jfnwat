package jp.keio.jfn.jfnwat.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the PartOfSpch database table.
 *
 */
@Entity
@NamedQuery(name="PartOfSpch.findAll", query="SELECT p FROM PartOfSpch p")
public class PartOfSpch implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private byte id;

	private String definition;

	private String name;

	//bi-directional many-to-one association to Lemma
	@OneToMany(mappedBy="partOfSpch")
	private List<Lemma> lemmas;

	//bi-directional many-to-one association to Lexeme
	@OneToMany(mappedBy="partOfSpch")
	private List<Lexeme> lexemes;

	public PartOfSpch() {
	}

	public byte getId() {
		return this.id;
	}

	public void setId(byte id) {
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

	public List<Lemma> getLemmas() {
		return this.lemmas;
	}

	public void setLemmas(List<Lemma> lemmas) {
		this.lemmas = lemmas;
	}

	public Lemma addLemma(Lemma lemma) {
		getLemmas().add(lemma);
		lemma.setPartOfSpch(this);

		return lemma;
	}

	public Lemma removeLemma(Lemma lemma) {
		getLemmas().remove(lemma);
		lemma.setPartOfSpch(null);

		return lemma;
	}

	public List<Lexeme> getLexemes() {
		return this.lexemes;
	}

	public void setLexemes(List<Lexeme> lexemes) {
		this.lexemes = lexemes;
	}

	public Lexeme addLexeme(Lexeme lexeme) {
		getLexemes().add(lexeme);
		lexeme.setPartOfSpch(this);

		return lexeme;
	}

	public Lexeme removeLexeme(Lexeme lexeme) {
		getLexemes().remove(lexeme);
		lexeme.setPartOfSpch(null);

		return lexeme;
	}

}