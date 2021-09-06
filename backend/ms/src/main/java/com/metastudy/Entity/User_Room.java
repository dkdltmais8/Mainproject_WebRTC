package com.metastudy.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User_Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String username;
    Boolean state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userpk")
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="roompk")
    Room room;

    LocalTime joinTime;
    long sumTime;

    @PrePersist
    public void createdAt()
    {
        this.joinTime = LocalTime.now();
        this.sumTime = 0;
    }

    @PreUpdate
    public void updatedAt()
    {
        this.joinTime = LocalTime.now();
    }

    @Override
    public String toString() {
        return "User_Room{" +
                "id=" + id +
                ", usernickname='" + username + '\'' +
                ", state=" + state +
                ", user=" + user +
                ", room=" + room +
                '}';
    }


}
