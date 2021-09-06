package com.metastudy.Common.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("RoomRegisterPostRequest")
public class CreateRoomPostReq {
    @ApiModelProperty(name="방 번호", example="1")
    Long id;
    @ApiModelProperty(name="방 이름", example="안녕하세요")
    String roomname;
    @ApiModelProperty(name="핀 번호", example="123456")
    Long pin;
}
