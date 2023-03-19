package com.epam.nosqlrun.ratelimiter.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="user_rate")
public class TenantById implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id()
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(name = "account_id")
	private String accountId;

	@Column(name = "allowed_requests")
	private int allowedRequests;

	@Column(name = "time_interval")
	private int timeInterval;
	
}
