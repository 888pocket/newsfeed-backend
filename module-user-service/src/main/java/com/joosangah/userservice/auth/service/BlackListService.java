package com.joosangah.userservice.auth.service;

import com.joosangah.userservice.auth.domain.entity.BlackList;
import com.joosangah.userservice.auth.repository.BlackListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BlackListService {

    private final BlackListRepository blackListRepository;

    public void addBlacklist(String userId) {
        BlackList newBlackList = BlackList.builder()
                .userId(userId).build();
        blackListRepository.save(newBlackList);
    }

    public boolean isInBlacklist(String userId) {
        return blackListRepository.findByUserId(userId).isPresent();
    }

    @Transactional
    public void deleteBlacklist(String userId) {
        blackListRepository.deleteByUserId(userId);
    }
}
