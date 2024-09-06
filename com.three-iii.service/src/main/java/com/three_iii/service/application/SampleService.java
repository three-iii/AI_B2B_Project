package com.three_iii.service.application;

import com.three_iii.service.application.dto.SampleResponseDto;
import com.three_iii.service.exception.ApplicationException;
import org.springframework.stereotype.Service;

import static com.three_iii.service.exception.ErrorCode.ACCESS_DENIED;

@Service
public class SampleService {
    public SampleResponseDto getString(String msg) {
        if(msg.equals("error")){
            throw new ApplicationException(ACCESS_DENIED);
        }
        return new SampleResponseDto(msg);
    }
}
