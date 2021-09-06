package com.metastudy.Repository;

import com.metastudy.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Override
    Optional<Room> findById(Long aLong);

    Optional<Room> findByRoomname(String roomname);
}
