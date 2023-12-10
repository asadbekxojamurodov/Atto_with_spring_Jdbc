package code.uz.repository;

import code.uz.dto.Terminal;
import code.uz.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class TerminalRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;



    public List<Terminal> getAllTerminal() {
        String sql = "select * from terminal where visible = true";
        List<Terminal> terminalList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Terminal.class));
        return terminalList;
    }


    public void updateTerminal(Terminal terminal) {
        String sql = "update terminal set address = ? where code = '" + terminal.getCode() + "'";
        jdbcTemplate.update(sql, terminal.getAddress());
    }


    public boolean changeTerminalStatus(String code, Status status) {
        String sql = "update terminal set status = ? where code = '" + code + "'";
        return jdbcTemplate.update(sql, String.valueOf(Status.valueOf(status.name()))) != 0;
    }


    public boolean delete(Terminal terminal) {
        String sql = "update terminal set visible = false where code = '" + terminal.getCode() + "'";
        return jdbcTemplate.update(sql) != 0;
    }


    public Terminal getTerminalByCode(String code) {
        String sql = "select * from terminal where visible = true and code=?";
        List<Terminal> terminalList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Terminal.class), code);
        if (terminalList.size() > 0) {
            return terminalList.get(0);
        }
        return null;
    }


    public int adminCreateTerminal(Terminal terminal, boolean visible) {
        String sql = "insert into terminal(code,address,status,created_date,visible) values(?,?,?,?,?)";
      return   jdbcTemplate.update(sql,terminal.getCode(),terminal.getAddress(),terminal.getStatusTerminal().name()
                ,Timestamp.valueOf(terminal.getCreatedDate()),visible);

    }


    public int createDefaultTerminal() {
        String sql = "insert into terminal(code,address,status,created_date,visible) values(?,?,?,?,?)";
        return  jdbcTemplate.update(sql,"123","toshkent",String.valueOf(Status.ACTIVE),Timestamp.valueOf(LocalDateTime.now()),true);
    }


    public void makePayment(Double amount, String cardNumber) {
        String sql = "update card set balance = balance - ? where card_number = '" + cardNumber + "'";
         jdbcTemplate.update(sql,amount);
    }


    public void addBalanceToCompanyCard(String amount) {
        String sql = "update card set balance = balance + ? where card_number = '" + "5555" + "'";
        jdbcTemplate.update(sql,amount);
    }



}
