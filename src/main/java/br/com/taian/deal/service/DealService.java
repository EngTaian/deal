package br.com.taian.deal.service;

import br.com.taian.deal.enumeration.BusinessExceptionCode;
import br.com.taian.deal.enumeration.DealStatus;
import br.com.taian.deal.exception.BusinessException;
import br.com.taian.deal.exception.InvalidAccessException;
import br.com.taian.deal.model.Deal;
import br.com.taian.deal.repository.DealRepository;
import br.com.taian.deal.util.CrudServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DealService extends CrudServiceImpl<DealRepository, Deal> {


    public Page<Deal> findAllByDealStatus(Pageable pageable, DealStatus dealStatus){
        Page<Deal> deals = this.repository.findAllByDealStatus(pageable, dealStatus);
        return deals;
    }

    @Override
    public Deal createElement(Deal deal){
        validateBeforeSave(deal);
        return super.createElement(deal);
    }

    @Override
    public Deal updateElement(Long id, Deal deal){
        validateBeforeSave(deal);
        return super.updateElement(id, deal);
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

    private void validateBeforeSave(Deal deal){
        List<String> errors = new ArrayList<>();

        if(ObjectUtils.isEmpty(deal.getMake())){
            errors.add("DealService.validateBeforeSave Error ::: make can't be null");
        }

        if(ObjectUtils.isEmpty(deal.getModel())){
            errors.add("DealService.validateBeforeSave Error ::: model can't be null");
        }

        if(ObjectUtils.isEmpty(deal.getPrice()) || deal.getPrice() < 1 ){
            errors.add("DealService.validateBeforeSave Error ::: price can't be null or zero");
        }

        if(ObjectUtils.isEmpty(deal.getDealStatus())){
            deal.setDealStatus(DealStatus.ACTIVE);
        }

        if(errors.size() > 0){
            log.error("Validation Error " + errors);
            throw new BusinessException(errors, "Validation Error");
        }
    }

}
