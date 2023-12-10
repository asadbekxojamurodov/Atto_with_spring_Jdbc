package code.uz.service;

import code.uz.RandomUtils.RandomUtil;
import code.uz.dto.Card;
import code.uz.dto.Profile;
import code.uz.enums.Status;
import code.uz.enums.TransactionType;
import code.uz.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private TransactionService transactionService;


    long smallest = 1000_0000_0000L;
    long biggest = 9999_9999_9999L;


    public void createCard(long year) {
        String cardNumber = "1234";
        Card card = new Card();
        card.setCardNumber(cardNumber + RandomCardNumber());

        card.setBalance(0.0);
        card.setStatus(Status.NO_ACTIVE);
        card.setCreatedDate(LocalDateTime.now());
        card.setExpireDate(LocalDate.now().plusYears(year));
        cardRepository.createCard(card);
    }


    public String RandomCardNumber() {
        return String.valueOf(RandomUtil.getRandomNumber(smallest, biggest));
    }


    public void getCardList() {
        List<Card> allCard = cardRepository.getAllCard();
        if (allCard.isEmpty()) {
            System.out.println("\nNo cards\n");
            return;
        }
        System.out.println("\t\t\t\t\t\t\t\t\t\t**************** Card list ****************\n");
        for (Card cardDto : allCard) {
            System.out.println(cardDto);
        }
        System.out.println();
    }

    public void addCard(Profile profile) {

        if (!setCardForUser(profile)) {
            System.out.println("No cards");
            return;
        }
        System.out.println("successfully added");
    }

    public boolean setCardForUser(Profile profile) {

        List<Card> allNoActiveCardList = cardRepository.getAllNoActiveCardList();
        if (allNoActiveCardList.isEmpty()) {
            return false;
        }

        for (Card card : allNoActiveCardList) {

            card.setPhone(profile.getPhone());
            card.setStatus(Status.ACTIVE);
            return cardRepository.addCard(card);
        }

        return false;
    }

    public void getMyCardList(Profile profileDto) {

        List<Card> allCard = cardRepository.getAllMyCard(profileDto.getPhone());
        if (allCard.isEmpty()) {
            System.out.println("You don't hava cards ");
            return;
        }
        System.out.println(allCard);
        System.out.println("\t\t\t\t\t\t\t\t\t\t**************** Card list ****************\n");
        for (Card cardDto : allCard) {
            System.out.println(cardDto);
//            System.out.println("CARD NUMBER < " + cardDto.getCardNumber()
//                    + " > EXPIRE DATE < " + cardDto.getExpireDate()
//                    + " > BALANCE < " + cardDto.getBalance() + " >");
        }


    }


    public void changeCardStatus(String cardNumber) {

        Card existCard = cardRepository.getCardByNumber(cardNumber);
        if (existCard == null) {
            System.out.println("card not found");
            return;
        }

        if (existCard.getStatus().equals(Status.ACTIVE)) {
            existCard.setStatus(Status.BLOCKED);
            cardRepository.changeCardStatus(existCard);

        } else if (existCard.getStatus().equals(Status.BLOCKED)) {
            existCard.setStatus(Status.ACTIVE);
            cardRepository.changeCardStatus(existCard);

        }
    }


    public void refill(String phone, String cardNumber, double balance, String terminalCode) {

        Card card = cardRepository.getCardByNumber(cardNumber);

        if (card == null) {
            System.out.println("this card not belong to you");
            return;
        }

        if (card.getPhone() == null || !card.getPhone().equals(phone)) {
            System.out.println(" card not belongs to you.");
            return;
        }

        cardRepository.refill(cardNumber, balance);

        transactionService.createTransaction(card.getId(), cardNumber, terminalCode, balance, TransactionType.REFILL, phone);

    }

    public void adminUpdateCard(String cardNumber, String expireDate) {
        Card exist = cardRepository.getCardByNumber(cardNumber);
        if (exist == null) {
            System.out.println("Card not found");
            return;
        }

        Card card = new Card();
        card.setCardNumber(cardNumber);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDate localDate = LocalDate.parse(expireDate, timeFormatter);

        if (localDate.isBefore(LocalDate.now())) {
            System.out.println("locale Date must be bigger than now");
            return;
        }

        card.setExpireDate(localDate);

        int n = cardRepository.AdminUpdateCard(card);
        if (n != 0) {
            System.out.println("Card Updated");
        }
    }

    public void adminChangeCardStatus(String cardNumber) {
        Card exist = cardRepository.getCardByNumber(cardNumber);
        if (exist == null) {
            System.out.println("Card not found");
            return;
        }

        if (exist.getStatus().equals(Status.ACTIVE)) {
            cardRepository.updateCardStatus(cardNumber, Status.BLOCKED);

        } else if (exist.getStatus().equals(Status.BLOCKED)) {
            cardRepository.updateCardStatus(cardNumber, Status.ACTIVE);

        }
    }

    public int adminDeleteCard(String cardNumber) {
        Card exist = cardRepository.getCardByNumber(cardNumber);
        if (exist == null) {
            System.out.println("Card not found");
            return 0;
        }

        return cardRepository.adminDeleteCard(cardNumber);
    }

    public void profileDeleteCard(String cardNumber) {
        Card exist = cardRepository.getCardByNumber(cardNumber);
        if (exist == null) {
            System.out.println("Card not found");
            return;
        }
        cardRepository.profileDeleteCard(exist);

        System.out.println("Card deleted ");

    }

//    public void setCardRepository(CardRepository cardRepository) {
//        this.cardRepository = cardRepository;
//    }
//
//    public void setTransactionService(TransactionService transactionService) {
//        this.transactionService = transactionService;
//    }
}





