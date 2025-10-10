package com.project.AirBnB.service;

import com.project.AirBnB.dto.HotelDTO;
import com.project.AirBnB.dto.HotelSearchRequest;
import com.project.AirBnB.entity.Room;
import org.springframework.data.domain.Page;



public interface InventoryService {

    void initializeRoomForYear(Room room);

    void deleteAllInventories(Room room);

    Page<HotelDTO> searchHotels(HotelSearchRequest hotelSearchRequest);
}
