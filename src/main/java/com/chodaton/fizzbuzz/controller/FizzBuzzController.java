package com.chodaton.fizzbuzz.controller;

import com.chodaton.fizzbuzz.exception.RequestNotFound;
import com.chodaton.fizzbuzz.model.dto.FrequentRequestDTO;
import com.chodaton.fizzbuzz.model.entity.TruncateRequestParams;
import com.chodaton.fizzbuzz.service.FizzBuzzService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("fizz-buzz")
public class FizzBuzzController {

    @Autowired
    private FizzBuzzService fizzBuzzService;

    @SecurityRequirement(name = "fizzbuzzapi_acces")
    @GetMapping
    @Operation(summary = "Get Truncate Data list",
            description = "Returns a list of strings with numbers from 1 to limit, where: all multiples of int1 are replaced by str1, \n all multiples of int2 are replaced by str2, all multiples of int1 and int2 are replaced by str1str2.")
    @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = String.class)))
            })
    List<String> getTruncateData(@RequestParam Integer int1, @RequestParam Integer int2,
                                 @RequestParam Integer limit, @RequestParam String str1, String str2){
        TruncateRequestParams requestParams =  new TruncateRequestParams(int1, int2, limit, str1, str2);
        this.fizzBuzzService.saveRequest(requestParams);
        return this.fizzBuzzService.getValues(requestParams);
    }

    @SecurityRequirement(name = "fizzbuzzapi_acces")
    @GetMapping(path="frequent-request", produces="application/json")
    @ExceptionHandler(RequestNotFound.class)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Statistics endpoint",
            description = "Return the parameters corresponding to the most used request, as well as the number of hits for this request")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "204", description = "Aucun contenu trouvé", content = {@Content()})
    FrequentRequestDTO getFrequentRequest(){
        return this.fizzBuzzService.getMostFrequentRequest();
    }

}
