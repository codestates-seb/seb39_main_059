package com.twentyfour_seven.catvillage.security;

import com.twentyfour_seven.catvillage.exception.BusinessLogicException;
import com.twentyfour_seven.catvillage.exception.ExceptionCode;
import com.twentyfour_seven.catvillage.user.dto.SessionUser;
import com.twentyfour_seven.catvillage.user.entity.User;
import com.twentyfour_seven.catvillage.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // OAuth2 Service ID (google, kakao, naver)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // OAuth2 로그인 진행 시 키가 되는 필드 값(PK)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // Test code
//        System.out.println(oAuth2User.getAttributes().toString());

        // OAuth2UserService
        OAuthAttributes authAttributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        User user = saveOfUpdate(authAttributes);
//        httpSession.setAttribute("user", new SessionUser(user));    // 직렬화된 dto 클래스 사용

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole())),
                authAttributes.getAttributes(),
                authAttributes.getNameAttributeKey());
    }

    private User saveOfUpdate(OAuthAttributes authAttributes) {
        // name은 유니크 값이기 때문에 중복인 경우 뒤에 추가로 숫자를 붙혀서 생성
        // 이미 가입된 경우도 존재하기 때문에 email 동일 여부 확인 후 진행
        String name = authAttributes.getName();
        Optional<User> findUser = userRepository.findByName(name);
        long count = 0;
        while(findUser.isPresent()) {
            if(!findUser.get().getEmail().equals(authAttributes.getEmail())) {
                findUser = userRepository.findByName(name + ++count);
            } else {
                break;
            }
        }
        // 중복값이 없다면 패스
        if(count > 0) {
            name = authAttributes.getName() + count;
        }

        // 비밀번호가 있다면 일반 회원 가입 한 유저이므로 예외 처리
        if(findUser.isPresent()) {
            if(findUser.get().getPassword() != null) {
                throw new BusinessLogicException(ExceptionCode.MEMBER_EMAIL_EXISTS);
            }
        }

        String finalName = name;
        User user = userRepository.findByEmail(authAttributes.getEmail())
                .map(entry -> { // 계정이 존재 한다면, 이름과 프로필 이미지를 업데이트
                    entry.setName(finalName);
                    entry.setProfileImage(authAttributes.getPicture());
                    return entry;
                }).orElse(authAttributes.toEntity());
        return userRepository.save(user);
    }
}
