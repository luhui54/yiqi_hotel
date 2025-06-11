package com.yiqi.api_backend.controller;


import com.yiqi.api_backend.common.dto.LoginDto;
import com.yiqi.api_backend.common.lang.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

	@GetMapping("/login")
	public Result test(@Validated @RequestBody LoginDto dto) {

		return Result.success(dto);
	}

}
