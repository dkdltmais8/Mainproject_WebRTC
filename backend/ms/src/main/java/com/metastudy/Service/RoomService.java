package com.metastudy.Service;

import com.metastudy.Common.Response.TimeResponse;
import com.metastudy.Common.request.CreateRoomPostReq;
import com.metastudy.Common.request.UserRoomPostReq;
import com.metastudy.Entity.Room;
import com.metastudy.Entity.User_Room;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface RoomService {

    Room createRoom(CreateRoomPostReq registerInfo);

    Room roomNumber(String roomname);

    void roomJoin(UserRoomPostReq userroom);

    void leavRoom(Long roompk, Long userpk);

    void deleteRoom(Long roompk);

    List<User_Room> getUserRoomList(Long roompk);

    List<TimeResponse> getTime(Long roompk);

    Room getRoomByRoomname(String roomname);
}
