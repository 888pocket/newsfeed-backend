package com.joosangah.userservice.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.joosangah.userservice.auth.security.WebSecurityConfig;
import com.joosangah.userservice.file.service.FileService;
import com.joosangah.userservice.user.domain.dto.request.SignupRequest;
import com.joosangah.userservice.user.domain.dto.request.UserForm;
import com.joosangah.userservice.user.domain.entity.User;
import com.joosangah.userservice.user.repository.UserRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @MockBean
    private FileService fileService;

    @MockBean
    private UserRepository userRepository;

    @Mock
    private WebSecurityConfig webSecurityConfig;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 회원가입_테스트() {
        // given
        SignupRequest request = SignupRequest.builder()
                .email("test@example.com")
                .name("testUserName")
                .password("password123").build();

        when(webSecurityConfig.passwordEncoder()).thenReturn(passwordEncoder);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        // when
        userService.addUser(request);

        // then
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void 회원_로드_테스트_성공() {
        // given
        String userId = "testUserId";
        User user = User.builder().build();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // when
        User loadedUser = userService.loadUser(userId);

        // then
        assertThat(loadedUser).isNotNull();
        assertThat(loadedUser.getId()).isEqualTo(userId);
    }

    @Test
    public void 회원_로드_테스트_실패() {
        // given
        String userId = "testUserId";
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(NoSuchElementException.class, () -> userService.loadUser(userId));
    }

    @Test
    public void 회원_정보_수정_테스트() {
        // Given
        String userId = "testUserId";
        UserForm request = UserForm.builder()
                .name("testUserName").build();
        // 모킹된 파일
        MultipartFile profileImageFile = mock(MultipartFile.class);
        // 모킹된 사용자
        User mockUser = User.builder()
                .name("oldUserName")
                .profileImage("oldImageUrl").build();
        mockUser.setId(userId);

        // userRepository에서 사용자 로드하는 행위 모킹
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        // fileService의 이미지 업로드 행위 모킹
        when(fileService.uploadImage(any(MultipartFile.class), anyString())).thenReturn("newImageUrl");

        // When
        userService.modifyUser(userId, request, profileImageFile);

        // Then
        // 사용자가 요청대로 수정되었는지 확인
        assertEquals(request.getName(), mockUser.getUsername());
        assertEquals("newImageUrl", mockUser.getProfileImage());
        // userRepository의 save 메서드가 호출되었는지 확인
        verify(userRepository, times(1)).save(mockUser);
    }
}