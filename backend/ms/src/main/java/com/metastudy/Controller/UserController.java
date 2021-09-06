package com.metastudy.Controller;


import com.metastudy.Common.Jwt.JwtTokenUtil;

import com.metastudy.Common.Jwt.SsafyUserDetails;
import com.metastudy.Common.Response.UserResponse;
import com.metastudy.Common.request.UserLoginReq;
import com.metastudy.Common.request.UserModifyReq;

import com.metastudy.Common.request.UserRegisterPostReq;
import com.metastudy.Entity.User;
import com.metastudy.Entity.User_Room;
import com.metastudy.Service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "<strong>아이디와 패스워드</strong>를 통해 로그인 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = UserResponse.class),
            @ApiResponse(code = 401, message = "인증 실패", response = UserResponse.class),
            @ApiResponse(code = 404, message = "사용자 없음", response = UserResponse.class),
            @ApiResponse(code = 500, message = "서버 오류", response = UserResponse.class)
    })
    public ResponseEntity<UserResponse> login(@RequestBody @ApiParam(value="로그인 정보", required = true) UserLoginReq loginInfo) {
        String userId = loginInfo.getUserid();
        String password = loginInfo.getPassword();

        User user = userService.getUserByUserId(userId);

        // 로그인 요청한 유저로부터 입력된 패스워드 와 디비에 저장된 유저의 암호화된 패스워드가 같은지 확인.(유효한 패스워드인지 여부 확인)
        if (user.getId() == null)
        {
            return ResponseEntity.status(404).body(UserResponse.of(401, "존재하지 않는 계정입니다.", null));
        }

        if(passwordEncoder.matches(password, user.getPassword())) {
            // 유효한 패스워드가 맞는 경우, 로그인 성공으로 응답.(액세스 토큰을 포함하여 응답값 전달)
            return ResponseEntity.ok(UserResponse.of(200, "Success", JwtTokenUtil.getToken(userId)));
        }
        // 유효하지 않는 패스워드인 경우, 로그인 실패로 응답.
        return ResponseEntity.status(401).body(UserResponse.of(401, "잘못된 비밀번호입니다.", null));
    }


    @PostMapping()
    @ApiOperation(value = "회원 가입", notes = "<strong>아이디와 패스워드</strong>를 통해 회원가입 한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends UserResponse> register(
            @RequestBody @ApiParam(value="회원가입 정보", required = true) UserRegisterPostReq registerInfo) {

        //임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
        User user = userService.createUser(registerInfo);

        return ResponseEntity.status(201).body(UserResponse.of(201, "Success",user.getUserid(),user.getUsername()));
    }

    @PutMapping("/{userId}")
    @ApiOperation(value = "회원정보 수정", notes = "회원정보를 수정합니다. ")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
    })
    public ResponseEntity<? extends UserResponse> modifyUser(@PathVariable("userId") String userId, @RequestBody UserModifyReq modifyinfo) {
        User user = userService.modifyUser(modifyinfo,userId);

        return ResponseEntity.status(201).body(UserResponse.of(201, "Success"));
    }

    @DeleteMapping("/{userId}")
    @ApiOperation(value = "회원정보 삭제", notes = "회원정보를 삭제합니다. ")
    @ApiResponses({
            @ApiResponse(code = 204, message = "성공"),
    })
    public ResponseEntity<? extends UserResponse> deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(204).body(UserResponse.of(204, "Success"));
    }

    @GetMapping("/id/{userId}")
    @ApiOperation(value = "아이디 중복검사", notes = "중복된 아이디를 검사합니다.")
    public ResponseEntity<? extends UserResponse> duplication(@PathVariable("userId") String userId) {
        User user = userService.getUserByUserId(userId);

        if (user.getId() == null)
        {
            return ResponseEntity.status(200).body(UserResponse.of(200, "가입이 가능합니다."));
        }
        else
            return ResponseEntity.status(401).body(UserResponse.of(401, "중복된 아이디입니다."));
    }

    @GetMapping("/nickname/{usernickname}")
    @ApiOperation(value = "닉네임 중복검사", notes = "중복된 닉네임을 검사합니다.")
    public ResponseEntity<? extends UserResponse> nicknameduplication(@PathVariable("usernickname") String usernickname) {
        User_Room user = userService.getUserByUserNickname(usernickname);

        if (user.getId() == null)
        {
            return ResponseEntity.status(200).body(UserResponse.of(200, "Success"));
        }
        else
            return ResponseEntity.status(200).body(UserResponse.of(200, "Fail"));
    }

    @GetMapping("/{userId}")
    @ApiOperation(value = "회원정보 조회", notes = "회원정보를 조회합니다.")
    public ResponseEntity<UserResponse> UserInfo(@PathVariable("userId") String userId) {
        User user = userService.getUserByUserId(userId);

        if (user.getId() == null)
        {
            return ResponseEntity.status(404).body(UserResponse.of(404, "존재하지 않는 아이디입니다."));
        }
        else {
            UserResponse res = new UserResponse();
            res.setUsername(user.getUsername());
            res.setMessage("성공");
            return ResponseEntity.status(200).body(res);
        }
    }

    @GetMapping("/me")
    @ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<UserResponse> getUserInfo(@ApiIgnore Authentication authentication) {
        /**
         * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
         * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
         */
        System.out.println("★★★");
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String userId = userDetails.getUsername();
        User user = userService.getUserByUserId(userId);

        return ResponseEntity.status(200).body(UserResponse.of(user));
    }
}
