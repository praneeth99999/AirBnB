package com.project.AirBnB.service;

import com.project.AirBnB.dto.HotelDTO;
import com.project.AirBnB.dto.RoomDTO;
import com.project.AirBnB.entity.Hotel;
import com.project.AirBnB.entity.Room;
import com.project.AirBnB.exception.ResourceNotFoundException;
import com.project.AirBnB.repository.HotelRepository;
import com.project.AirBnB.repository.RoomRepository;
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
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public RoomDTO createNewRoom(Long id,RoomDTO roomDTO) {

        log.info("creating a new room in hotel with ID: {}",id);
        Hotel hotel=hotelRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with ID:"+id));
        Room room=modelMapper.map(roomDTO, Room.class);
        room.setHotel(hotel);
        room=roomRepository.save(room);

        if(hotel.getActive())
        {
            inventoryService.initializeRoomForYear(room);
        }

        return modelMapper.map(room, RoomDTO.class);
    }

    @Override
    public RoomDTO getRoomById(Long roomId) {
        log.info("Getting a room with id:{}",roomId);
        Room room=roomRepository
                .findById(roomId)
                .orElseThrow(()-> new ResourceNotFoundException("Room not found with ID:"+roomId));

        return modelMapper.map(room, RoomDTO.class);


    }

    @Override
    public List<RoomDTO> getAllRoomsInHotel(Long id) {
        log.info("Getting all rooms in hotel with id:{}",id);
        Hotel hotel=hotelRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with ID:"+id));

        return hotel.getRooms()
                .stream()
                .map((element) -> modelMapper.map(element, RoomDTO.class)).collect(Collectors.toList());

    }

    @Transactional
    @Override
    public void deleteRoomById(Long roomId) {

        log.info("deleting a room wit id: {}",roomId);
        Room room=roomRepository
                .findById(roomId)
                .orElseThrow(()-> new ResourceNotFoundException("Room not found with ID:"+roomId));

        inventoryService.deleteAllInventories(room);
        roomRepository.deleteById(roomId);

    }
}
