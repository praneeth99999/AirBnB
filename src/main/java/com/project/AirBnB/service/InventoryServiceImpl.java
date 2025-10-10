package com.project.AirBnB.service;

import com.project.AirBnB.dto.HotelDTO;
import com.project.AirBnB.dto.HotelSearchRequest;
import com.project.AirBnB.entity.Hotel;
import com.project.AirBnB.entity.Inventory;
import com.project.AirBnB.entity.Room;
import com.project.AirBnB.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{
    private final ModelMapper modelMapper;
    private final InventoryRepository inventoryRepository;

    @Override
    public void initializeRoomForYear(Room room) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusYears(1);

        for (; !today.isAfter(endDate);today=today.plusDays(1)) {
         Inventory inventory = Inventory.builder()
                    .hotel(room.getHotel())
                    .room(room)
                    .date(today)
                    .bookedCount(0)
                    .city(room.getHotel().getCity())
                    .price(room.getBasePrice())
                    .surgeFactor(BigDecimal.ONE)
                    .totalCount(room.getTotalCount())
                    .closed(false)
                    .build();
            inventoryRepository.save(inventory);
        }

    }

    @Override
    public void deleteAllInventories(Room room) {
        inventoryRepository.deleteByRoom(room);

    }

    @Override
    public Page<HotelDTO> searchHotels(HotelSearchRequest hotelSearchRequest) {
        log.info("searching hotels for{}",hotelSearchRequest.getCity());
        Pageable pageable= PageRequest.of(hotelSearchRequest.getPage(),hotelSearchRequest.getSize());
        Long dateCount= ChronoUnit.DAYS.between(hotelSearchRequest.getStartDate(),hotelSearchRequest.getEndDate())+1;

        Page<Hotel> hotels=inventoryRepository.findHotelSearchBYDate(hotelSearchRequest.getCity(),hotelSearchRequest.getStartDate(),hotelSearchRequest.getEndDate(),hotelSearchRequest.getRoomsCount(),dateCount,pageable);
        return hotels.map((element) -> modelMapper.map(element, HotelDTO.class));
    }
}
