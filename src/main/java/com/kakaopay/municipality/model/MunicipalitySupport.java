package com.kakaopay.municipality.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 
 * csv 파일의 데이터는 분명 @OneToOne
 * 
 * 일단 oneToOne...
 * 
 * created by basquiat
 *
 */
@Entity
@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "municipality_support")
public class MunicipalitySupport {

	/** 자동 생성 설정 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	/** 일단 OneToOne */
	@OneToOne(cascade=CascadeType.ALL)
	private Municipality municipality;
	
	/** 지자체 코드 */
	@Column(name = "municipality_code")
	private String municipalityCode;
	
	/** 지원 대상 */
	@Column(name = "support_target")
	private String supportTarget;
	
	/* 용도 */
	private String usage;
	
	/** 지원 한도 */
	@Column(name = "support_limit")
	private String supportLimit;
	
	/** 2차 보전 */
	private String rate;
	
	/** 추천기관 */
	@Column(name = "recommand_institute")
	private String recommandInstitute;
	
	/** 관리점 */
	private String management;
	
	/** 취급점 */
	private String reception;
	
	/** 생성일 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	/** 수정일 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
	
	/** insert할때 현재 시간으로 인서트한다. */
    @PrePersist
    protected void setUpCreatedAt() {
    	createdAt = new Date();
    }

    /** update 이벤트가 발생시에 업데이트된 시간으로 update */
    @PreUpdate
    protected void onUpdate() {
    	updatedAt = new Date();
    }
    
	/** 컬럼에 추가되지 않지만 dto로 쓰고 버리기 위해 해당 필드를 @Transient로 추가한다. */
	@Transient
	private String municipalityName;
	
}
