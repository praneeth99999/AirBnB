package com.project.AirBnB.repository;

import com.project.AirBnB.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {

}