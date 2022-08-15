package org.elkartech.snackmachine.domain;

import java.math.BigDecimal;
import java.util.List;

import org.elkartech.snackmachine.util.ValueObject;

public final class Money extends ValueObject<Money> {

    private final int fiveCentsCoinAmount;
    private final int tenCentsCoinAmount;
    private final int twentyCentsCoinAmount;
    private final int fiftyCentsCoinAmount;
    private final int oneEuroCoinAmount;
    private final int twoEuroCoinAmount;
    private final int fiveEuroBillAmount;

    public static final Money NO_MONEY =     new Money(0, 0, 0, 0, 0, 0, 0);
    public static final Money FIVE_CENTS =   new Money(1, 0, 0, 0, 0, 0, 0);
    public static final Money TEN_CENTS =    new Money(0, 1, 0, 0, 0, 0, 0);
    public static final Money TWENTY_CENTS = new Money(0, 0, 1, 0, 0, 0, 0);
    public static final Money FIFTY_CENTS =  new Money(0, 0, 0, 1, 0, 0, 0);
    public static final Money ONE_EURO =     new Money(0, 0, 0, 0, 1, 0, 0);
    public static final Money TWO_EURO =     new Money(0, 0, 0, 0, 0, 1, 0);
    public static final Money FIVE_EURO =    new Money(0, 0, 0, 0, 0, 0, 1);
    public static final List<Money> ALL_MONEY_TYPES = List.of(
            FIVE_CENTS, TEN_CENTS, TWENTY_CENTS, FIFTY_CENTS, ONE_EURO, TWO_EURO, FIVE_EURO);

    
    public Money(
            final int fiveCentsCoinAmount, 
            final int tenCentsCoinAmount, 
            final int twentyCentsCoinAmount,
            final int fiftyCentsCoinAmount, 
            final int oneEuroCoinAmount, 
            final int twoEuroCoinAmount,
            final int fiveEuroBillAmount) {
        if (fiveCentsCoinAmount < 0)
            throw new IllegalArgumentException();
        if (tenCentsCoinAmount < 0)
            throw new IllegalArgumentException();
        if (twentyCentsCoinAmount < 0)
            throw new IllegalArgumentException();
        if (fiftyCentsCoinAmount < 0)
            throw new IllegalArgumentException();
        if (oneEuroCoinAmount < 0)
            throw new IllegalArgumentException();
        if (twoEuroCoinAmount < 0)
            throw new IllegalArgumentException();
        if (fiveEuroBillAmount < 0)
            throw new IllegalArgumentException();

        this.fiveCentsCoinAmount = fiveCentsCoinAmount;
        this.tenCentsCoinAmount = tenCentsCoinAmount;
        this.twentyCentsCoinAmount = twentyCentsCoinAmount;
        this.fiftyCentsCoinAmount = fiftyCentsCoinAmount;
        this.oneEuroCoinAmount = oneEuroCoinAmount;
        this.twoEuroCoinAmount = twoEuroCoinAmount;
        this.fiveEuroBillAmount = fiveEuroBillAmount;
    }

    protected int getFiveCentsCoinAmount() {
        return this.fiveCentsCoinAmount;
    }

    protected int getTenCentsCoinAmount() {
        return this.tenCentsCoinAmount;
    }

    protected int getTwentyCentsCoinAmount() {
        return this.twentyCentsCoinAmount;
    }

    protected int getFiftyCentsCoinAmount() {
        return this.fiftyCentsCoinAmount;
    }

    protected int getOneEuroCoinAmount() {
        return this.oneEuroCoinAmount;
    }

    protected int getTwoEuroCoinAmount() {
        return this.twoEuroCoinAmount;
    }

    protected int getFiveEuroBillAmount() {
        return this.fiveEuroBillAmount;
    }
    
    public static Money of(final BigDecimal amount) {
        if (amount.compareTo(FIVE_CENTS.amountInEuro()) == 0) {
            return FIVE_CENTS;
        }
        else if (amount.compareTo(TEN_CENTS.amountInEuro()) == 0) {
            return TEN_CENTS;
        }
        else if (amount.compareTo(TWENTY_CENTS.amountInEuro()) == 0) {
            return TWENTY_CENTS;
        }
        else if (amount.compareTo(FIFTY_CENTS.amountInEuro()) == 0) {
            return FIFTY_CENTS;
        }
        else if (amount.compareTo(ONE_EURO.amountInEuro()) == 0) {
            return ONE_EURO;
        }
        else if (amount.compareTo(TWO_EURO.amountInEuro()) == 0) {
            return TWO_EURO;
        }
        else if (amount.compareTo(FIVE_EURO.amountInEuro()) == 0) {
            return FIVE_EURO;
        }
        throw new IllegalArgumentException("The given amount does not match an existing coin or bill");
    }

    public Money add(final Money money) {
        return new Money(
                this.fiveCentsCoinAmount + money.fiveCentsCoinAmount,
                this.tenCentsCoinAmount + money.tenCentsCoinAmount,
                this.twentyCentsCoinAmount + money.twentyCentsCoinAmount,
                this.fiftyCentsCoinAmount + money.fiftyCentsCoinAmount,
                this.oneEuroCoinAmount + money.oneEuroCoinAmount, 
                this.twoEuroCoinAmount + money.twoEuroCoinAmount,
                this.fiveEuroBillAmount + money.fiveEuroBillAmount);
    }
    
