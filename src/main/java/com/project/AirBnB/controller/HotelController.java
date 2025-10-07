package com.project.AirBnB.controller;

import com.project.AirBnB.dto.HotelDTO;
import com.project.AirBnB.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/hotels")
@RequiredArgsConstructor
@Slf4j
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelDTO> createNewHotel(@RequestBody HotelDTO hotelDTO)
    {
       log.info("Attempting to create a new hotel with name: "+hotelDTO.getName());
       HotelDTO hotelDTO1=hotelService.createNewHotel(hotelDTO);
       return new ResponseEntity<>(hotelDTO1, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDTO> getHotelById(@PathVariable Long id)
    {
        HotelDTO hotelDTO=hotelService.getHotelById(id);
        return ResponseEntity.ok(hotelDTO);
    }

}
