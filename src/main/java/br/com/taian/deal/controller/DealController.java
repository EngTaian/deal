package br.com.taian.deal.controller;

import br.com.taian.deal.dto.DealDto;
import br.com.taian.deal.enumeration.DealStatus;
import br.com.taian.deal.exception.InvalidAccessException;
import br.com.taian.deal.model.Deal;
import br.com.taian.deal.service.DealService;
import br.com.taian.deal.util.BaseController;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.reflect.TypeToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Deal Controller", tags = {"deal"})
@Slf4j
@RestController
@RequestMapping("/deal/api")
public class DealController extends BaseController<DealService, Deal, DealDto> {
    

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

    @ApiOperation("update deal status")
    @PutMapping("/v1.0/{id}/status")
    public ResponseEntity updateDealStatus(@PathVariable Long id, @RequestParam Long dealerId,@RequestParam DealStatus dealStatus){
        try{
            this.service.validadeAccess(id, dealerId);
            Deal deal = this.service.updateStatus(id, dealStatus);
            ObjectNode response = objectMapper.createObjectNode();
            response.put("id", deal.getId());
            return ResponseEntity.ok(response);
        }catch (InvalidAccessException invalidAccessException){
            log.error(String.format("Dealer %s can't alter this deal %s", dealerId, id));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(invalidAccessException.getMessage());
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
