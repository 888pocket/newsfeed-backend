package com.joosangah.ordersystem.follow.service;

import com.joosangah.ordersystem.follow.domain.Follow;
import com.joosangah.ordersystem.follow.repository.FollowRepository;
import com.joosangah.ordersystem.user.domain.entity.User;
import com.joosangah.ordersystem.user.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final UserService userService;

    private final FollowRepository followRepository;

    @Transactional
    public boolean toggleFollow(User user, String targetId) {
        User target = userService.loadUser(targetId);
        Optional<Follow> findFollow = followRepository.findByUserAndTargetUser(user, target);
        if (findFollow.isPresent()) {
            followRepository.delete(findFollow.get());
            return false;
        }

        followRepository.save(Follow.builder()
                .user(user)
                .targetUser(target).build());
        return true;
    }
}
