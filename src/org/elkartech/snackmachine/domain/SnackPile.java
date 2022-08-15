package org.elkartech.snackmachine.domain;

import org.elkartech.snackmachine.util.ValueObject;

public final class SnackPile extends ValueObject<SnackPile> {

    private Snack snack;
    private int amount;
    
    
    public SnackPile(final Snack snack, final int amount) {
        if (amount < 0) 
            throw new IllegalArgumentException();
        
        this.snack = snack;
        this.amount = amount;
    }
    
    public Snack getSnack() {
        return this.snack;
    }

    public int getAmount() {
        return this.amount;
    }

    public SnackPile substractOne() {
        if (this.amount == 0) {
            throw new IllegalStateException("There are no snacks left in the slot");
        }
        
        return new SnackPile(this.snack, this.amount - 1);
    }
    
    @Override
    protected boolean equalsCore(final SnackPile snackPile) {
        return this.snack.equals(snackPile.snack) && this.amount == snackPile.amount;
    }

    @Override
    protected int hashCodeCore() {
        int hashCode = this.snack.hashCode();
        hashCode = 31 * hashCode + this.amount;
        return hashCode;
    }

}
