package com.imran.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="drools_file_tbl")
public class DroolsFileEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false)
	String title;
	
	@Column(nullable = false)
	String drools_file;
	
	@Column(nullable = true)
    int isCurrentActiveFile = 0;
	

	@Column(nullable = true)
    int active_status = 1;
	
	public DroolsFileEntity(){}
	
	public DroolsFileEntity(String title, String drools_file) {
		this.title = title;
		this.drools_file = drools_file;
	}
	
	public DroolsFileEntity(long id, String title, String drools_file) {
		this.id = id;
		this.title = title;
		this.drools_file = drools_file;
	}

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDrools_file() {
		return drools_file;
	}


	public void setDrools_file(String drools_file) {
		this.drools_file = drools_file;
	}


	public int getActive_status() {
		return active_status;
	}


	public void setActive_status(int active_status) {
		this.active_status = active_status;
	}

	public int getIsCurrentActiveFile() {
		return isCurrentActiveFile;
	}

	public void setIsCurrentActiveFile(int isCurrentActiveFile) {
		this.isCurrentActiveFile = isCurrentActiveFile;
	}
	
	

}
