package com.metastudy.Service;

import com.metastudy.Common.Response.TimeResponse;
import com.metastudy.Common.request.CreateRoomPostReq;
import com.metastudy.Common.request.UserRoomPostReq;
import com.metastudy.Entity.Room;
import com.metastudy.Entity.User;
import com.metastudy.Entity.User_Room;
import com.metastudy.Repository.RoomRepository;
import com.metastudy.Repository.UserRepository;
import com.metastudy.Repository.UserRepositorySup;
import com.metastudy.Repository.UserRoomRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service("roomService")
public class RoomServiceImpl implements RoomService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    UserRoomRepositry userRoomRepositry;
    @Autowired
    UserRepositorySup userRepositorySup;
    @Override
    public Room createRoom(CreateRoomPostReq registerInfo) {

        Room room = new Room();

        room.setRoomname(registerInfo.getRoomname());
        room.setPin(registerInfo.getPin());

        return roomRepository.save(room);
    }

    @Override
    public Room roomNumber(String roomname) {

        Room room = new Room();
        Optional<Room> findroom = roomRepository.findByRoomname(roomname);

        room.setId(findroom.get().getId());
        return findroom.get();
    }

    @Override
    public void roomJoin(UserRoomPostReq userroom) {

        User_Room user_room = new User_Room();

        Optional<User> user = userRepository.findById(userroom.getUserpk());
        Optional<Room> room = roomRepository.findById(userroom.getRoompk());

        Optional<User_Room> check = userRoomRepositry.findByUserAndRoom(user.get(),room.get());


        if(check.isPresent())
        {
            Optional<User_Room> update = userRoomRepositry.findById(check.get().getId());
            System.out.println(check.get().getId());
            System.out.println(update.toString());
            System.out.println("수정");
            System.out.println(user.get().toString());
            if(update.isPresent())
            {
                user_room = update.get();
                user_room.setState(true);
                user_room.setUser(user.get());
                user_room.setRoom(room.get());
                user_room.setUsername(userroom.getUsername());

                userRoomRepositry.save(user_room);
            }
        }
        else {
            user_room.setState(true);
            System.out.println("추가");
            System.out.println(user.get().toString());
            if (user.isPresent()) {
                user_room.setUser(user.get());
            }
            if (room.isPresent()) {
                user_room.setRoom(room.get());
            }
            user_room.setUsername(userroom.getUsername());

            userRoomRepositry.save(user_room);
        }
    }

    @Override
    public void leavRoom(Long roompk, Long userpk) {

        Optional<User> user = userRepository.findById(userpk);
        Optional<Room> room = roomRepository.findById(roompk);


        Optional<User_Room> user_room =  userRoomRepositry.findByUserAndRoom(user.get(),room.get());
        if (user_room.isPresent())
        {
            User_Room update = user_room.get();

            update.setState(false);

            Duration duration = Duration.between(LocalTime.now(), update.getJoinTime());

            update.setSumTime(Math.abs(duration.getSeconds())+ update.getSumTime());
            userRoomRepositry.save(update);
        }
    }
    @Override
    public void deleteRoom(Long roompk) {
        Optional<Room> room = roomRepository.findById(roompk);
        deleteUserRoom(room.get());
        roomRepository.delete(room.get());
    }

    @Override
    public List<User_Room> getUserRoomList(Long roompk) {
        Optional<Room> room = roomRepository.findById(roompk);

        List<User_Room> list = userRoomRepositry.findUser_RoomsByRoom(room.get());

        return list;
    }

    @Override
    public List<TimeResponse> getTime(Long roompk) {

        List<User_Room> user_roomList = getUserRoomList(roompk);
        List<TimeResponse> result = new ArrayList();

        for (int i=0;i<user_roomList.size();i++)
        {
            Duration duration;
            if(user_roomList.get(i).getState())
            {
                duration = Duration.between(LocalTime.now(), user_roomList.get(i).getJoinTime());


                long diff = Math.abs(duration.getSeconds())+user_roomList.get(i).getSumTime();

                TimeResponse temp = TimeResponse.res(diff, user_roomList.get(i).getUser().getId(),
                        user_roomList.get(i).getUsername());
                result.add(temp);
            }
            else
            {
                long diff = user_roomList.get(i).getSumTime();
                TimeResponse temp = TimeResponse.res(diff, user_roomList.get(i).getUser().getId(),
                        user_roomList.get(i).getUsername());
                result.add(temp);
            }
        }

        return result;
    }

    @Override
    public Room getRoomByRoomname(String roomname) {
        Room room = userRepositorySup.findRoomByRoomName(roomname).get();
        System.out.println(room.toString());

        return room;
    }

    public void deleteUserRoom(Room room) {
        Optional<User_Room> user_room =  userRoomRepositry.findByRoom(room);

        userRoomRepositry.delete(user_room.get());
    }

}
