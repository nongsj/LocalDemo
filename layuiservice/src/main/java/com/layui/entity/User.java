package com.layui.entity;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class User extends BaseModel implements Serializable {

	private String id;
	private String userAccounts;
	private String userName;
	private String userJobNumber;
	private String userJobNumberSeq;
	private Integer listNumber;
	private String password;
	private String userNamePy;
	private String userNameZm;
	private Long userPosition;
	private Long userJobCategory;
	private Long userWorkplace;
	private Long userCity;
	private Long userArea;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private Timestamp deletedAt;
	private Integer isLock;
	private Integer changePwd;
	//用于高级查询
	private String sTime;
	private String eTime;
	
	
    

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
     *
     * @return
     */
	public String getUserAccounts () {
		return userAccounts;
	}

    /**
     *
     * @param userAccounts
     */
	public void setUserAccounts(String userAccounts) {
		this.userAccounts = userAccounts;
	}

    /**
     *
     * @return
     */
	public String getUserName () {
		return userName;
	}

    /**
     *
     * @param userName
     */
	public void setUserName(String userName) {
		this.userName = userName;
	}

    /**
     *
     * @return
     */
	public String getUserJobNumber () {
		return userJobNumber;
	}

    /**
     *
     * @param userJobNumber
     */
	public void setUserJobNumber(String userJobNumber) {
		this.userJobNumber = userJobNumber;
	}

    /**
     *
     * @return
     */
	public String getUserJobNumberSeq () {
		return userJobNumberSeq;
	}

    /**
     *
     * @param userJobNumberSeq
     */
	public void setUserJobNumberSeq(String userJobNumberSeq) {
		this.userJobNumberSeq = userJobNumberSeq;
	}

    /**
     *
     * @return
     */
	public Integer getListNumber () {
		return listNumber;
	}

    /**
     *
     * @param listNumber
     */
	public void setListNumber(Integer listNumber) {
		this.listNumber = listNumber;
	}

    /**
     *
     * @return
     */
	public String getPassword () {
		return password;
	}

    /**
     *
     * @param password
     */
	public void setPassword(String password) {
		this.password = password;
	}

    /**
     *
     * @return
     */
	public String getUserNamePy () {
		return userNamePy;
	}

    /**
     *
     * @param userNamePy
     */
	public void setUserNamePy(String userNamePy) {
		this.userNamePy = userNamePy;
	}

    /**
     *
     * @return
     */
	public String getUserNameZm () {
		return userNameZm;
	}

    /**
     *
     * @param userNameZm
     */
	public void setUserNameZm(String userNameZm) {
		this.userNameZm = userNameZm;
	}

    /**
     *
     * @return
     */
	public Long getUserPosition () {
		return userPosition;
	}

    /**
     *
     * @param userPosition
     */
	public void setUserPosition(Long userPosition) {
		this.userPosition = userPosition;
	}

    /**
     *
     * @return
     */
	public Long getUserJobCategory () {
		return userJobCategory;
	}

    /**
     *
     * @param userJobCategory
     */
	public void setUserJobCategory(Long userJobCategory) {
		this.userJobCategory = userJobCategory;
	}

    /**
     *
     * @return
     */
	public Long getUserWorkplace () {
		return userWorkplace;
	}

    /**
     *
     * @param userWorkplace
     */
	public void setUserWorkplace(Long userWorkplace) {
		this.userWorkplace = userWorkplace;
	}

    /**
     *
     * @return
     */
	public Long getUserCity () {
		return userCity;
	}

    /**
     *
     * @param userCity
     */
	public void setUserCity(Long userCity) {
		this.userCity = userCity;
	}

    /**
     *
     * @return
     */
	public Long getUserArea () {
		return userArea;
	}

    /**
     *
     * @param userArea
     */
	public void setUserArea(Long userArea) {
		this.userArea = userArea;
	}

    /**
     *
     * @return
     */
	public Timestamp getCreatedAt () {
		return createdAt;
	}

    /**
     *
     * @param createdAt
     * @throws ParseException 
     */
	public void setCreatedAt(Timestamp createdAt) throws ParseException {
		this.createdAt = createdAt;
	}

    /**
     *
     * @return
     */
	public Timestamp getUpdatedAt () {
		return updatedAt;
	}

    /**
     *
     * @param updatedAt
     */
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

    /**
     *
     * @return
     */
	public Timestamp getDeletedAt () {
		return deletedAt;
	}

    /**
     *
     * @param deletedAt
     */
	public void setDeletedAt(Timestamp deletedAt) {
		this.deletedAt = deletedAt;
	}

    /**
     *
     * @return
     */
	public Integer getIsLock () {
		return isLock;
	}

    /**
     *
     * @param isLock
     */
	public void setIsLock(Integer isLock) {
		this.isLock = isLock;
	}

    /**
     *
     * @return
     */
	public Integer getChangePwd () {
		return changePwd;
	}

    /**
     *
     * @param changePwd
     */
	public void setChangePwd(Integer changePwd) {
		this.changePwd = changePwd;
	}

	public String getsTime() {
		return sTime;
	}

	public void setsTime(String sTime) {
		this.sTime = sTime;
	}

	public String geteTime() {
		return eTime;
	}

	public void seteTime(String eTime) {
		this.eTime = eTime;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userAccounts=" + userAccounts + ", userName=" + userName + ", userJobNumber="
				+ userJobNumber + ", userJobNumberSeq=" + userJobNumberSeq + ", listNumber=" + listNumber
				+ ", password=" + password + ", userNamePy=" + userNamePy + ", userNameZm=" + userNameZm
				+ ", userPosition=" + userPosition + ", userJobCategory=" + userJobCategory + ", userWorkplace="
				+ userWorkplace + ", userCity=" + userCity + ", userArea=" + userArea + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", deletedAt=" + deletedAt + ", isLock=" + isLock + ", changePwd="
				+ changePwd + ", sTime=" + sTime + ", eTime=" + eTime + "]";
	}


	
	
	
	
	
	
}