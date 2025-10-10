package com.project.AirBnB.service;

import com.project.AirBnB.dto.BookingDTO;
import com.project.AirBnB.dto.BookingRequest;
import com.project.AirBnB.dto.GuestDTO;

import java.util.List;

public interface BookingService {

    BookingDTO initializeBooking(BookingRequest bookingRequest);

    BookingDTO addGuests(Long bookingId, List<GuestDTO> guestDTOList);
}
