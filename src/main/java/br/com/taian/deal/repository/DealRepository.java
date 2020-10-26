package br.com.taian.deal.repository;

import br.com.taian.deal.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DealRepository extends JpaRepository<Deal, Long> {

    Optional<Deal> findDealByIdAndDealerId(Long id, Long dealerId);
}
