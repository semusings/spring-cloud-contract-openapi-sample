package io.github.bhuwanupadhyay.springcloudcontractopenapisample.infrastructure;

import io.github.bhuwanupadhyay.springcloudcontractopenapisample.domain.OrderEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

interface SpringDataJdbcOrderRepository extends PagingAndSortingRepository<OrderEntity, String> {

}