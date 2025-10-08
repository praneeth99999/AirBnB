package com.project.AirBnB.service;

import com.project.AirBnB.dto.RoomDTO;

import java.util.List;

public interface RoomService {

    RoomDTO createNewRoom(Long id, RoomDTO roomDTO);

    RoomDTO getRoomById(Long roomId);

    List<RoomDTO> getAllRoomsInHotel(Long id);

    void deleteRoomById(Long roomId);
}
