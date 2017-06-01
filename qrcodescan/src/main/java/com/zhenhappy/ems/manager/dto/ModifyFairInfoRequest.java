package com.zhenhappy.ems.manager.dto;

import com.zhenhappy.ems.dto.BaseRequest;

import java.util.Date;

/**
 * Created by wangxd on 2017-05-23.
 */
public class ModifyFairInfoRequest extends BaseRequest {
	private Integer fairId;
	private String fairname;
	private String fairalairname;
	private String begintime;
	private String endtime;
	private Integer fairenable;
	private Integer fairinit;
	private String database_url;
	private String load_data_begin_time;
	private String load_data_end_time;
	private String sync_onsite_start_time;

	public Integer getFairId() {
		return fairId;
	}

	public void setFairId(Integer fairId) {
		this.fairId = fairId;
	}

	public String getFairname() {
		return fairname;
	}

	public void setFairname(String fairname) {
		this.fairname = fairname;
	}

	public String getFairalairname() {
		return fairalairname;
	}

	public void setFairalairname(String fairalairname) {
		this.fairalairname = fairalairname;
	}

	public String getBegintime() {
		return begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public Integer getFairenable() {
		return fairenable;
	}

	public void setFairenable(Integer fairenable) {
		this.fairenable = fairenable;
	}

	public Integer getFairinit() {
		return fairinit;
	}

	public void setFairinit(Integer fairinit) {
		this.fairinit = fairinit;
	}

	public String getDatabase_url() {
		return database_url;
	}

	public void setDatabase_url(String database_url) {
		this.database_url = database_url;
	}

	public String getLoad_data_begin_time() {
		return load_data_begin_time;
	}

	public void setLoad_data_begin_time(String load_data_begin_time) {
		this.load_data_begin_time = load_data_begin_time;
	}

	public String getLoad_data_end_time() {
		return load_data_end_time;
	}

	public void setLoad_data_end_time(String load_data_end_time) {
		this.load_data_end_time = load_data_end_time;
	}

	public String getSync_onsite_start_time() {
		return sync_onsite_start_time;
	}

	public void setSync_onsite_start_time(String sync_onsite_start_time) {
		this.sync_onsite_start_time = sync_onsite_start_time;
	}
}
