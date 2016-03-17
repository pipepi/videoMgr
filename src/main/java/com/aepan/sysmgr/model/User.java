package com.aepan.sysmgr.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户 model
 * @author rakika
 * 2015年6月9日下午5:19:51
 *
 */
public class User implements Serializable {

	
	public static final int PARTNER_SELLER=1;
	public static final int PARTNER_ADMIN=2;
	public static final int STATUS_FREEZE = -1;//冻结
	public static final int STATUS_CHECKING = 0;//审核中
	public static final int STATUS_OK = 1;//正常
	/**
	 * 
	 */
	private static final long serialVersionUID = -8605756708318286173L;

	private int id;
	
	private String userName;
	
	private int partnerId;
	
	private int partnerAccountId;
	
	private String partnerMobile;
	
	private String partnerAccountName;
	
	private String qqidKey;
	
	private String passWord;
	
	private String email;
	
	private int roleId;
	
	private String roleName;
	
	private int packageId;
	
	private String packageName;
	
	private int status;
	
	private Date createTime;
	
	/**
	 * @return the partnerMobile
	 */
	public String getPartnerMobile() {
		return partnerMobile;
	}

	/**
	 * @param partnerMobile the partnerMobile to set
	 */
	public void setPartnerMobile(String partnerMobile) {
		this.partnerMobile = partnerMobile;
	}

	/**
	 * @return the partnerName
	 */
	public String getPartnerAccountName() {
		return partnerAccountName;
	}

	/**
	 * @param partnerAccountName the partnerName to set
	 */
	public void setPartnerAccountName(String partnerAccountName) {
		this.partnerAccountName = partnerAccountName;
	}

	/**
	 * @return the partnerAccountId
	 */
	public int getPartnerAccountId() {
		return partnerAccountId;
	}

	/**
	 * @param partnerAccountId the partnerAccountId to set
	 */
	public void setPartnerAccountId(int partnerAccountId) {
		this.partnerAccountId = partnerAccountId;
	}

	/**
	 * @return the qqidKey
	 */
	public String getQqidKey() {
		return qqidKey;
	}

	/**
	 * @param qqidKey the qqidKey to set
	 */
	public void setQqidKey(String qqidKey) {
		this.qqidKey = qqidKey;
	}

	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @param packageName the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * @return the packageId
	 */
	public int getPackageId() {
		return packageId;
	}

	/**
	 * @param packageId the packageId to set
	 */
	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the partnerId
	 */
	public int getPartnerId() {
		return partnerId;
	}

	/**
	 * @param partnerId the partnerId to set
	 */
	public void setPartnerId(int partnerId) {
		this.partnerId = partnerId;
	}

	/**
	 * @return the passWord
	 */
	public String getPassWord() {
		return passWord;
	}

	/**
	 * @param passWord the passWord to set
	 */
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the roleId
	 */
	public int getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", partnerId="
				+ partnerId + ", partnerAccountId=" + partnerAccountId
				+ ", partnerAccountName=" + partnerAccountName + ", passWord="
				+ passWord + ", email=" + email + ", roleId=" + roleId
				+ ", roleName=" + roleName + ", packageId=" + packageId
				+ ", packageName=" + packageName + ", status=" + status
				+ ", createTime=" + createTime + "]";
	}
	
	
}
