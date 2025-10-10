package com.project.AirBnB.repository;

import com.project.AirBnB.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {

}
