package com.metastudy.Service;

import com.metastudy.Common.request.CreateArticlePostReq;
import com.metastudy.Common.request.UpdateArticlePostReq;
import com.metastudy.Entity.Board;
import com.metastudy.Entity.FileDto;
import com.metastudy.Entity.Room;
import com.metastudy.Entity.User;
import com.metastudy.Repository.BoardRepository;
import com.metastudy.Repository.FileRepository;
import com.metastudy.Repository.RoomRepository;
import com.metastudy.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service("boardService")
public class BoardServiceImpl implements BoardService{

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    UserRepository uesrRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    FileRepository fileRepository;

    @Override
    public Long createArticle(CreateArticlePostReq createArticleInfo) {
        Board board = new Board();
        Long roompk = createArticleInfo.getRoom_pk();
        Long userpk = createArticleInfo.getUser_pk();

        Optional<User> user = uesrRepository.findById(userpk);
        Optional<Room> room = roomRepository.findById(roompk);

        if (user.isPresent()) {
            board.setUser(user.get());
        }
        if (room.isPresent()){
            board.setRoom(room.get());
        }

        board.setTitle(createArticleInfo.getTitle());
        board.setContent(createArticleInfo.getContent());

        boardRepository.save(board);
        boardRepository.flush();
        System.out.println("가져왔나?");
        System.out.println(board.getId());

        return board.getId();
    }

    @Override
    public Board updateArticle(UpdateArticlePostReq updateinfo, Long boardpk) {

        Optional<Board> boardupdate = boardRepository.findById(boardpk);
        Board board = new Board();

        if (boardupdate.isPresent())
        {
            boardupdate.get().setContent(updateinfo.getContent());
            boardupdate.get().setTitle(updateinfo.getTitle());
        }

        return boardRepository.save(boardupdate.get());
    }

    @Override
    public List<Board> searchBoard(Long roompk) {
        return boardRepository.findBoardByRoomId(roompk);
    }

    @Override
    public Board findsearchBoard(Long boardpk) {
        Board board = boardRepository.findById(boardpk).get();

        int view = board.getViews();
        board.setViews(view+1);
        boardRepository.save(board);

        return board;
    }

    @Override
    public void deleteUser(Long boardpk) {
        Board board = boardRepository.findById(boardpk).get();
        boardRepository.delete(board);
    }

    @Override
    public void fileupload(FileDto files, Long boardpk) {
        Optional<Board> board = boardRepository.findById(boardpk);

        if(fileRepository.findByBoard(board.get()).isPresent())
        {
            System.out.println("수정");
            Optional<FileDto> update = fileRepository.findByBoard(board.get());
            FileDto fileupdate = update.get();
            fileupdate.setOriginFile(files.getOriginFile());
            fileupdate.setSaveFolder(files.getSaveFolder());
            fileupdate.setSaveFile(files.getSaveFile());

            fileRepository.save(fileupdate);
        }
        else
        {
            System.out.println("추가");
            files.setBoard(board.get());
            fileRepository.save(files);
        }


    }

    @Override
    public List<FileDto> getFiles(Long boardpk) {
        Optional<Board> board = boardRepository.findById(boardpk);


        return fileRepository.findFileDtoByBoard(board.get());
    }

}
