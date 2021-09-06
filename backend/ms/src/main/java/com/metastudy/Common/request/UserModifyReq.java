package com.metastudy.Common.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserModifyPostRequest")
public class UserModifyReq {
    @ApiModelProperty(name="유저 Password", example="your_password")
    String password;
    @ApiModelProperty(name="유저 Name", example="김기도")
    String username;
    @ApiModelProperty(name="유저 Nickname", example="ssafy")
    String usernickname;
}
