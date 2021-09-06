package com.metastudy.Controller;

import com.metastudy.Common.Response.BoardResponse;
import com.metastudy.Common.Response.RoomResponse;
import com.metastudy.Common.Response.TimeResponse;
import com.metastudy.Common.Response.UserResponse;
import com.metastudy.Common.request.CreateArticlePostReq;
import com.metastudy.Common.request.CreateRoomPostReq;
import com.metastudy.Common.request.UserRoomPostReq;
import com.metastudy.Entity.Board;
import com.metastudy.Entity.Room;
import com.metastudy.Entity.User_Room;
import com.metastudy.Repository.UserRoomRepositry;
import com.metastudy.Service.RoomService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Api(value = "방 API", tags = {"Room"})
@Controller
@RequestMapping("/api/room")
@CrossOrigin(origins = "*")
public class RoomController {

    @Autowired
    RoomService roomService;

    @PostMapping()
    @ApiOperation(value = "방 생성", notes = "방을 생성한다")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BoardResponse> register(
             @ApiParam(value="방 생성 정보", required = true) CreateRoomPostReq registerInfo) {

        Room room = roomService.createRoom(registerInfo);
        return ResponseEntity.status(201).body(BoardResponse.of(201, "Success"));
    }

    @GetMapping("/roomNumber/{roomname}")
    @ApiOperation(value = "방 번호", notes = "선택한 방의 이름으로 roompk를 받아온다")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends RoomResponse> roomNumber(
            @PathVariable("roomname") String roomname) {
        System.out.println(roomname);
        Room room = roomService.roomNumber(roomname);

        return ResponseEntity.status(201).body(RoomResponse.of(room));
    }


    @PostMapping("/join")
    @ApiOperation(value = "방 접속", notes = "선택한 방에 접속한다")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 w"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends RoomResponse> RoomJoin(UserRoomPostReq userroom)
    {
        System.out.println(userroom.toString());

        roomService.roomJoin(userroom);

        return ResponseEntity.status(201).body(RoomResponse.of(201, "Success"));
    }

    @PutMapping("/leaveroom/{roompk}/{userpk}")
    @ApiOperation(value = "방 번호", notes = "선택한 방을 나간다")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BoardResponse> leaveRoom(
            @PathVariable("roompk") Long roompk, @PathVariable("userpk") Long userpk) {

        roomService.leavRoom(roompk,userpk);

        return ResponseEntity.status(201).body(BoardResponse.of(201, "Success"));
    }

    @DeleteMapping ("/{roompk}")
    @ApiOperation(value = "방 번호", notes = "선택한 방 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BoardResponse> deleteRoom(
            @PathVariable("roompk") Long roompk) {

        roomService.deleteRoom(roompk);

        return ResponseEntity.status(201).body(BoardResponse.of(201, "Success"));
    }

    @GetMapping("/time/{roompk}")
    @ApiOperation(value = "방 번호", notes = "선택한 방에 참여한 인원의 접속시간을 가져온다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public @ResponseBody List<TimeResponse> getTime(
            @PathVariable("roompk") Long roompk) {


        List<TimeResponse> result = roomService.getTime(roompk);
        Collections.sort(result);
        return result;
    }
    @GetMapping("/roomname/{roomname}")
    @ApiOperation(value = "방 이름 중복검사", notes = "중복된 방 이름을 검사합니다.")
    public ResponseEntity<? extends UserResponse> roomnameduplication(@PathVariable("roomname") String roomname) {

        Room room = roomService.getRoomByRoomname(roomname);

        if (room.getId() == null)
        {
            return ResponseEntity.status(200).body(UserResponse.of(200, "Success"));
        }
        else
            return ResponseEntity.status(200).body(UserResponse.of(200, "Fail"));
    }
}
