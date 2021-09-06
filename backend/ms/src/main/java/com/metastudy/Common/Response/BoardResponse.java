package com.metastudy.Common.Response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("BoardResponse")
public class BoardResponse {

    @ApiModelProperty(name="응답 메시지", example = "정상")
    String message = null;
    @ApiModelProperty(name="응답 코드", example = "200")
    Integer statusCode = null;

    Long board;

    public static BoardResponse of(Integer statusCode, String message) {
        BoardResponse body = new BoardResponse();
        body.message = message;
        body.statusCode = statusCode;
        return body;
    }

    public static BoardResponse of(Integer statusCode, String message,Long boardpk) {
        BoardResponse body = new BoardResponse();
        body.message = message;
        body.statusCode = statusCode;
        body.board = boardpk;

        return body;
    }



}
