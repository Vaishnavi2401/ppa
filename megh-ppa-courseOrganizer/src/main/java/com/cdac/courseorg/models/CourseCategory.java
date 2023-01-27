package com.cdac.courseorg.models;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the course_category database table.
 * 
 */
@Entity
@Table(name="course_category")
@NamedQuery(name="CourseCategory.findAll", query="SELECT c FROM CourseCategory c")
public class CourseCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="category_id")
	private int categoryId;

	private String category_description;

	@Column(name="category_name")
	private String categoryName;

	public CourseCategory() {
	}

	public int getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategory_description() {
		return this.category_description;
	}

	public void setCategory_description(String category_description) {
		this.category_description = category_description;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}