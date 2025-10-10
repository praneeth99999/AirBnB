package com.project.AirBnB.controller;

import com.project.AirBnB.dto.BookingDTO;
import com.project.AirBnB.dto.BookingRequest;
import com.project.AirBnB.dto.GuestDTO;
import com.project.AirBnB.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class HotelBookingController {

    private final BookingService bookingService;
    @PostMapping("/init")
    public ResponseEntity<BookingDTO> initializeBooking(@RequestBody BookingRequest bookingRequest)
    {
        return ResponseEntity.ok(bookingService.initializeBooking(bookingRequest));
    }

    @PostMapping("/{bookingId}/addGuests")
    public ResponseEntity<BookingDTO> addGuests(@PathVariable Long bookingId, @RequestBody List<GuestDTO> guestDTOList)
    {
        return ResponseEntity.ok(bookingService.addGuests(bookingId,guestDTOList));
    }
}
