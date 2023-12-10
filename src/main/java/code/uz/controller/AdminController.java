package code.uz.controller;


import code.uz.dto.Terminal;
import code.uz.service.CardService;
import code.uz.service.ProfileService;
import code.uz.service.TerminalService;
import code.uz.service.TransactionService;
import code.uz.utils.ScannerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
public class AdminController {
    @Autowired
    private CardService cardService;
    @Autowired
    private TerminalService terminalService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private TransactionService transactionService;


    Scanner scannerLong = new Scanner(System.in);
    Scanner scannerStr = new Scanner(System.in);
    boolean bool;
    int option;

    public void start() {
        bool = true;
        int option;

        while (bool) {

            menu();
            option = ScannerUtil.getOption();

            switch (option) {
                case 1 -> cardMenu();
                case 2 -> terminalMenu();
                case 3 -> profileMenu();
                case 4 -> transactionMenu();
                case 5 -> statisticsMenu();
                case 0 -> bool = false;
            }

        }
    }

    private void menu() {
        System.out.println("""
                                
                ********************** Menu *********************
                                
                1. Card
                2. Terminal
                3. Profile
                4. Transaction
                5. Statistic
                0. Log out
                """);
    }


    /**
     * CARD
     */

    private void cardMenu() {
        do {

            System.out.println("""
                                        
                    ************ Card menu ************
                                        
                    1. Create Card
                    2. Card List
                    3. Update Card
                    4. Card Change Status
                    5. Delete Card
                    0. Exit
                    """);
            option = ScannerUtil.getOption();
            if (option == 0) {
                return;
            }
            cardOption(option);
        } while (option != 0);


    }

    private void cardOption(int option) {
        switch (option) {
            case 1 -> createCard();
            case 2 -> cardList();
            case 3 -> updateCard();
            case 4 -> changeCardStatus();
            case 5 -> deleteCard();
        }
    }


    /**
     * TERMINAL
     */
    private void terminalMenu() {
        do {

            System.out.println("""
                                        
                    ************ Terminal menu ************
                                        
                    1. Create Terminal
                    2. Terminal List
                    3. Update Terminal
                    4. Change Terminal Status
                    5. Delete terminal
                    0. Exit
                    """);
            option = ScannerUtil.getOption();
            if (option == 0) {
                return;
            }
            terminalOption(option);
        } while (option != 0);

    }

    private void terminalOption(int option) {
        switch (option) {
            case 1 -> createTerminal();
            case 2 -> terminalList();
            case 3 -> updateTerminal();
            case 4 -> changeTerminalStatus();
            case 5 -> deleteTerminal();

        }
    }


    /**
     * PROFILE
     */
    private void profileMenu() {
        do {

            System.out.println("""
                                        
                    ************ Profile menu ************
                                        
                    1. Profile List
                    2. Change Profile Status
                    0. Exit
                    """);
            option = ScannerUtil.getOption();
            if (option == 0) {
                return;
            }
            profileOption(option);
        } while (option != 0);

    }

    private void profileOption(int option) {
        switch (option) {
            case 1 -> profileList();
            case 2 -> changeProfileStatus();
        }
    }


    /**
     * TRANSACTION
     */
    private void transactionMenu() {
        do {

            System.out.println("""
                                        
                    ************ Transaction menu ************
                                        
                    1. Transaction List
                    2. Company Card Balance
                    0. Exit
                    """);
            option = ScannerUtil.getOption();
            if (option == 0) {
                return;
            }
            transactionOption(option);
        } while (option != 0);

    }

    private void transactionOption(int option) {
        switch (option) {
            case 1 -> transactionList();
            case 2 -> companyCardBalance();
        }
    }


    /**
     * STATISTICS
     */
    private void statisticsMenu() {
        do {
            System.out.println("""
                                        
                    ************ Statistic menu ************
                                        
                    1. Today's payments
                    2. Daily payments
                    3. Interim payments
                    4. Total balance
                    5. Transaction by terminal code
                    6. Transaction by card
                    0. Exit
                    """);
            option = ScannerUtil.getOption();
            if (option == 0) {
                return;
            }
            statisticsOption(option);
        } while (option != 0);

    }

    private void statisticsOption(int option) {
        switch (option) {
            case 1 -> paymentListForToday();
            case 2 -> dailyPayment();
            case 3 -> interimPayments();
            case 4 -> totalBalance();
            case 5 -> transactionByTerminal();
            case 6 -> transactionByCard();
        }
    }


