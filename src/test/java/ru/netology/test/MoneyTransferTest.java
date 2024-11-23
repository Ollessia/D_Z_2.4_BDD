package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPageV2;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

    @BeforeEach
    public void openPage() {

        open("http://localhost:9999");

        val loginPage = new LoginPageV2();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }


    @Test
    void shouldTransferMoneyBetweenOwnCards1() {

        val dashboardPage = new DashboardPage();

        int balanceFirstCard = dashboardPage.getFirstCardBalance();
        int balanceSecondCard = dashboardPage.getSecondCardBalance();
        val moneyTransfer = dashboardPage.firstCardButton();
        val infoCard = DataHelper.getSecondCardNumber();
        String sum = "200";
        moneyTransfer.transferForm(sum, infoCard);

        assertEquals(balanceFirstCard + Integer.parseInt(sum), dashboardPage.getFirstCardBalance());
        assertEquals(balanceSecondCard - Integer.parseInt(sum), dashboardPage.getSecondCardBalance());
    }

    @Test
    void shouldTransferMoneyBetweenOwnCards2() {

        val dashboardPage = new DashboardPage();

        int balanceFirstCard = dashboardPage.getFirstCardBalance();
        int balanceSecondCard = dashboardPage.getSecondCardBalance();
        val moneyTransfer = dashboardPage.secondCardButton();
        val infoCard = DataHelper.getFirstCardNumber();
        String sum = "1000";
        moneyTransfer.transferForm(sum, infoCard);

        assertEquals(balanceFirstCard - Integer.parseInt(sum), dashboardPage.getFirstCardBalance());
        assertEquals(balanceSecondCard + Integer.parseInt(sum), dashboardPage.getSecondCardBalance());
    }

    @Test
    void shouldTransferZero() {

        val dashboardPage = new DashboardPage();

        int balanceFirstCard = dashboardPage.getFirstCardBalance();
        int balanceSecondCard = dashboardPage.getSecondCardBalance();
        val moneyTransfer = dashboardPage.secondCardButton();
        val infoCard = DataHelper.getFirstCardNumber();
        String sum = "0";
        moneyTransfer.transferForm(sum, infoCard);

        assertEquals(balanceFirstCard - Integer.parseInt(sum), dashboardPage.getFirstCardBalance());
        assertEquals(balanceSecondCard + Integer.parseInt(sum), dashboardPage.getSecondCardBalance());
    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsError() {

        val dashboardPage = new DashboardPage();

        val moneyTransfer = dashboardPage.secondCardButton();
        val infoCard = DataHelper.getFirstCardNumber();
        String sum = "30000";
        moneyTransfer.transferForm(sum, infoCard);
        moneyTransfer.getError();
    }
}