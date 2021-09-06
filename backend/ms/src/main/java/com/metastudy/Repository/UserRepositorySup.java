package com.metastudy.Repository;

import com.metastudy.Entity.QUser_Room;
import com.metastudy.Entity.Room;
import com.metastudy.Entity.User;
import com.metastudy.Entity.User_Room;
import com.metastudy.Generated.QRoom;
import com.metastudy.Generated.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositorySup {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QUser qUser = QUser.user;


    QUser_Room qUser_room = QUser_Room.user_Room;
    QRoom qRoom = QRoom.room;

    public Optional<User> findUserByUserId(String userId) {
        User user = jpaQueryFactory.select(qUser).from(qUser)
                .where(qUser.userid.eq(userId)).fetchOne();
        if(user == null) return Optional.ofNullable(new User());
        return Optional.ofNullable(user);
    }

    public Optional<User_Room> findUserRoomByNickName(String nickname) {
        User_Room user = jpaQueryFactory.select(qUser_room).from(qUser_room)
                .where(qUser_room.username.eq(nickname)).fetchOne();

        if(user == null) return Optional.ofNullable(new User_Room());
        return Optional.ofNullable(user);
    }

    public Optional<Room> findRoomByRoomName(String roomname)
    {
        Room room = jpaQueryFactory.select(qRoom).from(qRoom)
                .where(qRoom.roomname.eq(roomname)).fetchOne();

        if(room == null) return Optional.ofNullable(new Room());
        return Optional.ofNullable(room);

    }
}
