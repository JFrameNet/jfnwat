package jp.keio.jfn.jfnwat.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Alex Kabbach
 */
@Entity public class Sequence {
	private String name;
	private Integer value;

	@Id @Column(name = "Name", nullable = false, length = 80) public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic @Column(name = "Value", nullable = true) public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Sequence sequence = (Sequence) o;

		if (name != null ? !name.equals(sequence.name) : sequence.name != null)
			return false;
		if (value != null ? !value.equals(sequence.value) : sequence.value != null)
			return false;

		return true;
	}

	@Override public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (value != null ? value.hashCode() : 0);
		return result;
	}
}
