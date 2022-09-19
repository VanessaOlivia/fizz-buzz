package com.chodaton.fizzbuzz.model.dto;

import com.chodaton.fizzbuzz.model.entity.TruncateRequestParams;
import com.chodaton.fizzbuzz.util.constant.APINames;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class FrequentRequestDTO {
    String url;
    Integer nbHits;

    public FrequentRequestDTO (TruncateRequestParams params){
        StringBuilder urlBuilder = new StringBuilder("/");
        urlBuilder.append(APINames.FIZZ_BUZ_BASE_API);
        urlBuilder.append("?");
        urlBuilder.append(params.toString());
        url = urlBuilder.toString();
        nbHits = params.getNbHits();
    }
}
