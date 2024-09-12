package com.three_iii.user.application;

import com.three_iii.user.application.dto.ShipperDto;
import com.three_iii.user.domain.Role;
import com.three_iii.user.domain.Shipper;
import com.three_iii.user.domain.ShipperType;
import com.three_iii.user.domain.User;
import com.three_iii.user.domain.repository.ShipperRepository;
import com.three_iii.user.domain.repository.UserRepository;
import com.three_iii.user.exception.ErrorCode;
import com.three_iii.user.exception.UserException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShipperService {

    private final ShipperRepository shipperRepository;
    private final UserRepository userRepository;

    @Transactional
    public UUID create(ShipperDto dto) {
        // User 존재 확인
        User user = userRepository.findById(dto.getUserId()).orElseThrow(
            () -> new UserException(ErrorCode.NOT_FOUND_USER)
        );

        // 중복 없는지 확인
        if (shipperRepository.existsByUser(user)) {
            throw new UserException(ErrorCode.DUPLICATE_SHIPPER);
        }

        // TODO Hub 존재 확인 - FeignClient 호출

        // 생성
        Shipper shipper = Shipper.of(
            ShipperType.valueOf(dto.getShipperType()),
            user,
            UUID.fromString(dto.getHubId())
        );

        // User Role 변경
        user.updateRole(Role.valueOf("SHIPPER"));
        return shipperRepository.save(shipper).getId();
    }

    private void validate() {

    }
}
