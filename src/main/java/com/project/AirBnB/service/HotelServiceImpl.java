package com.project.AirBnB.service;

import com.project.AirBnB.dto.HotelDTO;
import com.project.AirBnB.dto.HotelInfoDTO;
import com.project.AirBnB.dto.RoomDTO;
import com.project.AirBnB.entity.Hotel;
import com.project.AirBnB.entity.Room;
import com.project.AirBnB.exception.ResourceNotFoundException;
import com.project.AirBnB.repository.HotelRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService{

    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;
    private final RoomService roomService;
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

    @Override
    public HotelDTO updateHotelById(Long id,HotelDTO hotelDTO) {

        log.info("updating a hotel with id: {}",id);
        Hotel hotel=hotelRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with ID:"+id));
        modelMapper.map(hotelDTO,hotel);
        hotel.setId(id);
        hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDTO.class);

    }

    @Override
    @Transactional
    public void deleteHotelById(Long id) {
        Hotel hotel=hotelRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with ID:"+id));
       for (Room room: hotel.getRooms())
       {
           //inventoryService.deleteAllInventories(room);
           roomService.deleteRoomById(room.getId());
       }

       hotelRepository.deleteById(id);
    }


    @Override
    @Transactional
    public void activateHotelById(Long id) {

        log.info("activating a hotel with id: {}",id);
        Hotel hotel=hotelRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with ID:"+id));

        hotel.setActive(true);
        hotelRepository.save(hotel);

        for(Room room:hotel.getRooms())
        {
            inventoryService.initializeRoomForYear(room);
        }
    }

    @Override
    public HotelInfoDTO getHotelInfo(Long id) {

        log.info("Getting hotel info with id: {}",id);
        Hotel hotel=hotelRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with ID:"+id));

        List<RoomDTO> rooms= hotel.getRooms()
                .stream()
                .map((element) -> modelMapper.map(element, RoomDTO.class))
                .toList();

        return new HotelInfoDTO(modelMapper.map(hotel, HotelDTO.class),rooms);

    }


}
