package io.github.bhuwanupadhyay.ordersapijava8.infrastructure;

import io.github.bhuwanupadhyay.ordersapijava8.domain.EntityNotFoundException;
import io.github.bhuwanupadhyay.ordersapijava8.domain.OrderEntity;
import io.github.bhuwanupadhyay.ordersapijava8.domain.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
class JdbcOrderRepository implements OrderRepository {

    private final SpringDataJdbcOrderRepository repository;

    JdbcOrderRepository(SpringDataJdbcOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<OrderEntity> find(String orderId) {
        return repository.findById(orderId);
    }

    @Override
    public Page<OrderEntity> list(OrderEntity filters, Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public OrderEntity update(String orderId, OrderEntity changed) {
        repository.findById(orderId).orElseThrow(() -> new EntityNotFoundException(orderId));
        return repository.save(changed);
    }

    @Override
    public void delete(String orderId) {
        repository.deleteById(orderId);
    }

    @Override
    public OrderEntity save(OrderEntity entity) {
        return repository.save(entity);
    }
}
