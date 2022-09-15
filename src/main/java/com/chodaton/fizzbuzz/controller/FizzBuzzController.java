package com.chodaton.fizzbuzz.controller;

import com.chodaton.fizzbuzz.exception.RequestNotFound;
import com.chodaton.fizzbuzz.model.dto.FrequentRequestDTO;
import com.chodaton.fizzbuzz.model.params.entity.TruncateRequestParams;
import com.chodaton.fizzbuzz.service.FizzBuzzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("fizz-buzz")
public class FizzBuzzController {

    @Autowired
    private FizzBuzzService fizzBuzzService;

    @GetMapping
    List<String> getTruncateData(@RequestParam Integer int1, @RequestParam Integer int2,
                                 @RequestParam Integer limit, @RequestParam String str1, String str2){
        TruncateRequestParams requestParams =  new TruncateRequestParams(int1, int2, limit, str1, str2);
        this.fizzBuzzService.saveRequest(requestParams);
        return this.fizzBuzzService.getValues(requestParams);
    }

    @GetMapping(path="frequent-request", produces="application/json")
    @ExceptionHandler(RequestNotFound.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    FrequentRequestDTO getFrequentRequest(){
        return this.fizzBuzzService.getMostFrequentRequest();
    }

}
