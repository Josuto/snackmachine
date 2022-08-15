package org.elkartech.snackmachine.adapters;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.elkartech.snackmachine.domain.Money;
import org.elkartech.snackmachine.domain.Snack;
import org.elkartech.snackmachine.domain.SnackMachine;
import org.elkartech.snackmachine.domain.SnackMachineRepository;
import org.elkartech.snackmachine.domain.SnackPile;

public final class SnackMachineInMemoryRepositoryImpl implements SnackMachineRepository {

    private static final Map<Long, SnackMachine> CONTAINER = new HashMap<>();
    
    
    public SnackMachineInMemoryRepositoryImpl() {
        loadSnackMachines();
    }
    
    private void loadSnackMachines() {
        SnackMachine snackMachine = new SnackMachine();
        snackMachine.loadMoney(new Money(10, 10, 10, 10, 10, 10, 0));
        snackMachine.loadSnackPile(0, new SnackPile(new Snack("Sandwitch", new BigDecimal(1)), 10));
        snackMachine.loadSnackPile(1, new SnackPile(new Snack("Chocolate", new BigDecimal(1.5)), 10));
        snackMachine.loadSnackPile(1, new SnackPile(new Snack("Water", new BigDecimal(2)), 10));
        
        CONTAINER.put(snackMachine.getId(), snackMachine);
    }
    
    @Override
    public Optional<SnackMachine> findById(long id) {
        return Optional.ofNullable(CONTAINER.get(id));
    }
    
    @Override
    public Optional<SnackMachine> findOne() {
        long randomSnackMachineId = ((SnackMachine)CONTAINER.values().toArray()[0]).getId();
        return Optional.ofNullable(CONTAINER.get(randomSnackMachineId));
    }

    @Override
    public void save(SnackMachine snackMachine) {
        CONTAINER.put(snackMachine.getId(), snackMachine);
    }

}
