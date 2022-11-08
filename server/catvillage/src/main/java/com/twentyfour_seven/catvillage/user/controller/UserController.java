package com.twentyfour_seven.catvillage.user.controller;

import com.twentyfour_seven.catvillage.cat.entity.Cat;
import com.twentyfour_seven.catvillage.cat.service.CatService;
import com.twentyfour_seven.catvillage.dto.MultiResponseDto;
import com.twentyfour_seven.catvillage.user.dto.*;
import com.twentyfour_seven.catvillage.user.dto.follow.FollowerResponseDto;
import com.twentyfour_seven.catvillage.user.dto.follow.FollowingResponseDto;
import com.twentyfour_seven.catvillage.user.entity.Follow;
import com.twentyfour_seven.catvillage.user.entity.User;
import com.twentyfour_seven.catvillage.user.mapper.FollowMapper;
import com.twentyfour_seven.catvillage.user.mapper.UserMapper;
import com.twentyfour_seven.catvillage.user.service.FollowService;
import com.twentyfour_seven.catvillage.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

//@Tag(name = "Users", description = "유저 API")
@RestController
@RequestMapping("/users")
@Transactional
@Validated
public class UserController {
    private UserService userService;
    private UserMapper userMapper;
    private FollowService followService;
    private FollowMapper followMapper;
    private CatService catService;

    public UserController(UserService userService, UserMapper userMapper, FollowService followService, FollowMapper followMapper, CatService catService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.followService = followService;
        this.followMapper = followMapper;
        this.catService = catService;
    }

    //    @PostMapping
//    public ResponseEntity postUser(@Valid @RequestBody UserPostDto userPostDto) {
////        User user = userMapper.userPostDtoToUser(userPostDto);
////        User createUser = userService.createUser(user);
////        return new ResponseEntity<>(userMapper.userToUserPostResponseDto(createUser), HttpStatus.CREATED);
//    }

