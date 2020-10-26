package br.com.taian.deal.service;

import br.com.taian.deal.enumeration.DealStatus;
import br.com.taian.deal.exception.BusinessException;
import br.com.taian.deal.model.Deal;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ObjectUtils;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DealServiceTest {
    @Autowired
    DealService dealService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void findDealsPageable(){
        Pageable pageable = PageRequest.of(0, 20);
        Page<Deal> dealPage = dealService.findAll(pageable);
        Assertions.assertThat(dealPage.get()).isNotNull();
        Assertions.assertThat(dealPage.getSize()).isEqualTo(20);
    }

    @Test
    public void findDealsByDealStatus(){
        Pageable pageable = PageRequest.of(0, 20);
        Page<Deal> deals = dealService.findAllByDealStatus(pageable,DealStatus.ACTIVE);
        Assertions.assertThat(deals).isNotNull();
    }

    @Test
    public void createDealAndShouldReturn(){
        Deal deal = dealService.createElement(getData());
        Assertions.assertThat(deal.getId()).isNotZero();
        Assertions.assertThat(deal.getMake()).isEqualToIgnoringCase("chevrolet");
        Assertions.assertThat(deal.getModel()).isEqualToIgnoringCase("onix 1.4");
        Assertions.assertThat(deal.getPrice()).isNotZero();
        Assertions.assertThat(deal.getCreatedAt()).isNotNull();
    }

    @Test
    public void createDealAndShoulReturnErrorWithMakeNull(){
        thrown.expect(BusinessException.class);
        Deal deal = getData();
        deal.setMake(null);
        dealService.createElement(deal);
    }

    @Test
    public void createDealAndShoulReturnErrorWithModelNull(){
        thrown.expect(BusinessException.class);
        Deal deal = getData();
        deal.setModel(null);
        dealService.createElement(deal);
    }

    @Test
    public void createDealAndShoulReturnErrorWithPriceNull(){
        thrown.expect(Exception.class);
        Deal deal = getData();
        deal.setPrice(null);
        dealService.createElement(deal);
    }

    @Test
    public void createDealAndShoulReturnErrorWithPriceIsZero(){
        thrown.expect(Exception.class);
        Deal deal = getData();
        deal.setPrice(0D);
        dealService.createElement(deal);
    }

    @Test
    public void updateDealAndShouldReturnDealUpdated(){
        List<Deal> dealList = dealService.findAll();
        Deal dealUpdated = dealList.get(0);
        if(!ObjectUtils.isEmpty(dealUpdated)){
            dealUpdated.setPrice(5000.00);
            dealService.updateElement(dealUpdated.getId(), dealUpdated);
        }
    }

    @Test
    public void updateDealAndShouldReturnErrorWithMakeIsNull(){
        thrown.expect(BusinessException.class);
        List<Deal> dealList = dealService.findAll();
        Deal dealUpdated = dealList.get(0);
        if(!ObjectUtils.isEmpty(dealUpdated)){
            dealUpdated.setMake(null);
            dealService.updateElement(dealUpdated.getId(), dealUpdated);
        }
    }

    @Test
    public void updateDealAndShouldReturnErrorWithModelIsNull(){
        thrown.expect(BusinessException.class);
        List<Deal> dealList = dealService.findAll();
        Deal dealUpdated = dealList.get(0);
        if(!ObjectUtils.isEmpty(dealUpdated)){
            dealUpdated.setModel(null);
            dealService.updateElement(dealUpdated.getId(), dealUpdated);
        }
    }

    @Test
    public void updateDealAndShouldReturnErrorWithPriceIsNull(){
        thrown.expect(BusinessException.class);
        List<Deal> dealList = dealService.findAll();
        Deal dealUpdated = dealList.get(0);
        if(!ObjectUtils.isEmpty(dealUpdated)){
            dealUpdated.setPrice(null);
            dealService.updateElement(dealUpdated.getId(), dealUpdated);
        }
    }

    @Test
    public void updateDealAndShouldReturnErrorWithPriceIsZero(){
        thrown.expect(BusinessException.class);
        List<Deal> dealList = dealService.findAll();
        Deal dealUpdated = dealList.get(0);
        if(!ObjectUtils.isEmpty(dealUpdated)){
            dealUpdated.setPrice(0D);
            dealService.updateElement(dealUpdated.getId(), dealUpdated);
        }
    }

    @Test
    public void deleteDeal(){
        List<Deal> dealList = dealService.findAll();
        for(Deal deal : dealList){
            dealService.updateStatus(deal.getId(), DealStatus.INACTIVE);
        }
    }

    private Deal getData(){
        Deal deal = new Deal();
        deal.setMake("Chevrolet");
        deal.setModel("Onix 1.4");
        deal.setPrice(49999.99);
        return deal;
    }

}

