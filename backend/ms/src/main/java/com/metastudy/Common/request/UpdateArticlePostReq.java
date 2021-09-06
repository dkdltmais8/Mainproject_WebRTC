package com.metastudy.Common.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ArticleUpdatePostRequest")
public class UpdateArticlePostReq {
    @ApiModelProperty(name="글 제목", example="안녕하세요")
    String title;
    @ApiModelProperty(name="글 내용", example="반갑습니다")
    String content;
}
