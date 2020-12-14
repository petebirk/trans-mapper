package org.openbank.model;

import java.util.List;

public class TransactionMetadata {
	String narrative;
	List<String> comments;
	List<String> tags;
	List<String> images;
	String where;
	
	public String getNarrative() {
		return narrative;
	}
	public void setNarrative(String narrative) {
		this.narrative = narrative;
	}
	public List<String> getComments() {
		return comments;
	}
	public void setComments(List<String> comments) {
		this.comments = comments;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	public String getWhere() {
		return where;
	}
	public void setWhere(String where) {
		this.where = where;
	}
	
	@Override
	public String toString() {
		return "TransactionMetadata [narrative=" + narrative + ", comments=" + comments + ", tags=" + tags + ", images="
				+ images + ", where=" + where + "]";
	}
	
}
