package jp.keio.jfn.wat.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the StatusType database table.
 *
 */
@Entity
@NamedQuery(name="StatusType.findAll", query="SELECT s FROM StatusType s")
public class StatusType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private byte id;

	private String description;

	@Lob
	private byte[] icon;

	private String name;

	private int rank;

	//bi-directional many-to-one association to Status
	@OneToMany(mappedBy="statusType")
	private List<Status> statuses;

	public StatusType() {
	}

	public byte getId() {
		return this.id;
	}

	public void setId(byte id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getIcon() {
		return this.icon;
	}

	public void setIcon(byte[] icon) {
		this.icon = icon;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRank() {
		return this.rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public List<Status> getStatuses() {
		return this.statuses;
	}

	public void setStatuses(List<Status> statuses) {
		this.statuses = statuses;
	}

	public Status addStatus(Status status) {
		getStatuses().add(status);
		status.setStatusType(this);

		return status;
	}

	public Status removeStatus(Status status) {
		getStatuses().remove(status);
		status.setStatusType(null);

		return status;
	}

}