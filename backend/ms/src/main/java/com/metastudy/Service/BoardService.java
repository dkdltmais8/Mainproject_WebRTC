package com.metastudy.Service;

import com.metastudy.Common.request.CreateArticlePostReq;
import com.metastudy.Common.request.UpdateArticlePostReq;
import com.metastudy.Entity.Board;
import com.metastudy.Entity.FileDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardService {
    Long createArticle(CreateArticlePostReq createArticleInfo);

    Board updateArticle(UpdateArticlePostReq updateinfo, Long boardpk);

    List<Board> searchBoard(Long roompk);

    Board findsearchBoard(Long boardpk);

    void deleteUser(Long boardpk);

    void fileupload(FileDto files, Long boardpk);

    List<FileDto> getFiles(Long boardpk);
}
