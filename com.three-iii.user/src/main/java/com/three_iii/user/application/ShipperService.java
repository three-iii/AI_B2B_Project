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
import com.three_iii.user.presentation.dtos.ShipperReadResponse;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShipperService {

    private final ShipperRepository shipperRepository;
    private final UserRepository userRepository;
    private final String SHIPPER_ROLE_NAME = "SHIPPER";

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
        user.updateRole(Role.valueOf(SHIPPER_ROLE_NAME));
        return shipperRepository.save(shipper).getId();
    }

    public List<ShipperReadResponse> findAll() {
        // Shipper 목록 조회
        final List<Shipper> shippers = shipperRepository.findAll();

        final List<UUID> shipperIds = shippers.stream()
            .map(Shipper::getId)
            .toList();

        List<ShipperReadResponse> responses = shippers
            .stream()
            .map(ShipperReadResponse::fromEntity)
            .collect(Collectors.toList());

        // TODO hub 이름 feignClient 로 호출해서 매핑해주기
//        HashMap<UUID, String> hubNames;
//
//        responses.forEach(response ->
//            response.updateHubName(hubNames.get(response.getHubId())));

        return responses;
    }
}
