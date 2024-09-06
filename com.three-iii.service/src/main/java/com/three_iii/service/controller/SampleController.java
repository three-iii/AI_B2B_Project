package com.three_iii.service.controller;

import com.three_iii.service.application.SampleService;
import com.three_iii.service.application.dto.SampleResponseDto;
import com.three_iii.service.exception.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sample")
@Tag(name = "Sample API", description = "데모용입니다.")
public class SampleController {

    private final SampleService sampleService;

    @GetMapping
    @Operation(summary = "샘플용", description = "샘플용 조회")
    public Response<SampleResponseDto> sampleMethod(@RequestParam(required = false) String msg){
        sampleService.getString(msg);
        return Response.success(sampleService.getString(msg));
    }

}
