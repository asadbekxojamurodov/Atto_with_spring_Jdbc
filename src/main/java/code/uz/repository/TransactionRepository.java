package code.uz.repository;

import code.uz.dto.Card;
import code.uz.dto.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
@Repository
public class TransactionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;



    public int createTransaction(Transaction transaction) {
        String sql = "insert into transaction(card_id,card_number,terminal_code,amount,transaction_type,created_date,phone) values(?,?,?,?,?,?,?)";
        if (transaction.getTerminalCode() != null) {
            return  jdbcTemplate.update(sql, transaction.getCardId(),transaction.getCardNumber(),transaction.getTerminalCode(),
                    transaction.getAmount(),transaction.getTransactionType().name(),Timestamp.valueOf(transaction.getCreatedDate()),transaction.getPhone());
        } else {
            return  jdbcTemplate.update(sql, transaction.getCardId(),transaction.getCardNumber(),null,
                    transaction.getAmount(),transaction.getTransactionType().name(),Timestamp.valueOf(transaction.getCreatedDate()),transaction.getPhone());
        }
    }


    public List<Transaction> getAllTransaction() {
        String sql = "select * from transaction";
        List<Transaction> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Transaction.class));
        return list;
    }


    public List<Transaction> userTransaction(String phone) {
        String sql = "select * from transaction where phone = '" + phone + "'";
        List<Transaction> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Transaction.class));
        return list;
    }


    public Transaction isExistTransaction() {
        String sql = "select * from transaction";
        List<Transaction> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Transaction.class));
        if (list.size()>0){
            return list.get(0);
        }
        return null;
    }


    public List<Transaction> isExistTransactionCheckByAdmin() {
        String sql = "select * from transaction";
        List<Transaction> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Transaction.class));
       return list;
    }


    public Card getCompanyCardBalance() {
        String sql = "select * from card where card_number = '" + "5555" + "'";
        List<Card> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Card.class));
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }


    public List<Transaction> getTransactionByTerminalCode(String code) {
        String sql = "select * from transaction where terminal_code = '" + code + "'";
        List<Transaction> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Transaction.class));
        return list;
    }


    public List<Transaction> getTransactionByCard(String cardNumber) {
        String sql = "select * from transaction where card_number = '" + cardNumber + "'";
        List<Transaction> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Transaction.class));
        return list;
    }


    public  List<Transaction> getPaymentInToday() {
        String sql = "select * from transaction where created_date::date = date '"+LocalDate.now()+"' ; ";
        List<Transaction> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Transaction.class));
        return list;
    }


    public List<Transaction> getDailyPayments(String date) {
        String sql = "select * from transaction where created_date::date =  '"+ date +"'";
        List<Transaction> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Transaction.class));
        return list;
    }


    public List<Transaction> getPaymentFromTo(String startDate, String finishDate)  {
        String sql = "select * from transaction where created_date::date >= date '"+startDate+"' or created_date::date <= date '"+finishDate+"'";
        List<Transaction> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Transaction.class));
        return list;
    }

}
