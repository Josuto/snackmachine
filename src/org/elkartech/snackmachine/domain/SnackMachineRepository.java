package org.elkartech.snackmachine.domain;

import java.util.Optional;

public interface SnackMachineRepository {
    
    public Optional<SnackMachine> findById(final long id);
    
    public Optional<SnackMachine> findOne();
    
    public void save(final SnackMachine snackMachine);

}
