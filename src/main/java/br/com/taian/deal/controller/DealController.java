package br.com.taian.deal.controller;

import br.com.taian.deal.dto.DealDto;
import br.com.taian.deal.enumeration.DealStatus;
import br.com.taian.deal.model.Deal;
import br.com.taian.deal.service.DealService;
import br.com.taian.deal.util.BaseController;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.reflect.TypeToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(value = "Deal Controller", tags = {"deal"})
@Slf4j
@RestController
@RequestMapping("/deal/api")
public class DealController extends BaseController<DealService, Deal, DealDto> {



    @GetMapping("/v1.0/all/pageable")
    public ResponseEntity findDealPageable(@RequestParam(required = false) DealStatus dealStatus,
                                           @RequestParam(required = false, defaultValue = "0") Integer page,
                                           @RequestParam(required = false, defaultValue = "20") Integer size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Deal> dealPage;
        if(!ObjectUtils.isEmpty(dealStatus)) {
            dealPage = this.service.findAllByDealStatus(pageable, dealStatus);
        }else{
            dealPage = this.service.findAllByDealStatus(pageable, DealStatus.ACTIVE);
        }
        List<DealDto> dtos = dealPage.get().map(deal->convertToDetailDto(deal)).collect(Collectors.toList());
        return ResponseEntity.ok(new PageImpl<>(dtos, pageable, dealPage.getTotalElements()));
    }

    @Override
    @PostMapping("/v1.0")
    public ResponseEntity createElement(DealDto dealDto){
        try {
            Deal deal = this.service.createElement(convertToModel(dealDto));
            ObjectNode response = objectMapper.createObjectNode();
            response.put("id", deal.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e){
            log.error("Create deal error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @Override
    @DeleteMapping("/v1.0/{id}")
    public ResponseEntity deleteElement(@PathVariable Long id){
        try{
            this.service.updateStatus(id, DealStatus.INACTIVE);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            log.error("Create deal error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @ApiOperation("update deal status")
    @PutMapping("/v1.0/{id}/status")
    public ResponseEntity updateDealStatus(@PathVariable Long id, @RequestParam Long dealerId,@RequestParam DealStatus dealStatus){
        try{
            Deal deal = this.service.updateStatus(id, dealStatus);
            ObjectNode response = objectMapper.createObjectNode();
            response.put("id", deal.getId());
            return ResponseEntity.ok(response);
        }catch (Exception ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @Override
    protected List<DealDto> convertToListDto(List<Deal> deals) {
        return modelMapper.map(deals, new TypeToken<List<DealDto>>() {}.getType());
    }

    @Override
    protected DealDto convertToDetailDto(Deal deal) {
        return modelMapper.map(deal, DealDto.class);
    }

    @Override
    protected Deal convertToModel(DealDto dealDto) {
        return modelMapper.map(dealDto, Deal.class);
    }
}
