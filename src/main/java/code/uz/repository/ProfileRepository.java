package code.uz.repository;

import code.uz.dto.Profile;
import code.uz.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ProfileRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;



    public Integer registration(Profile profile) {
        String sql = "insert into profile(name,surname,phone,password,created_date,status,profile_role) values(?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql, profile.getName(), profile.getSurname(), profile.getPhone(), profile.getPassword(),
                Timestamp.valueOf(LocalDateTime.now()), profile.getStatus().name(), profile.getProfileRole().name());
    }


    public List<Profile> getAllProfile() {
        String sql = "select * from profile";
        List<Profile> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Profile.class));
        return list;
    }


    public void changeProfileStatus(Profile profile, Status status) {
        String sql = "update profile set status = ? where phone = '" + profile.getPhone() + "'";
        jdbcTemplate.update(sql, status.name());
    }


    public Profile getProfileByPhoneAndPassword(String phone, String password) {
        String sql = "Select  * from Profile where phone= '" + phone + "' and password = '" + password + "' ";
        List<Profile> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Profile.class));
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }


    public Profile getProfileByPhone(String phone) {
        String sql = "select * from profile where phone = '" + phone + "'";
        List<Profile> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Profile.class));
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }


}
