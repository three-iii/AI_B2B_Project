package com.three_iii.user.application;

import com.three_iii.user.domain.repository.ShipperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShipperService {

    private final ShipperRepository shipperRepository;

}
