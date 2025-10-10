package com.project.AirBnB.dto;

import com.project.AirBnB.entity.User;
import com.project.AirBnB.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class GuestDTO {
    private Long id;

    private User user;

    private String name;
    private Gender gender;

    private Integer age;
}
