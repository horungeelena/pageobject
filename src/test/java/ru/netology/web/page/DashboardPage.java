package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
  private SelenideElement heading = $("[data-test-id=dashboard]");

  private SelenideElement cardNumberOneButton = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']>[data-test-id='action-deposit']");
  private SelenideElement cardNumberTwoButton = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']>[data-test-id='action-deposit']");
  private SelenideElement balanceCardNumberOne = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']");
  private SelenideElement balanceCardNumberTwo = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']");

  public void dashboardPageVisible() {
    heading.shouldBe(visible);
  }

  public MoneyTransfer topUpCardNumberOne() {
    cardNumberOneButton.click();
    return new MoneyTransfer();
  }

  public MoneyTransfer topUpCardNumberTwo() {
    cardNumberTwoButton.click();
    return new MoneyTransfer();
  }

  public Integer getBalanceCardOne() {
    String text = balanceCardNumberOne.getText();
    String[] subtext = text.split(":");
    String balance = subtext[1].substring(0, subtext[1].indexOf("р.")).trim();
    return Integer.parseInt(balance);
  }

  public Integer getBalanceCardTwo() {
    String text = balanceCardNumberTwo.getText();
    String[] subtext = text.split(":");
    String balance = subtext[1].substring(0, subtext[1].indexOf("р.")).trim();
    return Integer.parseInt(balance);
  }
}
