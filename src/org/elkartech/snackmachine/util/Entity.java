package org.elkartech.snackmachine.util;

import java.time.Instant;

public abstract class Entity {

    private long id;
    
    
    public Entity() {
        this.id = Instant.now().getEpochSecond();  // Not the best ID gen algorithm in history 
    }
    
    public long getId() {
        return this.id;
    }
    
    @Override
    public boolean equals(final Object object) {
        if (this == object) { // Referential identity
            return true;
        }
        if (!(object == null)) {
            if (this.getClass().isAssignableFrom(object.getClass())) {
                Entity entity = (Entity)object;
                if (this.id != 0 && entity.id != 0) {
                    return this.id == entity.id;
                }
            }
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.getClass().hashCode() * 31 + (int)this.id;
    }
    
}
