package com.three_iii.slack.infrastructure;

import com.three_iii.slack.exception.Response;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;

public interface UserService {

    Response<ShipperResponse> findShipper(@PathVariable String shipperId);

    Response<List<ShipperResponse>> findShipperList();
}
