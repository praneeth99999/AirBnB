package com.project.AirBnB.service;

import com.project.AirBnB.dto.HotelDTO;

public interface HotelService {

    HotelDTO createNewHotel(HotelDTO hotelDTO);

    HotelDTO getHotelById(Long id);
}
