package code.uz.repository;

import code.uz.dto.Card;
import code.uz.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class CardRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;



    public void createCard(Card card) {
        String sql = "insert into card(card_number,expire_date,balance,status,created_date) values(?,?,?,?,?)";
        jdbcTemplate.update(sql, card.getCardNumber(), Date.valueOf(card.getExpireDate()), card.getBalance(), card.getStatus().name(), Timestamp.valueOf(card.getCreatedDate()));
    }


    public List<Card> getAllNoActiveCardList() {
        String sql = "select * from card where status = '" + "NO_ACTIVE" + "'";
        List<Card> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Card.class));
        return list;
    }


    public List<Card> getAllCard() {
        String sql = "select * from card";
        List<Card> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Card.class));
        return list;
    }


    public List<Card> getAllMyCard(String phone) {
        String sql = "select * from card where visible = true and phone = '" + phone + "'";
        List<Card> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Card.class));
        return list;
    }


    public Card getCardByNumber(String cardNumber) {
        String sql = "select * from card where card_number = '" + cardNumber + "'";
        List<Card> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Card.class));
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }


    public Boolean addCard(Card card) {
        String sql = "update card set phone = ?,status = ? where card_number = '" + card.getCardNumber() + "'";
        return jdbcTemplate.update(sql, card.getPhone(), card.getStatus().name()) != 0;
    }


    public boolean changeCardStatus(Card cardDto) {
        return updateStatus(cardDto);
    }


    public boolean updateStatus(Card card) {
        String sql = "update card set status = ? where card_number = ?";
        return jdbcTemplate.update(sql, card.getStatus().name(), card.getCardNumber()) != 0;
    }


    public void refill(String cardNumber, double balance) {
        String sql = "update card set balance = balance + ? where card_number = '" + cardNumber + "'";
        jdbcTemplate.update(sql, balance);
    }


    public int AdminUpdateCard(Card card) {
        String sql = "update card set  expire_date = ? where card_number = '" + card.getCardNumber() + "'";
        return jdbcTemplate.update(sql, Date.valueOf(card.getExpireDate()));
    }


    public int updateCardStatus(String cardNumber, Status status) {
        String sql = "update card set  status = ? where card_number = '" + cardNumber + "'";
        return jdbcTemplate.update(sql, String.valueOf(status));
    }


    public int adminDeleteCard(String cardNumber) {
        String sql = "update card set  visible = ? where card_number = '" + cardNumber + "'";
        return jdbcTemplate.update(sql, false);
    }


    public void profileDeleteCard(Card exist) {
        String sql = "update card set  visible = ? where card_number = '" + exist.getCardNumber() + "'";
        jdbcTemplate.update(sql, false);
    }


    public void saveCompanyCard(Card card) {
        String sql = "insert into card(card_number,expire_date,balance,status,created_date,phone) values(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, card.getCardNumber(), Date.valueOf(card.getExpireDate()), card.getBalance(), card.getStatus().name(), Timestamp.valueOf(card.getCreatedDate()), card.getPhone());
    }


    public Card getMyBalanceByCardNumber(String cardNumber) {
        String sql = "select * from card where visible = true and card_number = '" + cardNumber + "';";
        List<Card> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Card.class));
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

}