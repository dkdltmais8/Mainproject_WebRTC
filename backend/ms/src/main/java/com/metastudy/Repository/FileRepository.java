package com.metastudy.Repository;

import com.metastudy.Entity.Board;
import com.metastudy.Entity.FileDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileDto, Long> {

    List<FileDto> findFileDtoByBoard(Board board);

    Optional<FileDto> findByBoard(Board board);

}
