package code.uz.db;



import code.uz.dto.Card;
import code.uz.dto.Profile;
import code.uz.enums.ProfileRole;
import code.uz.enums.Status;
import code.uz.repository.CardRepository;
import code.uz.repository.ProfileRepository;
import code.uz.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Component
public class InitDatabase {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private CardRepository cardRepository;

    public  void adminInit() {


        Profile profile = new Profile();
        profile.setName("Admin");                                //        name,
        profile.setSurname("Adminjon");                          //        surname,
        profile.setPhone("123");                                 //        phone,
        profile.setPassword(MD5Util.encode("123"));      //        password,
        profile.setCreatedDate(LocalDateTime.now());             //        created_date
        profile.setStatus(Status.ACTIVE);                        //        status,
        profile.setProfileRole(ProfileRole.ADMIN);               //        profile_role


        Profile profile1 = profileRepository.getProfileByPhone(profile.getPhone());
        if (profile1 != null) {
            return;
        }

        if (profileRepository.registration(profile) != 0) {
            System.out.println("Admin registered");
            return;
        }

        System.out.println("registered error");
    }

    public  void addCompanyCard() {
        Card card = new Card();

        card.setCardNumber("5555");
        card.setExpireDate(LocalDate.of(2025, 12, 01));
        card.setPhone("123");
        card.setBalance(0d);
        card.setCreatedDate(LocalDateTime.now());
        card.setStatus(Status.ACTIVE);

        Card exists = cardRepository.getCardByNumber(card.getCardNumber());

        if (exists != null) {
            return;
        }
        cardRepository.saveCompanyCard(card);
    }

}
