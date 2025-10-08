package com.project.AirBnB.service;

import com.project.AirBnB.dto.HotelDTO;

public interface HotelService {

    HotelDTO createNewHotel(HotelDTO hotelDTO);

    HotelDTO getHotelById(Long id);

    HotelDTO updateHotelById(Long id,HotelDTO hotelDTO);

    void deleteHotelById(Long id);

    void activateHotelById(Long id);
}
