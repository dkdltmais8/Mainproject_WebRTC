package com.metastudy.Controller;

import com.metastudy.Common.Response.BoardResponse;
import com.metastudy.Common.Response.UserResponse;
import com.metastudy.Common.request.CreateArticlePostReq;
import com.metastudy.Common.request.UpdateArticlePostReq;
import com.metastudy.Common.request.UserModifyReq;
import com.metastudy.Entity.Board;
import com.metastudy.Entity.FileDto;
import com.metastudy.Entity.User;
import com.metastudy.Service.BoardService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Api(value = "게시판 API", tags = {"Board"})
@RestController
@RequestMapping("/api/room")
@CrossOrigin(origins = "*")
public class BoardController {
    @Autowired
    BoardService boardService;

    @Autowired
    ServletContext servletContext;

    @PostMapping("/board")
    @ApiOperation(value = "게시글 작성", notes = "게시글을 작성한다")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })

    public ResponseEntity<? extends BoardResponse> register(
            @RequestBody @ApiParam(value="글 작성 정보", required = true) CreateArticlePostReq registerInfo) throws IOException {

        Long boardpk =  boardService.createArticle(registerInfo);

        return ResponseEntity.status(201).body(BoardResponse.of(201, "Success",boardpk));
    }

    @PutMapping("/board/{board_pk}")
    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정합니다. ")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
    })
    public ResponseEntity<? extends BoardResponse> updateBoard(@PathVariable("board_pk") Long boardpk, @RequestBody UpdateArticlePostReq updateinfo) {

        Board user = boardService.updateArticle(updateinfo,boardpk);

        return ResponseEntity.status(201).body(BoardResponse.of(201, "Success"));
    }

    @GetMapping("/{room_pk}/board")
    @ApiOperation(value = "게시글 리스트", notes = "게시글리스트를 반환합니다. ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public List<Board> SearchBoard(@PathVariable("room_pk") Long roompk)
    {
        List<Board> boardList = boardService.searchBoard(roompk);

        Collections.sort(boardList);
        return boardList;
    }

    @GetMapping("/board/{board_pk}")
    @ApiOperation(value = "게시글 상세조회", notes = "글번호로 게시글 상세조회. ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public Board FindSearchBoard(@PathVariable("board_pk") Long boardpk)
    {
        Board board = boardService.findsearchBoard(boardpk);

        return board;
    }

    @GetMapping("/filename/{board_pk}")
    @ApiOperation(value = "게시글 상세조회", notes = "게시글에 업로드된 파일 불러오기 ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public @ResponseBody List<FileDto> FileByBoard(@PathVariable("board_pk") Long boardpk)
    {
        List<FileDto> files = boardService.getFiles(boardpk);

        return files;
    }

    @PostMapping("/file/{boardpk}")
    @ApiOperation(value = "파일 업로드", notes = "게시글에 파일 업로드 ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<? extends BoardResponse> fileupload(@RequestParam("files") MultipartFile files,@PathVariable("boardpk") Long boardpk ) throws IOException {
        String realPath = servletContext.getRealPath("/upload");
        String today = new SimpleDateFormat("yyMMdd").format(new Date());
        String saveFolder = "C:/metastudy/"+today;

        System.out.printf("저장폴더 = "+saveFolder);
        File folder = new File(saveFolder);
        if (!folder.exists())
        {
            folder.mkdirs();
        }
        FileDto fileDto = new FileDto();
        String originalFileName = files.getOriginalFilename();
        if (!originalFileName.isEmpty()) {
            String saveFileName = UUID.randomUUID().toString()
                    + originalFileName.substring(originalFileName.lastIndexOf('.'));

            fileDto.setSaveFolder(today);
            fileDto.setOriginFile(originalFileName);
            fileDto.setSaveFile(saveFileName);
            files.transferTo(new File(folder, saveFileName));
        }

        boardService.fileupload(fileDto,boardpk);

        return ResponseEntity.status(201).body(BoardResponse.of(201, "Success"));
    }

    @DeleteMapping("/board/{board_pk}")
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제합니다. ")
    @ApiResponses({
            @ApiResponse(code = 204, message = "성공"),
    })
    public ResponseEntity<? extends BoardResponse> DeleteBoard(@PathVariable("board_pk") Long boardpk) {
        boardService.deleteUser(boardpk);

        return ResponseEntity.status(204).body(BoardResponse.of(204, "Success"));
    }
}