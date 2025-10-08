package com.project.AirBnB.service;

import com.project.AirBnB.entity.Room;
import com.project.AirBnB.repository.InventoryRepository;

import java.time.LocalDate;

public interface InventoryService {

    void initializeRoomForYear(Room room);

    void deleteAllInventories(Room room);
}
