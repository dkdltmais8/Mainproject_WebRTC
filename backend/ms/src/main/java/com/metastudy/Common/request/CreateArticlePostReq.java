package com.metastudy.Common.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@ApiModel("ArticleRegisterPostRequest")
public class CreateArticlePostReq {
    @ApiModelProperty(name="글 번호", example="1")
    Long id;
    @ApiModelProperty(name="글 제목", example="안녕하세요")
    String title;
    @ApiModelProperty(name="글 내용", example="반갑습니다")
    String content;
    @ApiModelProperty(name="방 번호", example="1")
    Long room_pk;
    @ApiModelProperty(name="작성자", example="1")
    Long user_pk;

}
