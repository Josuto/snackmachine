package org.elkartech.snackmachine.domain;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.*;
import static org.elkartech.snackmachine.domain.Money.ONE_EURO;

import java.math.BigDecimal;

public final class MoneyTest {

    @Test
    public void testPlusPass() {
        Money money1 = new Money(1, 2, 3, 4, 5, 6, 7);
        Money money2 = new Money(1, 2, 3, 4, 5, 6, 7);

        Money result = money1.add(money2);

        assertThat(result).isEqualTo(new Money(2, 4, 6, 8, 10, 12, 14));
    }

    @Test
    public void testStructurallyEqualMoneyInstancesPass() {
        Money money1 = new Money(1, 2, 3, 4, 5, 6, 7);
        Money money2 = new Money(1, 2, 3, 4, 5, 6, 7);

        assertThat(money1).isEqualTo(money2);
        assertThat(money1.hashCode()).isEqualTo(money2.hashCode());
    }

    @Test
    public void testStructurallyNonEqualMoneyInstancesPass() {
        Money money = new Money(20, 0, 0, 0, 0, 0, 0);

        assertThat(money).isNotEqualTo(ONE_EURO);
        assertThat(money.hashCode()).isNotEqualTo(ONE_EURO.hashCode());
    }
    
    @Test
    public void testNullInequalityPass() {
        Money money = new Money(20, 0, 0, 0, 0, 0, 0);

        assertThat(money).isNotEqualTo(null);
    }

    @DataProvider
    private Object[][] negativeMoneyDataProvider() {
        return new Object[][] { 
            { new int[] { -1, 0, 0, 0, 0, 0, 0 } }, 
            { new int[] { 0, -1, 0, 0, 0, 0, 0 } },
            { new int[] { 0, 0, -1, 0, 0, 0, 0 } }, 
            { new int[] { 0, 0, 0, -1, 0, 0, 0 } },
            { new int[] { 0, 0, 0, 0, -1, 0, 0 } }, 
            { new int[] { 0, 0, 0, 0, 0, -1, 0 } },
            { new int[] { 0, 0, 0, 0, 0, 0, -1 } } };
    }

    @Test(dataProvider = "negativeMoneyDataProvider")
    public void testMoneyConstructorNegativeAmountsFail(final int[] a) {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            new Money(a[0], a[1], a[2], a[3], a[4], a[5], a[6]);
        });
    }

    @DataProvider
    private Object[][] amountInEuroDataProvider() {
        return new Object[][] { 
                { new int[] { 1, 0, 0, 0, 0, 0, 0 }, new BigDecimal("0.05") },
                { new int[] { 1, 2, 0, 0, 0, 0, 0 }, new BigDecimal("0.25") },
                { new int[] { 1, 2, 3, 0, 0, 0, 0 }, new BigDecimal("0.85") },
                { new int[] { 1, 2, 3, 4, 0, 0, 0 }, new BigDecimal("2.85") },
                { new int[] { 1, 2, 3, 4, 5, 0, 0 }, new BigDecimal("7.85") },
                { new int[] { 1, 2, 3, 4, 5, 6, 0 }, new BigDecimal("19.85") },
                { new int[] { 1, 2, 3, 4, 5, 6, 7 }, new BigDecimal("54.85") }, };
    }

    @Test(dataProvider = "amountInEuroDataProvider")
    public void testAmountInEuroPass(final int[] a, final BigDecimal expectedAmount) {
        Money money = new Money(a[0], a[1], a[2], a[3], a[4], a[5], a[6]);

        BigDecimal amount = money.amountInEuro();

        assertThat(amount).isEqualTo(expectedAmount);
    }

}
