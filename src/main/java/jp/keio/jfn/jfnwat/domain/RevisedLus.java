package jp.keio.jfn.jfnwat.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author Alex Kabbach
 */
@Entity public class RevisedLus {
	private int id;
	private Integer lexUnitId;
	private Integer deletedLuId;
	private String createdBy;
	private Timestamp createdDate;
	private Timestamp modifiedDate;

	@Id @Column(name = "ID", nullable = false) public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic @Column(name = "LexUnitID", nullable = true) public Integer getLexUnitId() {
		return lexUnitId;
	}

	public void setLexUnitId(Integer lexUnitId) {
		this.lexUnitId = lexUnitId;
	}

	@Basic @Column(name = "DeletedLuID", nullable = true) public Integer getDeletedLuId() {
		return deletedLuId;
	}

	public void setDeletedLuId(Integer deletedLuId) {
		this.deletedLuId = deletedLuId;
	}

	@Basic @Column(name = "CreatedBy", nullable = false, length = 40) public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Basic @Column(name = "CreatedDate", nullable = true) public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	@Basic @Column(name = "ModifiedDate", nullable = false) public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		RevisedLus that = (RevisedLus) o;

		if (id != that.id)
			return false;
		if (lexUnitId != null ? !lexUnitId.equals(that.lexUnitId) : that.lexUnitId != null)
			return false;
		if (deletedLuId != null ? !deletedLuId.equals(that.deletedLuId) : that.deletedLuId != null)
			return false;
		if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null)
			return false;
		if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null)
			return false;
		if (modifiedDate != null ?
				!modifiedDate.equals(that.modifiedDate) :
				that.modifiedDate != null)
			return false;

		return true;
	}

	@Override public int hashCode() {
		int result = id;
		result = 31 * result + (lexUnitId != null ? lexUnitId.hashCode() : 0);
		result = 31 * result + (deletedLuId != null ? deletedLuId.hashCode() : 0);
		result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
		result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
		result = 31 * result + (modifiedDate != null ? modifiedDate.hashCode() : 0);
		return result;
	}
}
