package com.kakaopay.municipality.type;

import java.util.function.Function;

import lombok.Getter;

/**
 * 
 * CurrencyType Enum
 * 
 * created by basquiat
 *
 */
public enum CurrencyType {

	백만원(value -> value * 1000000),
	
	천만원(value -> value * 10000000),
	
	억원(value -> value * 100000000),
	
	추천금액(value -> value * 0);
	
	@Getter
	public long unit;
	
	/** long type constructor */
	CurrencyType(long unit) {
		this.unit = unit;
	}
	
	/** expression방법을 적용하자 */
	private Function<Long, Long> expression;
	
	/**
	 * constructor
	 * @param expression
	 */
	CurrencyType(Function<Long, Long> expression) {
		this.expression = expression;
	}
	
	/**
	 * 그냥 Enum타입을 통해서 실제 amount를 반환하게 만들자.
	 * @param value
	 * @return long
	 */
	public long calculate(long value) {
		return expression.apply(value);
	}
	
}
