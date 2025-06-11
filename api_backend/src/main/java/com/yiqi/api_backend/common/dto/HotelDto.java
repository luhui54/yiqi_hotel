package com.yiqi.api_backend.common.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class HotelDto implements Serializable {

	@Serial
	private static final long serialVersionUID = -8224741222877881750L;
	private Integer brand;
	private Integer rank;
	private Integer price;
	private Integer score;

	private String destination;
	private String keyword;

}
