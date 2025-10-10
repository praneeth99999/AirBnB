package com.project.AirBnB.controller;

import com.project.AirBnB.dto.HotelDTO;
import com.project.AirBnB.dto.HotelInfoDTO;
import com.project.AirBnB.dto.HotelSearchRequest;
import com.project.AirBnB.service.HotelService;
import com.project.AirBnB.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hotels")
public class HotelBrowseController {

    private final InventoryService inventoryService;
    private final HotelService hotelService;

    @GetMapping("/search")
    public ResponseEntity<Page<HotelDTO>> searchHotels(@RequestBody HotelSearchRequest hotelSearchRequest)
    {

       Page<HotelDTO> page= inventoryService.searchHotels(hotelSearchRequest);

       return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}/info")
    public ResponseEntity<HotelInfoDTO> getHotelInfo(@PathVariable Long id)
    {
        return ResponseEntity.ok(hotelService.getHotelInfo(id));
    }
}
