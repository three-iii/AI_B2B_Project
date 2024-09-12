package com.three_iii.user.presentation;

import com.three_iii.user.application.ShipperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shippers")
@RequiredArgsConstructor
public class ShipperController {

    private final ShipperService shipperService;

}
