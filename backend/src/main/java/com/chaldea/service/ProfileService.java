package com.chaldea.service;

import com.chaldea.dto.UpdateProfileRequest;
import com.chaldea.dto.UserProfileDTO;
import com.chaldea.exception.ResourceNotFoundException;
import com.chaldea.model.Servant;
import com.chaldea.model.User;
import com.chaldea.model.UserProfile;
import com.chaldea.repository.ServantRepository;
import com.chaldea.repository.UserProfileRepository;
import com.chaldea.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {

    private final UserProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final ServantRepository servantRepository;

    public ProfileService(UserProfileRepository profileRepository,
                          UserRepository userRepository,
                          ServantRepository servantRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.servantRepository = servantRepository;
    }

    public UserProfileDTO getProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User: " + username));

        UserProfile profile = profileRepository.findByUserIdWithAll(user.getId())
                .orElse(null);

        return toDTO(user, profile);
    }

    @Transactional
    public UserProfileDTO updateProfile(String username, UpdateProfileRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User: " + username));

        UserProfile profile = profileRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    UserProfile newProfile = new UserProfile();
                    newProfile.setUser(user);
                    return profileRepository.save(newProfile);
                });

        if (request.getBio() != null) {
            profile.setBio(request.getBio());
        }
        if (request.getMagicCircuitQuality() != null) {
            profile.setMagicCircuitQuality(request.getMagicCircuitQuality());
        }
        if (request.getFavoriteServantId() != null) {
            Servant servant = servantRepository.findById(request.getFavoriteServantId())
                    .orElseThrow(() -> new ResourceNotFoundException("Servant", request.getFavoriteServantId()));
            profile.setFavoriteServant(servant);
        }

        profileRepository.save(profile);
        return toDTO(user, profile);
    }

    @Transactional
    public void updateAvatar(String username, String avatarUrl) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User: " + username));

        UserProfile profile = profileRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    UserProfile newProfile = new UserProfile();
                    newProfile.setUser(user);
                    return profileRepository.save(newProfile);
                });

        profile.setAvatarUrl(avatarUrl);
        profileRepository.save(profile);
    }

    private UserProfileDTO toDTO(User user, UserProfile profile) {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setUserId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setDisplayName(user.getDisplayName());
        if (profile != null) {
            dto.setAvatarUrl(profile.getAvatarUrl());
            dto.setBio(profile.getBio());
            dto.setMagicCircuitQuality(profile.getMagicCircuitQuality());
            if (profile.getFavoriteServant() != null) {
                dto.setFavoriteServantId(profile.getFavoriteServant().getId());
                dto.setFavoriteServantName(profile.getFavoriteServant().getName());
            }
        }
        return dto;
    }
}
