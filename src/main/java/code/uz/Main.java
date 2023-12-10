package code.uz;

import code.uz.controller.AuthController;
import code.uz.db.DatabaseUtil;
import code.uz.db.InitDatabase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);

        DatabaseUtil databaseUtil = (DatabaseUtil) applicationContext.getBean("databaseUtil");


        databaseUtil.createProfileTable();
        databaseUtil.createCardTable();
        databaseUtil.createTerminalTable();
        databaseUtil.createTransactionTable();


        InitDatabase initDatabase = (InitDatabase) applicationContext.getBean("initDatabase");

        initDatabase.adminInit();
        initDatabase.addCompanyCard();

        AuthController authController = (AuthController) applicationContext.getBean("authController");
        authController.start();

    }
}
