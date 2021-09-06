package com.metastudy.Service;


import com.metastudy.Common.request.UserModifyReq;
import com.metastudy.Common.request.UserRegisterPostReq;
import com.metastudy.Entity.User;
import com.metastudy.Entity.User_Room;


public interface UserService {
    User getUserByUserId(String userId);

    User createUser(UserRegisterPostReq registerInfo);

    User modifyUser(UserModifyReq modifyinfo, String userId);

    void deleteUser(String userId);

    User_Room getUserByUserNickname(String usernickname);
}
