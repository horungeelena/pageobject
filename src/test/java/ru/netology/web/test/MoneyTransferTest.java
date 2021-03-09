package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
  private int amountValid = 5000;
  private int amountInvalid = 100000;


  private DashboardPage shouldEnterDashboardPage() {
      open("http://localhost:9999");
      val loginPage = new LoginPage();
      val authInfo = DataHelper.getAuthInfo();
      val verificationPage = loginPage.validLogin(authInfo);
      val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
      return verificationPage.validVerify(verificationCode);
    }

  @Test
  void shouldEnterValidLoginCards() {
    open("http://localhost:9999");
    val loginPage = new LoginPage();
    val authInfo = DataHelper.getAuthInfo();
    val verificationPage = loginPage.validLogin(authInfo);
    val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
    verificationPage.validVerify(verificationCode);
  }

  @Test
  void shouldTransferMoneyFromCardTwoToCardOne() {
    DashboardPage dashboardPage = shouldEnterDashboardPage();
    dashboardPage.dashboardPageVisible();
    int expected1 = dashboardPage.getBalanceCardTwo() + amountValid;
    int expected2 = dashboardPage.getBalanceCardOne() - amountValid;
    val moneyTransfer = dashboardPage.topUpCardNumberTwo();
    moneyTransfer.moneyTransferVisible();
    moneyTransfer.setTransferAmount(amountValid);
    moneyTransfer.setNumberCardFrom(DataHelper.getCardNumberOne());
    moneyTransfer.doTransfer();
    assertEquals(expected1, dashboardPage.getBalanceCardTwo());
    assertEquals(expected2, dashboardPage.getBalanceCardOne());
  }

  @Test
  void shouldTransferMoneyFromCardOneToCardTwo() {
    DashboardPage dashboardPage = shouldEnterDashboardPage();
    dashboardPage.dashboardPageVisible();
    int expected1 = dashboardPage.getBalanceCardOne() + amountValid;
    int expected2 = dashboardPage.getBalanceCardTwo() - amountValid;
    val moneyTransfer = dashboardPage.topUpCardNumberOne();
    moneyTransfer.moneyTransferVisible();
    moneyTransfer.setTransferAmount(amountValid);
    moneyTransfer.setNumberCardFrom(DataHelper.getCardNumberTwo());
    moneyTransfer.doTransfer();
    assertEquals(expected1, dashboardPage.getBalanceCardOne());
    assertEquals(expected2, dashboardPage.getBalanceCardTwo());
  }

  @Test
  void shouldTransferInvalidAmountFromCardTwoToCardOne() {
    DashboardPage dashboardPage = shouldEnterDashboardPage();
    dashboardPage.dashboardPageVisible();
    val moneyTransfer = dashboardPage.topUpCardNumberTwo();
    moneyTransfer.moneyTransferVisible();
    moneyTransfer.setTransferAmount(amountInvalid);
    moneyTransfer.setNumberCardFrom(DataHelper.getCardNumberOne());
    moneyTransfer.doTransfer();
    moneyTransfer.errorTransfer();
  }
}

