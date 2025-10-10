package com.project.AirBnB.dto;

import com.project.AirBnB.entity.Guest;
import com.project.AirBnB.entity.Hotel;
import com.project.AirBnB.entity.Room;
import com.project.AirBnB.entity.User;
import com.project.AirBnB.entity.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDTO {


    private Long id;

   // private User user;

    private Integer roomsCount;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private BookingStatus bookingStatus;

    private Set<Guest> guests;

}
