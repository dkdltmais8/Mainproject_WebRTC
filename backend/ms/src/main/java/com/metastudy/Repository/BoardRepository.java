package com.metastudy.Repository;

import com.metastudy.Common.request.CreateArticlePostReq;
import com.metastudy.Entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {

    @Override
    Optional<Board> findById(Long aLong);

    List<Board> findBoardByRoomId(Long id);
}
