package org.elkartech.snackmachine.util;

public abstract class ValueObject<T> {

    @Override
    public boolean equals(final Object object) {
        if (this == object) { // Referential identity
            return true;
        }
        if (!(object == null)) {
            if (this.getClass().isAssignableFrom(object.getClass())) {
                @SuppressWarnings("unchecked")
                // The cast is safe since thanks to the previous type check
                T valueObject = (T)object;
                return this.equalsCore(valueObject);
            }
        }
        return false;
    }
    
    protected abstract boolean equalsCore(final T object);
    
    @Override
    public int hashCode() {
        return this.hashCodeCore();
    }
    
    protected abstract int hashCodeCore();
    
}
