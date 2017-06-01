package com.zhenhappy.ems.manager.dto;

/**
 * Created by wangxd on 2017-04-19.
 */
public class QueryVisitorRequest extends EasyuiRequest {
	private Integer id;
	private String checkingno;
	private String firstName;
	private String company;
	private String email;
	private String mobilePhone;
	private Integer type;  //1：佛事展；2：矿物展；2：健康展

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getCheckingno() {
		return checkingno;
	}

	public void setCheckingno(String checkingno) {
		this.checkingno = checkingno;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
