package code.uz.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
@Component
public class DatabaseUtil {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createProfileTable() {
        String sql ="  create table if not exists profile(" +
                "        id serial ," +
                "        name varchar not null," +
                "        surname varchar not null," +
                "        phone varchar(13) primary key," +
                "        password varchar not null," +
                "        created_date timestamp default now()," +
                "        status varchar default  'ACTIVE'," +
                "        profile_role varchar not null )";
        jdbcTemplate.execute(sql);
    }

    public void createCardTable() {
        String sql = "   create table if not exists card(" +
                "        id serial ," +
                "        card_number varchar(16) primary key," +
                "        expire_date date," +
                "        balance double precision," +
                "        status varchar default  'NO_ACTIVE'," +
                "        phone varchar(13) references profile (phone)," +
                "        visible boolean default true, " +
                "        created_date timestamp default now()" +
                "      )";
        jdbcTemplate.execute(sql);
    }


    public void createTerminalTable() {
        String sql = "   create table if not exists terminal(" +
                    "        id serial," +
                    "        code varchar primary key," +
                    "        address varchar not null," +
                    "        status varchar default  'ACTIVE'," +
                    "        visible boolean default true, " +
                    "        created_date timestamp default now()" +
                    "      );";
        jdbcTemplate.execute(sql);
    }


    public void createTransactionTable() {
        String sql = "   create table if not exists transaction(" +
                "        id serial," +
                "        card_id int not null," +
                "        phone varchar not null," +
                "        card_number varchar(16)  references card(card_number)," +
                "        terminal_code varchar references terminal(code)," +
                "        amount double precision, " +
                "        transaction_type varchar not null, " +
                "        created_date timestamp default now()" +
                "      );";
        jdbcTemplate.execute(sql);
    }


}