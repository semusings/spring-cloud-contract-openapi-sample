package io.github.bhuwanupadhyay.ordersapijava8.infrastructure;

import io.github.bhuwanupadhyay.ordersapijava8.domain.OrderEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

interface SpringDataJdbcOrderRepository extends PagingAndSortingRepository<OrderEntity, String> {

}