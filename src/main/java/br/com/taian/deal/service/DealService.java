package br.com.taian.deal.service;

import br.com.taian.deal.enumeration.DealStatus;
import br.com.taian.deal.exception.BusinessException;
import br.com.taian.deal.exception.InvalidAccessException;
import br.com.taian.deal.model.Deal;
import br.com.taian.deal.repository.DealRepository;
import br.com.taian.deal.util.CrudServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Slf4j
@Service
public class DealService extends CrudServiceImpl<DealRepository, Deal> {

    public void validadeAccess(Long dealId, Long dealerId){
        Optional<Deal> optionalDeal = this.repository.findDealByIdAndDealerId(dealId, dealerId);
        if(!optionalDeal.isPresent()){
            log.error(String.format("Dealer %s can't access this deal %s", dealerId, dealId));
            throw new InvalidAccessException(String.format("Dealer %s can't access this deal %s", dealerId, dealId)  );
        }
    }

    public Deal updateStatus(Long id, DealStatus dealStatus){
        Deal deal = findById(id);
        if(ObjectUtils.isEmpty(deal)){
            log.error("Could not found order by id " + id);
            throw new BusinessException("Could not found order by id " + id);
        }
        deal.setDealStatus(dealStatus);
        return this.repository.saveAndFlush(deal);
    }

}
