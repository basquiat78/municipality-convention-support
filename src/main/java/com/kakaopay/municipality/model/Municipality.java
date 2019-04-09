package com.kakaopay.municipality.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 
 * jpa 스펙의 양방향 연관관계를 통해서 무언가를 해보고자 노오~~력을 해보았지만 무너짐...
 * 
 * 주어진 자료는 분명 @OneToOne의 관계지만 분명 이것은 @OneToMany, @ManyToOne으로 변경될 소지가 다분히 보인다.
 * 
 * 하지만 지금은 과제에 충실하고자 해당 엔티티는 심플하게 가고자 한다.
 * 
 * created by basquiat
 *
 */
@Entity
@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "municipality")
public class Municipality {

	/**
	 * 지자체 코드
	 * 자동 생성이 아닌 어느 특정 패턴으로 넣을 것이다.
	 */
	@Id
	@Column(name = " municipality_id")
	private String municipalityCode;
	
	/** 지자체 명 */
	@Column(name = "municipality_name")
	private String municipalityName;

//	/**
//	 * one to one으로 관계를 맺어야 할까?
//	 * 가령 해당 하나의 지자체가 여러개의 협약 지원을 할 수 있지 않을까?
//	 * 하지만 주어진 데이터를 중심으로 onetoone으로...
//	 * 하지만 oneToMany로 변경된다면  외래키를 갖게 될 MunicipalityInformation이 주인이 되야하니 
//	 * mappedBy르르 municipality로 지정하자
//	 * 
//	 */
//	
//	@OneToMany(mappedBy="municipality", fetch=FetchType.EAGER)
//	private Set<MunicipalitySupport> municipalitySupport;
	
}
