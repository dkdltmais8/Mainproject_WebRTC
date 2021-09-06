package com.metastudy.Repository;

import com.metastudy.Entity.Room;
import com.metastudy.Entity.User;
import com.metastudy.Entity.User_Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoomRepositry extends JpaRepository<User_Room,Long> {
    Optional<User_Room> findByUser(User user);

    Optional<User_Room> findByUserAndRoom(User user, Room room);

    Optional<User_Room> findByRoom(Room room);

    Optional<User_Room> findByUsername(String usernickname);

    List<User_Room> findUser_RoomsByRoom(Room room);
}
