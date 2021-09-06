package com.metastudy.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String roomname;
    Long pin;

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomname='" + roomname + '\'' +
                ", pin=" + pin +
                '}';
    }
}
