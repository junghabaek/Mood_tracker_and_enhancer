package com.moodTracker.config.auth;

import com.moodTracker.config.auth.dto.OAuthAttributes;
import com.moodTracker.config.auth.dto.SessionUser;
import com.moodTracker.domain.users.UserRepository;
import com.moodTracker.domain.users.Users;
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


@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest
                .getClientRegistration().getRegistrationId();

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        //위에서 가져온 registrationId, userNameAttributeName을 oAuth2User에서 받아온 attribute에 담는다
        // OAuthAttributes는 그냥 임시 저장소의 역할만 하므로 dto로 보는게 타당한것 같다.

        System.out.println(registrationId);
        System.out.println(userNameAttributeName);


        OAuthAttributes attributes = OAuthAttributes
                .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        Users user = saveOrUpdate(attributes);

        httpSession.setAttribute("user", new SessionUser(user));
        httpSession.setAttribute("user_id", user.getUser_id());

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(), attributes.getNameAttributeKey());

        //유저가 로그인했을때 이름이나 프로필 사진이 변경되었으면 그것으로 바꿔주는 역살을 하는 코드
        //그리고 세션에 유저를 저장시킨다.

    }


    private Users saveOrUpdate(OAuthAttributes attributes){
        Users user = userRepository.findByEmail(attributes.getEmail())
                .map(entity->entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity()); // 처음 가입할때는 개체가 없으니까!

        return userRepository.save(user);
    }


}
