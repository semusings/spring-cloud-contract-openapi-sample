package io.github.bhuwanupadhyay.ordersapijava8.domain;

public class EntityNotFoundException extends RuntimeException {

    private final String entityId;

    public EntityNotFoundException(String entityId) {
        super("Entity not found for id: " + entityId);
        this.entityId = entityId;
    }

    public String getEntityId() {
        return entityId;
    }
}
