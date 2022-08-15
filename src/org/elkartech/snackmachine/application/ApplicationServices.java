package org.elkartech.snackmachine.application;

import org.elkartech.snackmachine.domain.Money;
import org.elkartech.snackmachine.domain.SnackMachine;
import org.elkartech.snackmachine.domain.SnackMachineRepository;

public final class ApplicationServices {
    
    private SnackMachineRepository repository;
    
    
    public ApplicationServices(final SnackMachineRepository repository) {
        this.repository = repository;
    }
    
    public void insertMoney(final Money money) {
        SnackMachine snackMachine = repository.findOne().orElseThrow(IllegalStateException::new);
        snackMachine.insertMoney(money);
        repository.save(snackMachine);
    }
    
    public void returnChange() {
        SnackMachine snackMachine = repository.findOne().orElseThrow(IllegalStateException::new);
        snackMachine.returnChange();
        repository.save(snackMachine);
    }
    
    public void buySnack(final int position) {
        SnackMachine snackMachine = repository.findOne().orElseThrow(IllegalStateException::new);
        snackMachine.buySnack(position);
        repository.save(snackMachine);
    }
    
}
