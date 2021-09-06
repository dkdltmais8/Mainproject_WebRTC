package com.metastudy.Common.Response;

import com.metastudy.Entity.Room;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("RoomResponse")
public class RoomResponse {

    @ApiModelProperty(name="응답 메시지", example = "정상")
    String message = null;
    @ApiModelProperty(name="응답 코드", example = "200")
    Integer statusCode = null;

    @ApiModelProperty(name="Room PK")
    Long roompk;
    @ApiModelProperty(name="User ID")
    String roomname;
    @ApiModelProperty(name="Room Pin")
    Long pin;

    public static RoomResponse of(Room room) {
        RoomResponse res = new RoomResponse();
        res.setRoompk(room.getId());
        res.setPin(room.getPin());
        res.setRoomname(room.getRoomname());

        return res;
    }

    public static RoomResponse of(Integer statusCode, String message) {
        RoomResponse body = new RoomResponse();
        body.message = message;
        body.statusCode = statusCode;
        return body;
    }

}
