package com.metastudy.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class FileDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String saveFolder;
    String originFile;
    String saveFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="boardpk")
    Board board;
}
