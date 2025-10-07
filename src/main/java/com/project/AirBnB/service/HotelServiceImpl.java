package com.project.AirBnB.service;

import com.project.AirBnB.dto.HotelDTO;
import com.project.AirBnB.entity.Hotel;
import com.project.AirBnB.exception.ResourceNotFoundException;
import com.project.AirBnB.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService{

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    @Override
    public HotelDTO createNewHotel(HotelDTO hotelDTO) {
        log.info("creating a new hotel with name: {}",hotelDTO.getName());
        Hotel hotel=modelMapper.map(hotelDTO, Hotel.class);
        hotel.setActive(false);
        hotelRepository.save(hotel);
        log.info("created a new hotel with name: {}",hotelDTO.getName());
        return modelMapper.map(hotel, HotelDTO.class);
    }

    @Override
    public HotelDTO getHotelById(Long id) {
        log.info("Getting a hotel with id: {}",id);
        Hotel hotel=hotelRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with ID:"+id));
     return modelMapper.map(hotel, HotelDTO.class) ;
    }
}
