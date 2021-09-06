package com.metastudy.Service;

import com.metastudy.Common.request.UserModifyReq;
import com.metastudy.Common.request.UserRegisterPostReq;
import com.metastudy.Entity.User;
import com.metastudy.Entity.User_Room;
import com.metastudy.Repository.UserRepository;
import com.metastudy.Repository.UserRepositorySup;
import com.metastudy.Repository.UserRoomRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRepositorySup userRepositorySup;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRoomRepositry userRoomRepositry;

    @Override
    public User getUserByUserId(String userId) {
        User user = userRepositorySup.findUserByUserId(userId).get();
        System.out.println(user);
        return user;
    }

    @Override
    public User createUser(UserRegisterPostReq userRegisterInfo) {
        User user = new User();
        user.setUserid(userRegisterInfo.getUserid());
        // 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
        user.setPassword(passwordEncoder.encode(userRegisterInfo.getPassword()));
        user.setUsername(userRegisterInfo.getUsername());

        return userRepository.save(user);
    }

    @Override
    public User modifyUser(UserModifyReq modifyinfo, String userId) {
        User userupdate = userRepository.findUserByUserid(userId).get();
        userupdate.setUserid(userId);
        userupdate.setUsername(modifyinfo.getUsername());
        userupdate.setPassword(passwordEncoder.encode(modifyinfo.getPassword()));

        return userRepository.save(userupdate);
    }

    @Override
    public void deleteUser(String userId) {
        User user = getUserByUserId(userId);

        userRepository.delete(user);
    }

    @Override
    public User_Room getUserByUserNickname(String usernickname) {
        User_Room user = userRepositorySup.findUserRoomByNickName(usernickname).get();
        System.out.println(user);
        return user;
    }
}
