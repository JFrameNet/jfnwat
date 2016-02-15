package jp.keio.jfn.wat.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the AnnotationStatus database table.
 *
 */
@Entity
@NamedQuery(name="AnnotationStatus.findAll", query="SELECT a FROM AnnotationStatus a")
public class AnnotationStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String description;

	private String name;

	//bi-directional many-to-one association to AnnotationSet
	@OneToMany(mappedBy="annotationStatus")
	private List<AnnotationSet> annotationSets;

	public AnnotationStatus() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
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

	public List<AnnotationSet> getAnnotationSets() {
		return this.annotationSets;
	}

	public void setAnnotationSets(List<AnnotationSet> annotationSets) {
		this.annotationSets = annotationSets;
	}

	public AnnotationSet addAnnotationSet(AnnotationSet annotationSet) {
		getAnnotationSets().add(annotationSet);
		annotationSet.setAnnotationStatus(this);

		return annotationSet;
	}

	public AnnotationSet removeAnnotationSet(AnnotationSet annotationSet) {
		getAnnotationSets().remove(annotationSet);
		annotationSet.setAnnotationStatus(null);

		return annotationSet;
	}

}