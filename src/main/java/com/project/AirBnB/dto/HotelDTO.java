package com.project.AirBnB.dto;

import com.project.AirBnB.entity.HotelContactInfo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HotelDTO {

    private Long id;

    private String name;

    private String city;

    private String[] photos;

    private String[] amenities;

    private HotelContactInfo contactInfo;

    private Boolean active;
}
