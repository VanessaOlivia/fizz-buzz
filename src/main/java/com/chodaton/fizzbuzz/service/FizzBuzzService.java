package com.chodaton.fizzbuzz.service;

import com.chodaton.fizzbuzz.exception.RequestNotFound;
import com.chodaton.fizzbuzz.model.dto.FrequentRequestDTO;
import com.chodaton.fizzbuzz.model.params.entity.TruncateRequestParams;
import com.chodaton.fizzbuzz.repository.FizzBuzzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FizzBuzzService {

    @Autowired
    FizzBuzzRepository fizzBuzzRepository;

    public List<String> getValues(TruncateRequestParams input) {
        List<String> results = new ArrayList<>();
        for (int i = 1; i <= input.getLimit(); i++) {
            String truncate = String.valueOf(i);
            if(i% input.getInt1() == 0 && i% input.getInt2() == 0){
                truncate = input.getStr1() + input.getStr2();
            }
            else if(i% input.getInt1() == 0){
                truncate = input.getStr1();
            }
            else if(i% input.getInt2() == 0){
                truncate = input.getStr2();
            }
            results.add(truncate);
        }
        return results;
    }

    public void saveRequest(TruncateRequestParams requestParams){
       Optional<TruncateRequestParams> existingRequest = this.findRequestParams(requestParams);
       if(existingRequest.isPresent()){
           requestParams.setNbHits(existingRequest.get().getNbHits() + 1);
           requestParams.setId(existingRequest.get().getId());
       }
       else {
           requestParams.setNbHits(1);
       }
       this.fizzBuzzRepository.save(requestParams);
    }

    public Optional<TruncateRequestParams>  findRequestParams(TruncateRequestParams requestParams){
        return this.fizzBuzzRepository.findByInt1AndInt2AndLimitAndStr1AndStr2(requestParams.getInt1(),
                requestParams.getInt2(), requestParams.getLimit(), requestParams.getStr1(),
                requestParams.getStr2());
    }

    public FrequentRequestDTO getMostFrequentRequest(){
        return this.fizzBuzzRepository.findFirstByOrderByNbHitsDesc().map(FrequentRequestDTO::new)
                .orElseThrow(()->new RequestNotFound());
    }

}