    /***
     * Card
     */

    private void createCard() {
        long year;
        do {
            System.out.println("Enter year for expire date [3 , 10]");

            year = scannerLong.nextLong();
        } while (year < 3);

        cardService.createCard(year);

    }

    private void cardList() {
        cardService.getCardList();
//        Profile profile = new Profile();
//        profile.setPhone("1");
//        cardService.getMyCardList(profile);
    }

    private void updateCard() {

        System.out.println("Enter card number");
        String cardNumber = scannerStr.nextLine();

        System.out.print("Enter card expired date (yyyy.MM.dd): ");
        String expireDate = scannerStr.nextLine();

        cardService.adminUpdateCard(cardNumber, expireDate);

    }

    private void changeCardStatus() {

        System.out.print("Enter card number: ");
        String cardNumber = scannerStr.nextLine();

        cardService.adminChangeCardStatus(cardNumber);

    }

    private void deleteCard() {
        System.out.print("Enter card number: ");
        String cardNumber = scannerStr.nextLine();


        if (cardService.adminDeleteCard(cardNumber) == 1) {
            System.out.println("Card deleted");
            return;
        }
        System.out.println("card not found");
    }


    /***
     * Terminal
     */

    private void createTerminal() {

        System.out.print("Enter  code: ");
        String code = scannerStr.nextLine();

        System.out.println("Enter address");
        String address = scannerStr.nextLine();

        Terminal terminal = new Terminal();

        terminal.setAddress(address);
        terminal.setCode(code);

        terminalService.adminCreateTerminal(terminal);

    }

    private void terminalList() {
        terminalService.getTerminalList();
    }

    private void updateTerminal() {

        System.out.println("enter terminal code");
        String code = scannerStr.nextLine();
        System.out.println("Enter new address for terminal");
        String address = scannerStr.nextLine();

        Terminal terminal = new Terminal();

        terminal.setCode(code);
        terminal.setAddress(address);

        terminalService.updateTerminalByCode(terminal);

    }

    private void changeTerminalStatus() {
        System.out.println("enter terminal code");
        String code = scannerStr.nextLine();

        terminalService.changeTerminalStatus(code);


    }

    private void deleteTerminal() {
        System.out.println("enter terminal code");
        String code = scannerStr.nextLine();
        terminalService.delete(code);
    }


    /***
     * Profile
     */

    private void profileList() {
        profileService.getAllProfileList();
    }

    private void changeProfileStatus() {
        System.out.println("Enter phone number");
        String phone = scannerStr.nextLine();
        profileService.changeProfileStatus(phone);
    }


    /***
     * Transaction
     */

    private void transactionList() {
        transactionService.transactionList();
    }

    private void companyCardBalance() {
        transactionService.companyCardBalance();
    }


    /**
     * Statistic
     */

    private void paymentListForToday() {
        transactionService.paymentInToday();
    }

    private void dailyPayment() {
        System.out.println("Enter Date: yyyy-MM-dd");
        String date = scannerStr.nextLine();
        transactionService.dailyPayments(date);
    }

    private void interimPayments() {
        System.out.println("Enter start Date: yyyy-MM-dd");
        String startDate = scannerStr.nextLine();

        System.out.println("Enter finish Date: yyyy-MM-dd");
        String finishDate = scannerStr.nextLine();
        transactionService.getPaymentFromTo(startDate, finishDate);

    }

    private void totalBalance() {
        transactionService.companyCardBalance();
    }

    private void transactionByCard() {
        System.out.println("enter card number");
        String cardNumber = scannerStr.nextLine();
        transactionService.getTransactionByCard(cardNumber);
    }

    private void transactionByTerminal() {
        System.out.println("enter terminal code");
        String code = scannerStr.nextLine();
        transactionService.getTransactionByTerminalCode(code);

    }

//    public void setCardService(CardService cardService) {
//        this.cardService = cardService;
//    }
//
//    public void setTerminalService(TerminalService terminalService) {
//        this.terminalService = terminalService;
//    }
//
//    public void setProfileService(ProfileService profileService) {
//        this.profileService = profileService;
//    }
//
//    public void setTransactionService(TransactionService transactionService) {
//        this.transactionService = transactionService;
//    }
}
