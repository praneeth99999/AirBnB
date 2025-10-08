package com.project.AirBnB.repository;

import com.project.AirBnB.entity.Inventory;
import com.project.AirBnB.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    void deleteByRoom(Room room);
}
