package code.uz.service;

import code.uz.dto.Card;
import code.uz.dto.Profile;
import code.uz.dto.Transaction;
import code.uz.enums.TransactionType;
import code.uz.repository.CardRepository;
import code.uz.repository.TerminalRepository;
import code.uz.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TerminalRepository terminalRepository;
    @Autowired
    private CardRepository cardRepository;


    public void transactionList() {
        List<Transaction> allTransaction = transactionRepository.getAllTransaction();
        if (allTransaction.isEmpty()) {
            System.out.println("Transaction list is empty!!!\n");
            return;
        }

        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t ********** Transaction list **********\n");
        for (Transaction transaction : allTransaction) {
            System.out.println(transaction);
        }
        System.out.println();
    }

    public void companyCardBalance() {
        Card companyCardBalance = transactionRepository.getCompanyCardBalance();
        System.out.println("\t\t\t\t\t\t\t\t\t\t ********** Company card balance *********\n ");
        System.out.println(companyCardBalance + "\n");

    }

    public void createTransaction(Integer cardId, String cardNumber, String terminalCode, double balance, TransactionType type, String phone) {

        Transaction transaction = new Transaction();
        transaction.setCardId(cardId);
        transaction.setCardNumber(cardNumber);
        transaction.setTerminalCode(terminalCode);
        transaction.setAmount(balance);
        transaction.setTransactionType(type);
        transaction.setCreatedDate(LocalDateTime.now());
        transaction.setPhone(phone);

        if (transactionRepository.createTransaction(transaction) != 0) {
            System.out.println("balance successfully filled ");

        }
    }

    public void getUserTransactionList(Profile profile) {

        List<Transaction> transactionDtoList = transactionRepository.userTransaction(profile.getPhone());
        if (transactionDtoList.isEmpty()) {
            System.out.println("you don't have transaction ");
            return;
        }
        System.out.println("\t\t\t\t\t\t\t\t**************** Transaction ****************");
        for (Transaction transaction : transactionDtoList) {
            System.out.println(transaction);
        }
    }

    public void isExistTransaction() {
        Transaction exist = transactionRepository.isExistTransaction();
        if (exist == null) {
            terminalRepository.createDefaultTerminal(); //create terminal code=123 address = toshkent
            System.out.println("terminal code 123");
        }
    }

    public void makePayment(String amount, String cardNumber, String terminalCode) {
        Card cardBalance = cardRepository.getMyBalanceByCardNumber(cardNumber);
        if (cardBalance == null) {
            System.out.println("card not fount");
            return;
        }

        double balance = Double.parseDouble(amount);
        if (cardBalance.getBalance() < balance) {
            System.out.println("you balance is not enough to payment");
            return;
        }

        Card card = cardRepository.getCardByNumber(cardNumber);

        Transaction transaction = new Transaction();
        transaction.setCardId(card.getId());
        transaction.setCardNumber(card.getCardNumber());
        transaction.setTerminalCode(terminalCode);
        transaction.setAmount(Double.valueOf(amount));
        transaction.setTransactionType(TransactionType.PAYMENT);
        transaction.setCreatedDate(LocalDateTime.now());
        transaction.setPhone(card.getPhone());

        terminalRepository.makePayment(balance, cardNumber);

        terminalRepository.addBalanceToCompanyCard(amount);

        transactionRepository.createTransaction(transaction);

    }

    public void getTransactionByTerminalCode(String code) {
        List<Transaction> transactionList = transactionRepository.isExistTransactionCheckByAdmin();
        if (transactionList.isEmpty()) {
            System.out.println("\nTransaction is not available\n");
            return;
        }

        List<Transaction> transaction = transactionRepository.getTransactionByTerminalCode(code);
        System.out.println("\t\t\t\t\t\t\t\t\t\t ********** Transaction list **********\n");
        for (Transaction transactionDto : transaction) {
            System.out.println(transactionDto);
        }
        System.out.println();

    }

    public void getTransactionByCard(String cardNumber) {
        List<Transaction> transactionlist = transactionRepository.getTransactionByCard(cardNumber);
        if (transactionlist.isEmpty()) {
            System.out.println("\nTransaction is not available by this card\n");
            return;
        }
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t********** Transaction list **********");
        for (Transaction transactionDto : transactionlist) {
            System.out.println(transactionDto);
        }
        System.out.println();
    }

    public void paymentInToday() {
        List<Transaction> paymentInToday = transactionRepository.getPaymentInToday();
        if (paymentInToday.isEmpty()) {
            System.out.println("\nNo today's transaction!!!\n");
            return;
        }

        System.out.println("\t\t\t\t\t\t\t\t\t\t ******* Today's Transaction list *******\n");
        for (Transaction transactionDto : paymentInToday) {
            System.out.println(transactionDto);
        }
        System.out.println();
    }

    public void dailyPayments(String date) {
        List<Transaction> dailyPayments = transactionRepository.getDailyPayments(date);
        if (dailyPayments.isEmpty()) {
            System.out.println("\nNo transaction for this day!!!\n");
            return;
        }

        System.out.println("\t\t\t\t\t\t\t\t\t\t ******* Transaction list for this day *******\n");
        for (Transaction transactionDto : dailyPayments) {
            System.out.println(transactionDto);
        }
        System.out.println();
    }

    public void getPaymentFromTo(String startDate, String finishDate) {
        List<Transaction> paymentFromTo = transactionRepository.getPaymentFromTo(startDate, finishDate);
        if (paymentFromTo.isEmpty()) {
            System.out.println("\nNo transaction in this period!!!\n");
            return;
        }

        System.out.println("\t\t\t\t\t\t\t\t\t\t ******* Transaction list for those days *******\n");
        for (Transaction transactionDto : paymentFromTo) {
            System.out.println(transactionDto);
        }
        System.out.println();
    }

//    public void setTransactionRepository(TransactionRepository transactionRepository) {
//        this.transactionRepository = transactionRepository;
//    }
//
//    public void setTerminalRepository(TerminalRepository terminalRepository) {
//        this.terminalRepository = terminalRepository;
//    }
//
//    public void setCardRepository(CardRepository cardRepository) {
//        this.cardRepository = cardRepository;
//    }
}
