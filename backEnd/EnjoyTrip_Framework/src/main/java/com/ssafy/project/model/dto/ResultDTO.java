package com.ssafy.project.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ResultDTO<D> {
	private final String message;
	private final D data;
}
