package com.metastudy.Generated;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.metastudy.Entity.User;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User>{
    private static final long serialVersionUID = 846542477L;

    public static final QUser user = new QUser("user");

    //inherited
    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath username = createString("username");

    public final StringPath usernickname = createString("usernickname");

    public final StringPath userid = createString("userid");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }
}
