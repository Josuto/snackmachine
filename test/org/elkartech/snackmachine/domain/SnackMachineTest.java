package org.elkartech.snackmachine.domain;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.*;
import static org.elkartech.snackmachine.domain.Money.*;

import java.math.BigDecimal;


public class SnackMachineTest {

    @Test
    public void testInsertValidMoneyPass() {
    	SnackMachine snackMachine = new SnackMachine();
    	
    	assertThat(snackMachine.getMoneyInside()).isEqualTo(NO_MONEY);
    	assertThat(snackMachine.getMoneyInTransaction()).isEqualByComparingTo(BigDecimal.ZERO);
    	
    	snackMachine.insertMoney(ONE_EURO);
    	
    	assertThat(snackMachine.getMoneyInside()).isEqualTo(ONE_EURO);
    	assertThat(snackMachine.getMoneyInTransaction()).isEqualByComparingTo(BigDecimal.ONE);
    }
    
    @Test
    public void testInsertNullMoneyFail() {
    	SnackMachine snackMachine = new SnackMachine();
    	
    	assertThatIllegalArgumentException().isThrownBy(() -> {
    	    snackMachine.insertMoney(null);
    	});
    }
    
    @Test
    public void testInsertInvalidMoneyFail() {
        SnackMachine snackMachine = new SnackMachine();
        
        assertThatIllegalArgumentException().isThrownBy(() -> {
            snackMachine.insertMoney(new Money(1, 2, 3, 4, 5, 6, 7));
        });
    }
    
    @Test
    public void testReturnInsertedMoneyPass() {
    	SnackMachine snackMachine = new SnackMachine();
    	
    	snackMachine.insertMoney(ONE_EURO);
    	snackMachine.returnChange();
    	
    	assertThat(snackMachine.getMoneyInside()).isEqualTo(NO_MONEY);
    	assertThat(snackMachine.getMoneyInTransaction()).isEqualByComparingTo(BigDecimal.ZERO);
    }
    
    @Test
    public void testReturnNotInsertedMoneyPass() {
        SnackMachine snackMachine = new SnackMachine();
        
        snackMachine.returnChange();
        
        assertThat(snackMachine.getMoneyInTransaction()).isEqualByComparingTo(BigDecimal.ZERO);
    }
    
    @Test
    public void testBuySnackPass() {
    	SnackMachine snackMachine = new SnackMachine();
    	Snack sandwitch = new Snack("Sandwitch", BigDecimal.ONE);
    	snackMachine.loadSnackPile(0, new SnackPile(sandwitch, 10));
    	
    	snackMachine.insertMoney(ONE_EURO);
    	snackMachine.buySnack(0);
    	
    	assertThat(snackMachine.getSnackPile(0).getAmount()).isEqualTo(9);
    	assertThat(snackMachine.getSnackPile(0).getSnack()).isEqualTo(sandwitch);
    	assertThat(snackMachine.getMoneyInside()).isEqualTo(ONE_EURO);
    	assertThat(snackMachine.getMoneyInTransaction()).isEqualByComparingTo(BigDecimal.ZERO);
    }
    
    @Test
    public void testBuySnackInsufficientFundsFail() {
        SnackMachine snackMachine = new SnackMachine();
        Snack sandwitch = new Snack("Sandwitch", BigDecimal.ONE);
        snackMachine.loadSnackPile(0, new SnackPile(sandwitch, 10));
        
        snackMachine.insertMoney(FIVE_CENTS);
        
        assertThatIllegalStateException().isThrownBy(() -> {
            snackMachine.buySnack(0);
        });
    }
    
    @Test
    public void testBuySnackFromEmptySlotFail() {
        SnackMachine snackMachine = new SnackMachine();
        Snack sandwitch = new Snack("Sandwitch", BigDecimal.ONE);
        snackMachine.loadSnackPile(0, new SnackPile(sandwitch, 1));
        
        snackMachine.insertMoney(ONE_EURO);
        snackMachine.buySnack(0);
        snackMachine.insertMoney(ONE_EURO);
        
        assertThatIllegalStateException().isThrownBy(() -> {
            snackMachine.buySnack(0);            
        });
    }
    
    @Test
    public void testReturnChangePass() {
        SnackMachine snackMachine = new SnackMachine();
        snackMachine.loadMoney(ONE_EURO);
        
        snackMachine.insertMoney(TWENTY_CENTS);
        snackMachine.insertMoney(TWENTY_CENTS);
        snackMachine.insertMoney(TWENTY_CENTS);
        snackMachine.insertMoney(TWENTY_CENTS);
        snackMachine.insertMoney(TWENTY_CENTS);
        snackMachine.returnChange();
        
        assertThat(snackMachine.getMoneyInside().getTwentyCentsCoinAmount()).isEqualTo(5);
        assertThat(snackMachine.getMoneyInside().getOneEuroCoinAmount()).isEqualTo(0);
        
    }
    
}
