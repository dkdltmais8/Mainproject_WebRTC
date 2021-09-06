package com.metastudy.Generated;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.metastudy.Entity.Room;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;

@Generated("com.querydsl.codegen.EntitySerializer")
public class QRoom extends EntityPathBase<Room>{
    private static final long serialVersionUID = 846542477L;

    public static final QRoom room = new QRoom("room");

    //inherited
    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> userpk = createNumber("userpk", Long.class);

    public final StringPath roomname = createString("roomname");

    public final StringPath pin = createString("pin");


    public QRoom(String variable) {
        super(Room.class, forVariable(variable));
    }

    public QRoom(Path<? extends Room> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRoom(PathMetadata metadata) {
        super(Room.class, metadata);
    }


}
