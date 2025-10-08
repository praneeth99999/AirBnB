package com.project.AirBnB.controller;

import com.project.AirBnB.dto.RoomDTO;
import com.project.AirBnB.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/hotels/{id}/rooms")
public class RoomController {

    private final RoomService roomService;

    @PostMapping()
    public ResponseEntity<RoomDTO> createNewRoom(@PathVariable Long id, @RequestBody RoomDTO roomDTO)
    {
        RoomDTO room=roomService.createNewRoom(id,roomDTO);
        return new ResponseEntity<>(room, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RoomDTO>> getAllRoomsInHotel(@PathVariable Long id)
    {
        return ResponseEntity.ok(roomService.getAllRoomsInHotel(id));

    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long id,@PathVariable Long roomId)
    {
        return ResponseEntity.ok(roomService.getRoomById(roomId));

    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoomById(@PathVariable Long id,@PathVariable Long roomId)
    {
        roomService.deleteRoomById(roomId);
        return ResponseEntity.noContent().build();
    }

}
