package org.elkartech.snackmachine.domain;

import java.math.BigDecimal;

import org.elkartech.snackmachine.util.AggregateRoot;

public final class Snack extends AggregateRoot {

    private String name;
    private BigDecimal price;
    
    
    public Snack(final String name, final BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) == -1)
            throw new IllegalArgumentException(
                    "The price cannot be negative");
        if (price.remainder(new BigDecimal("0.01")).compareTo(BigDecimal.ZERO) == 1)
            throw new IllegalArgumentException(
                    "Cannot set a price that cannot be payed with current currency coins and bills");
        
    	this.name = name;
    	this.price = price;
    }
    
    public String getName() {
        return this.name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }
    
}
