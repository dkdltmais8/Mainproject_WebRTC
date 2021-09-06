package com.metastudy.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Board implements Comparable<Board>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="roompk")
    Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userpk")
    User user;

    String title;
    String content;
    LocalDateTime created;
    LocalDateTime updated;
    int views;


    @PrePersist
    public void createdAt()
    {
        this.created = LocalDateTime.now();
        this.updated = LocalDateTime.now();
        this.views = 0;
    }

    @PreUpdate
    public void updatedAt()
    {
        this.updated = LocalDateTime.now();
    }

    @Override
    public int compareTo(Board board) {

        return (int)(this.id - board.id);
    }
}
