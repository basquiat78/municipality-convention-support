package com.kakaopay.municipality.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 
 * 
 * csv 파일의 데이터는 분명 @OneToOne 하지만 나중을 위해 @ManyToOne으로 구조를 가져간다.
 * 
 * created by basquiat
 *
 */
@Entity
@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "Municipality_support")
public class MunicipalitySupport {

	/**
	 * 자동 생성 설정
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	/**
	 * 
	 * 일단 OneToOne
	 * 
	 */
	@OneToOne(cascade=CascadeType.ALL)
	private Municipality municipality;
	
	/**
	 * 지자체 코드
	 */
	@Column(name = "municipality_code")
	private String municipalityCode;
	
	@Transient
	@JsonProperty("region")
	private String municipalityName;
	
	/**
	 * 지원 대상
	 */
	@Column(name = "support_target")
	private String supportTarget;
	
	/**
	 * 용도
	 */
	private String usage;
	
	/**
	 * 지원 한도
	 */
	@Column(name = "support_limit")
	private String supportLimit;
	
	/**
	 * 2차 보전
	 */
	private String rate;
	
	/**
	 * 추천기관
	 */
	@Column(name = "recommand_institute")
	private String recommandInstitute;
	
	/**
	 * 관리점
	 */
	private String management;
	
	/**
	 * 취급점
	 */
	private String reception;
	
	/**
	 * 생성일
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	/**
	 * 생성일
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
	
}
