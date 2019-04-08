package com.kakaopay.municipality.type;

import java.util.function.Function;

import lombok.Getter;

public enum CurrencyType {

	백만원(value -> value * 1000000),
	
	천만원(value -> value * 10000000),
	
	억원(value -> value * 100000000),
	
	추천금액(value -> value * 0);
	
	@Getter
	public long unit;
	
	/** int type constructor */
	CurrencyType(long unit) {
		this.unit = unit;
	}
	
	private Function<Long, Long> expression;
	
	CurrencyType(Function<Long, Long> expression) {
		this.expression = expression;
	}
	
	public long calculate(long value) {
		return expression.apply(value);
	}
	
}
