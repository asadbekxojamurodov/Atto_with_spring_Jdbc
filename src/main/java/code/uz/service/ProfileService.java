package code.uz.service;

import code.uz.dto.Profile;
import code.uz.enums.Status;
import code.uz.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public void getAllProfileList() {
        List<Profile> profileDtoList = profileRepository.getAllProfile();
        if (profileDtoList.isEmpty()) {
            System.out.println("profile list is empty!!!");
            return;
        }
        System.out.println("\t\t\t\t\t\t\t\t\t\t ********** Profile list **********\n");
        for (Profile profileDto : profileDtoList) {
            System.out.println(profileDto);
        }
        System.out.println();
    }


    public void changeProfileStatus(String phone) {
        Profile profile = profileRepository.getProfileByPhone(phone);
        if (profile == null) {
            System.out.println("profile not found");
            return;
        }

        if (profile.getStatus().equals(Status.ACTIVE)) {
            profileRepository.changeProfileStatus(profile, Status.BLOCKED);

        } else if (profile.getStatus().equals(Status.BLOCKED)) {
            profileRepository.changeProfileStatus(profile, Status.ACTIVE);
        }
    }

//    public void setProfileRepository(ProfileRepository profileRepository) {
//        this.profileRepository = profileRepository;
//    }
}


