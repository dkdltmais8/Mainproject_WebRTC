package com.metastudy.Common.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserRoomPostRequest")
public class UserRoomPostReq {
    @ApiModelProperty(name="유저 네임", example="김기도")
    String username;
    @ApiModelProperty(name="유저 번호", example="1")
    Long userpk;
    @ApiModelProperty(name="방 번호", example="1")
    Long roompk;
    @ApiModelProperty(name="접속 상태", example="1")
    Boolean state;

    @Override
    public String toString() {
        return "UserRoomPostReq{" +
                "usernickname='" + username + '\'' +
                ", userpk=" + userpk +
                ", roompk=" + roompk +
                ", state=" + state +
                '}';
    }
}
