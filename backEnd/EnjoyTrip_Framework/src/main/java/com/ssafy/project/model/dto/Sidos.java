package com.ssafy.project.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Sidos {
	private int no;
	private int sido_code;
	private String sido_name;
	
	public SidosAi toAi() {
		SidosAi ai = new SidosAi();
		ai.setSido_code(sido_code);
		ai.setSido_name(sido_name);
		
		return ai;
	}
}
