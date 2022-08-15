package org.elkartech.snackmachine.domain;

import static org.elkartech.snackmachine.domain.Money.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.elkartech.snackmachine.util.AggregateRoot;

public final class SnackMachine extends AggregateRoot {
    
    private Money moneyInside;
    private BigDecimal moneyInTransaction;
    private List<Slot> slots;
    
    
    public SnackMachine() {
    	this.moneyInside = NO_MONEY;
    	this.moneyInTransaction = BigDecimal.ZERO;
    	this.slots = Arrays.asList(new Slot[] { 
    	        new Slot(0, null), new Slot(1, null), new Slot(2, null) 
        });
    }
    
    public Money getMoneyInside() {
        return this.moneyInside;
    }

    public BigDecimal getMoneyInTransaction() {
        return this.moneyInTransaction;
    }
    
    public void loadMoney(final Money money) {
        if (money == null) 
            throw new IllegalArgumentException();
        
        this.moneyInside = money;
    }
    
    private Slot getSlot(final int position) {
        return this.slots.get(position);
    }

    public void loadSnackPile(final int position, final SnackPile snackPile) {
        Slot slot = this.getSlot(position);
        slot.setSnackPile(snackPile);
    }
    
    SnackPile getSnackPile(final int position) {
        return this.getSlot(position).getSnackPile();
    }
    
    private BigDecimal getSnackPrice(final int position) {
        return this.getSnackPile(position).getSnack().getPrice();
    }
    
    public void insertMoney(final Money money) {
    	if (money == null)
    	    throw new IllegalArgumentException();
    	if (!ALL_MONEY_TYPES.contains(money)) // Cannot insert various coins/bills at once
    	    throw new IllegalArgumentException();
	
	    this.moneyInTransaction = this.moneyInTransaction.add(money.amountInEuro());
	    this.moneyInside = this.moneyInside.add(money);
    }
    
    public void returnChange() {
        this.returnChangeFor(this.moneyInTransaction);
    }
    
    private void returnChangeFor(final BigDecimal amount) {
        Money change = this.moneyInside.allocate(amount);
        this.moneyInside = this.moneyInside.subtract(change);
        this.moneyInTransaction = BigDecimal.ZERO;
    }
    
    public void buySnack(final int position) {
        BigDecimal snackPrice = this.getSnackPrice(position);
        if (this.moneyInTransaction.compareTo(snackPrice) == -1)
            throw new IllegalStateException("Not enough money to buy the selected snack");

        Slot slot = this.getSlot(position);
        slot.setSnackPile(slot.getSnackPile().substractOne());
        
        BigDecimal decimalChange = this.moneyInTransaction.subtract(snackPrice);
        this.returnChangeFor(decimalChange);
    }
    
}
