package com.metastudy.Common.Response;


import com.metastudy.Entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserLoginPostResponse")
public class UserResponse {
    @ApiModelProperty(name="JWT 인증 토큰", example="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN...")
    String accessToken;

    @ApiModelProperty(name="응답 메시지", example = "정상")
    String message = null;
    @ApiModelProperty(name="응답 코드", example = "200")
    Integer statusCode = null;

    @ApiModelProperty(name="User PK")
    Long userPk;
    @ApiModelProperty(name="User ID")
    String userId;
    @ApiModelProperty(name="유저 이름", example = "김싸피")
    String username = null;
    @ApiModelProperty(name="유저 닉네임", example = "피싸")
    String usernickname = null;

    public static UserResponse of(Integer statusCode, String message, String accessToken) {
        UserResponse res = new UserResponse();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setAccessToken(accessToken);
        return res;
    }

    public static UserResponse of(Integer statusCode, String message, String userId,String username) {
        UserResponse res = new UserResponse();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setUserId(userId);
        res.setUsername(username);

        return res;
    }

    public static UserResponse of(Integer statusCode, String message) {
        UserResponse body = new UserResponse();
        body.message = message;
        body.statusCode = statusCode;
        return body;
    }

    public static UserResponse of(String username, String usernickname) {
        UserResponse body = new UserResponse();
        body.username = username;
        body.usernickname = usernickname;
        return body;
    }

    public static UserResponse of(User user) {
        UserResponse res = new UserResponse();
        res.setUserId(user.getUserid());
        res.setUserPk(user.getId());
        res.setUsername(user.getUsername());
        return res;
    }
}
