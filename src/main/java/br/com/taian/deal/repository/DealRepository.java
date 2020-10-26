package br.com.taian.deal.repository;

import br.com.taian.deal.enumeration.DealStatus;
import br.com.taian.deal.model.Deal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DealRepository extends JpaRepository<Deal, Long> {


    Page<Deal> findAllByDealStatus(Pageable pageable, DealStatus dealStatus);

}
