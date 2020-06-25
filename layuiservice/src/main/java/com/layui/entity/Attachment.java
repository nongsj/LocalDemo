package com.layui.entity;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Attachment extends BaseModel implements Serializable {

	private String id;
	private String uid;
	private String filename;
	private String filepath;
	private LocalDateTime uploadtime;
	private String filesize;

    /**
     *
     * @return
     */
	public String getId () {
		return id;
	}

    /**
     *
     * @param id
     */
	public void setId(String id) {
		this.id = id;
	}

    /**
     *
     * @return
     */
	public String getUid () {
		return uid;
	}

    /**
     *
     * @param uid
     */
	public void setUid(String uid) {
		this.uid = uid;
	}

    /**
     *
     * @return
     */
	public String getFilename () {
		return filename;
	}

    /**
     *
     * @param filename
     */
	public void setFilename(String filename) {
		this.filename = filename;
	}

    /**
     *
     * @return
     */
	public String getFilepath () {
		return filepath;
	}

    /**
     *
     * @param filepath
     */
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

    /**
     *
     * @return
     */
	public LocalDateTime getUploadtime () {
		return uploadtime;
	}

    /**
     *
     * @param uploadtime
     */
	public void setUploadtime(LocalDateTime uploadtime) {
		this.uploadtime = uploadtime;
	}

    /**
     *
     * @return
     */
	public String getFilesize () {
		return filesize;
	}

    /**
     *
     * @param filesize
     */
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

}