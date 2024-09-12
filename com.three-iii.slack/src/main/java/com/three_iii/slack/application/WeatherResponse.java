package com.three_iii.slack.application;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponse {

    private Response response;

    @Getter
    public static class Response {

        private Header header;
        private Body body;
    }

    @Getter
    public static class Header {

        private String resultCode;
        private String resultMsg;
    }

    @Getter
    public static class Body {

        private String dataType;
        private Items items;
    }


    @Getter
    public static class Items {

        private List<Item> item;
    }

    @Getter
    public static class Item {

        private String baseDate;
        private String baseTime;
        private String category;
        private String fcstDate;
        private String fcstTime;
        private String fcstValue;
        private Long nx;
        private Long ny;
    }

}
