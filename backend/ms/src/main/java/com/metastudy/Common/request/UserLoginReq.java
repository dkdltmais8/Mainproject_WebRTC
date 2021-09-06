package com.metastudy.Common.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserLoginPostRequest")
public class UserLoginReq {
    @ApiModelProperty(name="유저 ID", example="ssafy_web")
    String userid;
    @ApiModelProperty(name="유저 Password", example="your_password")
    String password;
}
