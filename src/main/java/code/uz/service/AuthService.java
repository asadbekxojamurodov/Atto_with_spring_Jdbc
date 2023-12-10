package code.uz.service;

import code.uz.controller.AdminController;
import code.uz.controller.ProfileController;
import code.uz.dto.Profile;
import code.uz.enums.ProfileRole;
import code.uz.enums.Status;
import code.uz.repository.ProfileRepository;
import code.uz.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {
    @Autowired
    private AdminController adminController;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileController profileController;

    public AuthService() {
    }


    public void login(String phone, String password) {

        Profile profile = profileRepository.getProfileByPhoneAndPassword(phone, MD5Util.encode(password));

        if (profile == null) {
            System.out.println("Phone or Password incorrect");
            return;
        }

        if (!profile.getStatus().equals(Status.ACTIVE)) {
            System.out.println("You not allowed.MF");
            return;
        }

        if (profile.getProfileRole().equals(ProfileRole.ADMIN)) {

            adminController.start();

        } else if (profile.getProfileRole().equals(ProfileRole.USER)) {

            profileController.start(profile);

        } else {
            System.out.println("You don't have any role.");
        }

    }

    public void registration(Profile profile) {
        Profile exist = profileRepository.getProfileByPhone(profile.getPhone());
        if (exist != null) {
            System.out.println("profile exist");
            return;
        }
        profile.setName(profile.getName());
        profile.setSurname(profile.getSurname());
        profile.setPassword(MD5Util.encode(profile.getPassword()));
        profile.setPhone(profile.getPhone());
        profile.setStatus(Status.ACTIVE);
        profile.setProfileRole(ProfileRole.USER);
        profile.setCreatedDate(LocalDateTime.now());

        if (profileRepository.registration(profile) != 0) {
            System.out.println("successfully registered");
        }

        profileController.start(profile);

    }

//    public void setAdminController(AdminController adminController) {
//        this.adminController = adminController;
//    }
//
//    public void setProfileRepository(ProfileRepository profileRepository) {
//        this.profileRepository = profileRepository;
//    }
//
//    public void setProfileController(ProfileController profileController) {
//        this.profileController = profileController;
//    }

}
