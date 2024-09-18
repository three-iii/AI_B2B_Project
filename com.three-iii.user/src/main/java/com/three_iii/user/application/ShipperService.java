package com.three_iii.user.application;

import com.three_iii.user.domain.Role;
import com.three_iii.user.domain.Shipper;
import com.three_iii.user.domain.ShipperType;
import com.three_iii.user.domain.User;
import com.three_iii.user.domain.UserPrincipal;
import com.three_iii.user.domain.repository.ShipperRepository;
import com.three_iii.user.domain.repository.UserRepository;
import com.three_iii.user.exception.ErrorCode;
import com.three_iii.user.exception.UserException;
import com.three_iii.user.presentation.dtos.ShipperCreateRequest;
import com.three_iii.user.presentation.dtos.ShipperReadResponse;
import com.three_iii.user.presentation.dtos.ShipperUpdateRequest;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShipperService {

    private final ShipperRepository shipperRepository;
    private final UserRepository userRepository;
    private final String SHIPPER_ROLE_NAME = "SHIPPER";

    @Transactional
    public UUID create(ShipperCreateRequest request) {

        User user = userRepository.findById(request.getUserId()).orElseThrow(
            () -> new UserException(ErrorCode.NOT_FOUND_USER)
        );

        if (shipperRepository.existsByUser(user)) {
            throw new UserException(ErrorCode.DUPLICATE_SHIPPER);
        }

        // TODO Hub 존재 확인 - FeignClient 호출

        Shipper shipper = Shipper.of(
            ShipperType.valueOf(request.getShipperType()),
            user,
            UUID.fromString(request.getHubId())
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

    public ShipperReadResponse find(UUID shipperId, UserPrincipal getter) {
        final Shipper shipper = findShipperById(shipperId);
        // 마스터 관리자 / 허브 관리자 / 본인만 확인 가능
        if (Role.valueOf(getter.getRole()) != Role.MASTER_MANAGER) {
            if (Role.valueOf(getter.getRole()) != Role.HUB_MANAGER) {
                if (shipper.getUser().getId() != getter.getId()) {
                    throw new UserException(ErrorCode.ACCESS_DENIED);
                }
            }
            // TODO 자신의 허브 배송담당자를 삭제하는 허브 관리자인지 확인
        }
        ShipperReadResponse response = ShipperReadResponse.fromEntity(shipper);
        // TODO response.updateHubName();
        return response;
    }

    @Transactional
    public String update(UUID shipperId, ShipperUpdateRequest request) {
        Shipper shipper = findShipperById(shipperId);

        // TODO Hub 존재 확인 - FeignClient 호출

        shipper.update(request);
        return shipper.getId().toString();
    }

    @Transactional
    public void delete(UUID shipperId, UserPrincipal deleter) {
        if (Role.valueOf(deleter.getRole()) == Role.HUB_MANAGER) {
            // TODO 자신의 허브 배송담당자를 삭제하는 허브 관리자인지 확인
        }

        Shipper shipper = findShipperById(shipperId);
        shipper.delete();
    }

    private Shipper findShipperById(UUID shipperId) {
        return shipperRepository.findById(shipperId).orElseThrow(
            () -> new UserException(ErrorCode.NOT_FOUND_SHIPPER)
        );
    }


}