    @Operation(summary = "유저 이름(displayName) 중복 검사",
            description = "이미 등록되어 있는 이름일 경우 409 에러가 납니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "사용 가능한 이름"),
                    @ApiResponse(responseCode = "409", description = "이미 사용 중인 이름")
            })
    @GetMapping("/names")
    public ResponseEntity getNameCheck(@RequestParam String name) {
        userService.nameDuplicateCheck(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "유저 정보 가져오기(pagination)",
            description = "등록되어 있는 모든 유저 정보를 페이지화하여 반환합니다. 로그인되지 않은 사용자도 요청 가능합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "전체 유저 정보 조회 성공")
            })
    @GetMapping
    public ResponseEntity getUsers(@RequestParam @Positive int page,
                                   @RequestParam @Positive int size) {
        Page<User> users = userService.findUsers(page - 1, size);

        return new ResponseEntity<>(
                new MultiResponseDto<>(
                        userMapper.usersToUserGetResponseDtos(users.getContent()),
                        users
                ),
                HttpStatus.OK
        );
    }

    @Operation(summary = "유저 정보 불러오기",
            description = "로그인되지 않은 사용자도 요청 가능합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "유저 정보 조회 성공", content = @Content(schema = @Schema(implementation = UserGetResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "해당 유저를 찾을 수 없습니다.")
            })
    @GetMapping("/{user-id}")
    public ResponseEntity getUser(@PathVariable("user-id") Long userId) {
        User findUser = userService.findUser(userId);
        return new ResponseEntity<>(userMapper.userToUserGetResponseDto(findUser), HttpStatus.OK);
    }

    @Operation(summary = "유저 정보 변경",
            description = "로그인된 유저가 정보를 변경하려는 유저와 다른 경우 405 에러가 납니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "유저 정보 수정 성공", content = @Content(schema = @Schema(implementation = UserPatchResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "해당 유저를 찾을 수 없습니다."),
                    @ApiResponse(responseCode = "409", description = "이미 사용 중인 이름")
            })
    @PatchMapping
    public ResponseEntity patchUser(@Valid @RequestBody UserPatchDto requestBody,
                                    @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        User loginUser = userService.findVerifiedEmail(user.getUsername());
        User requestUser = userMapper.userPatchDtoToUser(requestBody);
        User updateUser = userService.updateUser(requestUser, loginUser);
        return new ResponseEntity<>(userMapper.userToUserPatchResponseDto(updateUser), HttpStatus.OK);
    }

    // TODO: 복구 기능 구현 필요
    @Operation(summary = "유저 탈퇴",
            description = "유저 정보가 바로 삭제되지 않고 7일동안 유지된 후 삭제됩니다. 그 이전에는 복구 가능합니다.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "유저 탈퇴 성공"),
                    @ApiResponse(responseCode = "404", description = "해당 유저를 찾을 수 없습니다.")
            })
    @DeleteMapping("/delete")
    public ResponseEntity deleteUser(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        userService.expiryUserByEmail(user.getUsername());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "로그인 후 유저 정보 가져오기,",
            description = "로그인 성공 응답 이후 바로 해당 요청을 보내서 기본 유저 정보를 가져옵니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "유저 정보 불러오기 성공",
                            content = @Content(schema = @Schema(implementation = UserMyInfoDto.class))),
                    @ApiResponse(responseCode = "404", description = "해당 유저를 찾을 수 없습니다.")
            }
    )
    @GetMapping("/my-info")
    public ResponseEntity getMyInfo(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        User findUser = userService.findVerifiedEmail(user.getUsername());
        return new ResponseEntity<>(userMapper.userToUserMyInfoDto(findUser), HttpStatus.OK);
    }

    @Operation(summary = "유저 팔로우",
            description = "유저를 팔로우 한다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "유저 팔로우 성공"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 유저"),
                    @ApiResponse(responseCode = "409", description = "이미 존재하는 팔로우")
            }
    )
    @PostMapping("/followers")
    public ResponseEntity<?> postFollowing(@RequestParam Long userId,
                                           @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        Follow follow = followService.addFollowing(user.getUsername(), userId);
        return new ResponseEntity<>(
                followMapper.followToFollowResponseDto(follow),
                HttpStatus.CREATED);
    }

    @Operation(summary = "유저 언팔로우",
            description = "유저를 언팔로우 한다.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "유저 언팔로우 성공"),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 유저\n존재하지 않는 팔로우")
            })
    @DeleteMapping("/followers")
    public ResponseEntity<?> deleteFollowing(@RequestParam Long userId,
                                             @AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        followService.deleteFollowing(user.getUsername(), userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "유저 팔로잉 조회",
            description = "유저의 팔로잉 목록을 조회한다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "유저 팔로잉 목록 조회 성공", content = @Content(schema = @Schema(implementation = FollowingResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 유저")
            })
    @GetMapping("/{users-id}/followings")
    public ResponseEntity<?> getFollowings(@PathVariable("users-id") Long userId) {
        return new ResponseEntity<>(
                new FollowingResponseDto(followMapper.followToFollowingGetResponseDto(userService.findUser(userId))),
                HttpStatus.OK);
    }

    @Operation(summary = "유저 팔로워 조회",
            description = "유저의 팔로워 목록을 조회한다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "유저 팔로워 목록 조회 성공", content = @Content(schema = @Schema(implementation = FollowerResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 유저")
            })
    @GetMapping("/{users-id}/followers")
    public ResponseEntity<?> getFollowers(@PathVariable("users-id") @Positive Long userId) {
        return new ResponseEntity<>(
                new FollowerResponseDto(followMapper.followToFollowerGetResponseDto(userService.findUser(userId))),
                HttpStatus.OK);
    }

    @Operation(summary = "로그인된 유저의 모든 고양이 프로필 조회", description = "냥이생활 피드 작성 시 프로필 선택 화면에 사용되는 API 입니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "고양이 프로필 조회 성공", content = @Content(schema = @Schema(implementation = UserCatResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "존재하지 않는 유저")
            }
    )
    @GetMapping("/cats")
    public ResponseEntity<?> getCats(@AuthenticationPrincipal org.springframework.security.core.userdetails.User user) {
        User findUser = userService.findVerifiedEmail(user.getUsername());
        List<Cat> catsByUser = catService.findCatsByUser(findUser.getUserId());
        return new ResponseEntity<>(userMapper.catsToUserCatResponseDtos(catsByUser), HttpStatus.OK);
    }
}
