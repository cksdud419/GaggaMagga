package com.ssafy.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class Guguns {
	private int no;
	private int sido_code;
	private int gugun_code;
	private String gugun_name;	
	
	public GugunsAi toAi() {
		GugunsAi ai =new GugunsAi();
		
		ai.setGugun_code(gugun_code);
		ai.setGugun_name(gugun_name);
		ai.setSido_code(sido_code);
		
		return ai;
	}
}