    public Money subtract(final Money money) {
        return new Money(
                this.fiveCentsCoinAmount - money.fiveCentsCoinAmount, 
                this.tenCentsCoinAmount - money.tenCentsCoinAmount, 
                this.twentyCentsCoinAmount - money.twentyCentsCoinAmount, 
                this.fiftyCentsCoinAmount - money.fiftyCentsCoinAmount, 
                this.oneEuroCoinAmount - money.oneEuroCoinAmount, 
                this.twoEuroCoinAmount - money.twoEuroCoinAmount, 
                this.fiveEuroBillAmount - money.fiveEuroBillAmount);
    }
    
    public Money allocate(BigDecimal amount) {
        int fiveEuroBillCount = amount.divide(new BigDecimal("5"))
                .min(new BigDecimal(this.fiveEuroBillAmount)).intValue();
        amount = amount.subtract(new BigDecimal(fiveEuroBillCount).multiply(new BigDecimal("5")));
        
        int twoEuroCoinCount = amount.divide(new BigDecimal("2"))
                .min(new BigDecimal(this.twoEuroCoinAmount)).intValue();
        amount = amount.subtract(new BigDecimal(twoEuroCoinCount).multiply(new BigDecimal("2")));
        
        int oneEuroCoinCount = amount
                .min(new BigDecimal(this.oneEuroCoinAmount)).intValue();
        amount = amount.subtract(new BigDecimal(oneEuroCoinCount));
        
        int fiftyCentsCoinCount = amount.divide(new BigDecimal("0.5"))
                .min(new BigDecimal(this.fiftyCentsCoinAmount)).intValue();
        amount = amount.subtract(new BigDecimal(fiftyCentsCoinCount).multiply(new BigDecimal("0.5")));
        
        int twentyCentsCoinCount = amount.divide(new BigDecimal("0.2"))
                .min(new BigDecimal(this.twentyCentsCoinAmount)).intValue();
        amount = amount.subtract(new BigDecimal(twentyCentsCoinCount).multiply(new BigDecimal("0.2")));
        
        int tenCentsCoinCount = amount.divide(new BigDecimal("0.1"))
                .min(new BigDecimal(this.tenCentsCoinAmount)).intValue();
        amount = amount.subtract(new BigDecimal(tenCentsCoinCount).multiply(new BigDecimal("0.1")));
        
        int fiveCentsCoinCount = amount.divide(new BigDecimal("0.05"))
                .min(new BigDecimal(this.fiveCentsCoinAmount)).intValue();
        amount = amount.subtract(new BigDecimal(fiveCentsCoinCount).multiply(new BigDecimal("0.05")));
        
        return new Money(fiveCentsCoinCount, tenCentsCoinCount, twentyCentsCoinCount, 
                fiftyCentsCoinCount, oneEuroCoinCount, twoEuroCoinCount, fiveEuroBillCount);
        
    }

    public BigDecimal amountInEuro() {
        return BigDecimal.valueOf(this.fiveCentsCoinAmount).multiply(new BigDecimal("0.05"))
                .add(BigDecimal.valueOf(this.tenCentsCoinAmount).multiply(new BigDecimal("0.1")))
                .add(BigDecimal.valueOf(this.twentyCentsCoinAmount).multiply(new BigDecimal("0.2")))
                .add(BigDecimal.valueOf(this.fiftyCentsCoinAmount).multiply(new BigDecimal("0.5")))
                .add(BigDecimal.valueOf(this.oneEuroCoinAmount))
                .add(BigDecimal.valueOf(this.twoEuroCoinAmount).multiply(new BigDecimal("2")))
                .add(BigDecimal.valueOf(this.fiveEuroBillAmount).multiply(new BigDecimal("5")));
    }

    @Override
    protected boolean equalsCore(final Money money) {
        return this.fiveCentsCoinAmount == money.fiveCentsCoinAmount
                && this.tenCentsCoinAmount == money.tenCentsCoinAmount
                && this.twentyCentsCoinAmount == money.twentyCentsCoinAmount
                && this.fiftyCentsCoinAmount == money.fiftyCentsCoinAmount
                && this.oneEuroCoinAmount == money.oneEuroCoinAmount
                && this.twoEuroCoinAmount == money.twoEuroCoinAmount
                && this.fiveEuroBillAmount == money.fiveEuroBillAmount;
    }

    @Override
    protected int hashCodeCore() {
        int hashCode = this.fiveCentsCoinAmount;
        hashCode = 31 * hashCode + this.tenCentsCoinAmount;
        hashCode = 31 * hashCode + this.twentyCentsCoinAmount;
        hashCode = 31 * hashCode + this.fiftyCentsCoinAmount;
        hashCode = 31 * hashCode + this.oneEuroCoinAmount;
        hashCode = 31 * hashCode + this.twoEuroCoinAmount;
        hashCode = 31 * hashCode + this.fiveEuroBillAmount;
        return hashCode;
    }

}
