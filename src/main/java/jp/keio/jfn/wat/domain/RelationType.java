package jp.keio.jfn.wat.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Alex Kabbach
 */
@Entity public class RelationType {
	private byte id;
	private String name;
	private String description;
	private String superFrameName;
	private String subFrameName;
	private Byte profiles;
	private Byte complete;

	@Id @Column(name = "ID", nullable = false) public byte getId() {
		return id;
	}

	public void setId(byte id) {
		this.id = id;
	}

	@Basic @Column(name = "Name", nullable = false, length = 80) public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic @Column(name = "Description", nullable = true, length = -1) public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Basic @Column(name = "SuperFrameName", nullable = false, length = 80) public String getSuperFrameName() {
		return superFrameName;
	}

	public void setSuperFrameName(String superFrameName) {
		this.superFrameName = superFrameName;
	}

	@Basic @Column(name = "SubFrameName", nullable = false, length = 80) public String getSubFrameName() {
		return subFrameName;
	}

	public void setSubFrameName(String subFrameName) {
		this.subFrameName = subFrameName;
	}

	@Basic @Column(name = "Profiles", nullable = true) public Byte getProfiles() {
		return profiles;
	}

	public void setProfiles(Byte profiles) {
		this.profiles = profiles;
	}

	@Basic @Column(name = "Complete", nullable = true) public Byte getComplete() {
		return complete;
	}

	public void setComplete(Byte complete) {
		this.complete = complete;
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		RelationType that = (RelationType) o;

		if (id != that.id)
			return false;
		if (name != null ? !name.equals(that.name) : that.name != null)
			return false;
		if (description != null ? !description.equals(that.description) : that.description != null)
			return false;
		if (superFrameName != null ?
				!superFrameName.equals(that.superFrameName) :
				that.superFrameName != null)
			return false;
		if (subFrameName != null ?
				!subFrameName.equals(that.subFrameName) :
				that.subFrameName != null)
			return false;
		if (profiles != null ? !profiles.equals(that.profiles) : that.profiles != null)
			return false;
		if (complete != null ? !complete.equals(that.complete) : that.complete != null)
			return false;

		return true;
	}

	@Override public int hashCode() {
		int result = (int) id;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (superFrameName != null ? superFrameName.hashCode() : 0);
		result = 31 * result + (subFrameName != null ? subFrameName.hashCode() : 0);
		result = 31 * result + (profiles != null ? profiles.hashCode() : 0);
		result = 31 * result + (complete != null ? complete.hashCode() : 0);
		return result;
	}
}
