package com.jleepersonal.model;

import java.util.UUID;

public class PasswordEntryHeader {
	private UUID entryId;
	private String url;
	private String entryName;
		
	// Empty constructor for JACKSON
	public PasswordEntryHeader () {	
	}

	public PasswordEntryHeader(UUID entryId, String url, String entryName) {	
		this.entryId = entryId;
		this.url = url;
		this.entryName = entryName;
	}

	public UUID getEntryId() {
		return entryId;
	}
	public void setEntryId(UUID entryId) {
		this.entryId = entryId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getEntryName() {
		return entryName;
	}
	public void setEntryName(String entryName) {
		this.entryName = entryName;
	}
	
	@Override
	public String toString() {
		return "PasswordEntryHeader [entryId=" + entryId + ", url=" + url + ", entryName=" + entryName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entryId == null) ? 0 : entryId.hashCode());
		result = prime * result + ((entryName == null) ? 0 : entryName.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PasswordEntryHeader other = (PasswordEntryHeader) obj;
		if (entryId == null) {
			if (other.entryId != null)
				return false;
		} else if (!entryId.equals(other.entryId))
			return false;
		if (entryName == null) {
			if (other.entryName != null)
				return false;
		} else if (!entryName.equals(other.entryName))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
}
