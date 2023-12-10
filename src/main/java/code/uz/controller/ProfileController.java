package code.uz.controller;


import code.uz.dto.Profile;
import code.uz.service.CardService;
import code.uz.service.TransactionService;
import code.uz.utils.ScannerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
public class ProfileController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private CardService cardService;

    Scanner scannerStr = new Scanner(System.in);
    String phone, cardNumber, terminalCode;
    int option;
    boolean bool;


    public void start(Profile profile) {
        bool = true;
        while (bool) {
            menu();
            option = ScannerUtil.getOption();
            switch (option) {
                case 1 -> addCard(profile);
                case 2 -> profileCardList(profile);
                case 3 -> changeCardStatus();
                case 4 -> deleteCard();
                case 5 -> reFill();
                case 6 -> transactionList(profile);
                case 7 -> makePayment();
                case 0 -> bool = false;
            }
        }
    }


    private void menu() {
        System.out.println("""
                                
                ********************** Menu **********************
                                
                1. Add Card
                2. Card List
                3. Card Change Status
                4. Delete Card
                5. ReFill
                6. Transaction List
                7. Make Payment
                0. Log out
                                
                """);
    }

    /**
     * Card
     */

    private void addCard(Profile profile) {
        cardService.addCard(profile);

    }

    private void profileCardList(Profile profile) {

        cardService.getMyCardList(profile);

    }

    private void changeCardStatus() {
        System.out.println("Enter card number");
        String cardNumber = scannerStr.nextLine();
        cardService.changeCardStatus(cardNumber);
    }

    private void deleteCard() {
        System.out.println("Enter card number");
        String cardNumber = scannerStr.nextLine();
        cardService.profileDeleteCard(cardNumber);
    }

    private void reFill() {
        transactionService.isExistTransaction();  // create default terminal code = 123

        String balances;
        do {
            System.out.println("Enter card number ");
            cardNumber = scannerStr.nextLine();

            System.out.println("Enter balance");
            balances = scannerStr.nextLine();

            System.out.println("Enter  phone");
            phone = scannerStr.nextLine();

            System.out.println("Enter  terminal code");
            terminalCode = scannerStr.nextLine();


        } while (cardNumber.trim().isEmpty() || balances.trim().isEmpty() || phone.trim().isEmpty());


        cardService.refill(phone, cardNumber, Double.parseDouble(balances), terminalCode);

    }

    private void transactionList(Profile profile) {
        transactionService.getUserTransactionList(profile);

    }

    private void makePayment() {
        System.out.println("Enter amount");
        String amount = scannerStr.nextLine();

        System.out.println("Enter card number");
        String cardNumber = scannerStr.nextLine();

        System.out.println("Enter terminal code");
        String terminalCode = scannerStr.nextLine();

        transactionService.makePayment(amount, cardNumber, terminalCode);

    }

//    public void setTransactionService(TransactionService transactionService) {
//        this.transactionService = transactionService;
//    }
//
//    public void setCardService(CardService cardService) {
//        this.cardService = cardService;
//    }
}


