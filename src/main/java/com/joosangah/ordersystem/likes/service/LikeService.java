package com.joosangah.ordersystem.likes.service;

import com.joosangah.ordersystem.likes.domain.entity.Like;
import com.joosangah.ordersystem.likes.domain.enums.LikeType;
import com.joosangah.ordersystem.likes.repository.LikeRepository;
import com.joosangah.ordersystem.user.domain.entity.User;
import com.joosangah.ordersystem.user.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final UserService userService;

    private final LikeRepository likeRepository;

    @Transactional
    public boolean toggleLike(User user, String targetId, LikeType type) {
        User targetUser = userService.loadUser(targetId);
        Optional<Like> findLike = likeRepository.findByUserAndTargetUserAndType(user, targetUser,
                type);
        if (findLike.isPresent()) {
            likeRepository.delete(findLike.get());
            return false;
        }

        likeRepository.save(Like.builder().user(user).targetUser(targetUser).type(type).build());
        return true;
    }
}
