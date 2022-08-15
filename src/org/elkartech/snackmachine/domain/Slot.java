package org.elkartech.snackmachine.domain;

import org.elkartech.snackmachine.util.Entity;

public final class Slot extends Entity {
    
    private int position;
    private SnackPile snackPile;
    

    Slot(final int position, final SnackPile snackPile) {
    	this.position = position;
    	this.snackPile = snackPile;
    }

    public int getPosition() {
        return this.position;
    }

    public SnackPile getSnackPile() {
        return this.snackPile;
    }
    
    void setSnackPile(final SnackPile snackPile) {
        this.snackPile = snackPile;
    }

}
