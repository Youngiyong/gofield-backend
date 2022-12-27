package com.gofield.infrastructure.external.api.tracker.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CarrierTrackResponse {
    private NameTimeField from;
    private NameTimeField to;
    private IdTextField state;
    private List<Progress> progresses;
    private Carrier carrier;


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NameTimeField {
        private String name;
        private String time;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IdTextField {
        private String id;
        private String text;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Progress {
        private String time;
        private IdTextField status;
        private Location location;
        private String description;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Location {
        private String name;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Carrier {
        private String id;
        private String name;
        private String tel;
    }

}

