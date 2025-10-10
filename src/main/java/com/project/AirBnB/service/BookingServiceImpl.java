package com.project.AirBnB.service;

import com.project.AirBnB.dto.BookingDTO;
import com.project.AirBnB.dto.BookingRequest;
import com.project.AirBnB.dto.GuestDTO;
import com.project.AirBnB.entity.*;
import com.project.AirBnB.entity.enums.BookingStatus;
import com.project.AirBnB.exception.ResourceNotFoundException;
import com.project.AirBnB.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{
    private final GuestRepository guestRepository;
    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public BookingDTO initializeBooking(BookingRequest bookingRequest) {

        log.info("initializing the booking with room id:{} date:{}-{}",bookingRequest.getRoomId(),bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate());
        Hotel hotel=hotelRepository.findById(bookingRequest.getId())
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with id:"+bookingRequest.getId()));

        Room room=roomRepository.findById(bookingRequest.getRoomId())
                .orElseThrow(()->new ResourceNotFoundException("Room not found with id:"+bookingRequest.getRoomId()));

        List<Inventory> inventoryList=inventoryRepository.findAndLockInventoryAvailable(bookingRequest.getRoomId(),bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate(),bookingRequest.getRoomsCount());
        long daysCount= ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate())+1;

        if(inventoryList.size()!=daysCount)
        {
            throw new IllegalStateException("Room is not available");
        }

        for(Inventory inventory:inventoryList)
        {
            inventory.setReservedCount(inventory.getReservedCount()+bookingRequest.getRoomsCount());
        }

        inventoryRepository.saveAll(inventoryList);


        Booking booking=Booking.builder()
                .hotel(hotel)
                .room(room)
                .bookingStatus(BookingStatus.RESERVED)
                .checkInDate(bookingRequest.getCheckInDate())
                .checkOutDate(bookingRequest.getCheckOutDate())
                .roomsCount(bookingRequest.getRoomsCount())
                .user(getUser())
                .price(BigDecimal.valueOf(200))
                .build();

        booking=bookingRepository.save(booking);
        return modelMapper.map(booking,BookingDTO.class);
    }

    @Override
    @Transactional
    public BookingDTO addGuests(Long bookingId, List<GuestDTO> guestDTOList) {
        log.info("adding the guests for booking with id:{}",bookingId);
        Booking booking=bookingRepository.findById(bookingId).orElseThrow(()->new ResourceNotFoundException("Booking id is not valid:"+bookingId));

        if(hasBookingExpired(booking))
        {
            throw new IllegalStateException("Booking has already expired");
        }

        if(booking.getBookingStatus()!=BookingStatus.RESERVED)
        {
            throw new IllegalStateException("Booking is not under reserved list");
        }

        for (GuestDTO guest:guestDTOList)
        {
            Guest guest1=modelMapper.map(guest, Guest.class);
            guest1.setUser(getUser());
            guest1=guestRepository.save(guest1);
            booking.getGuests().add(guest1);
        }
        booking.setBookingStatus(BookingStatus.GUESTS_ADDED);
        bookingRepository.save(booking);
        return modelMapper.map(booking,BookingDTO.class);
    }

    public boolean hasBookingExpired(Booking booking)
    {
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }

    public User getUser()
    {
        User user=new User();
        user.setId(1L);
        return user;
    }
}
