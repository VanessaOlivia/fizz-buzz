package com.chodaton.fizzbuzz.service;

import com.chodaton.fizzbuzz.exception.RequestNotFound;
import com.chodaton.fizzbuzz.model.dto.FrequentRequestDTO;
import com.chodaton.fizzbuzz.model.entity.TruncateRequestParams;
import com.chodaton.fizzbuzz.repository.FizzBuzzRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
 class FizzBuzzServiceTests {

    @InjectMocks
    FizzBuzzService fizzBuzzService;
    @Spy
    FizzBuzzRepository fizzBuzzRepository;
    @Captor
    ArgumentCaptor<TruncateRequestParams> paramsCaptor1;
    @Captor
    ArgumentCaptor<TruncateRequestParams> paramsCaptor2;

    @BeforeAll
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenParams_whenGetValues_thenReturnTruncateData(){
        //Given
        TruncateRequestParams params = new TruncateRequestParams(2, 3, 15, "fizz", "buzz");

        //When
        String[] truncateData = this.fizzBuzzService.getValues(params).toArray(new String[0]);
        String[] expectedData = new String[]{"1", "fizz", "buzz", "fizz", "5", "fizzbuzz", "7", "fizz", "buzz", "fizz", "11", "fizzbuzz", "13", "fizz", "buzz"};

        //Then
        Assert.assertArrayEquals(expectedData, truncateData);
    }

    @Test
    void givenExistingParams_whenSaveRequest_thenIncrementNbHits(){
        //Mise en place Bouchon accès données
        TruncateRequestParams existingParams = new TruncateRequestParams(4,5,20,"fizz","buzz");
        existingParams.setNbHits(8);
        when(this.fizzBuzzRepository.findByInt1AndInt2AndLimitAndStr1AndStr2(4,5,20, "fizz", "buzz"))
                .thenReturn(Optional.of(existingParams));

        //Given
        TruncateRequestParams params = new TruncateRequestParams(4,5,20,"fizz","buzz");

        //When
        this.fizzBuzzService.saveRequest(params);

        //Then
        Mockito.verify(this.fizzBuzzRepository).save(paramsCaptor1.capture());
        TruncateRequestParams paramsToSave = paramsCaptor1.getValue();
        Assertions.assertEquals(9, paramsToSave.getNbHits());
    }

    @Test
    void givenNotExistingParams_whenSaveRequest_thenSetNbHitsTo1(){
        //Bouchon accès données
        when(this.fizzBuzzRepository.findByInt1AndInt2AndLimitAndStr1AndStr2(6,7,50, "toto", "tata"))
                .thenReturn(empty());

        //Given
        TruncateRequestParams params = new TruncateRequestParams(6,7,50, "toto", "tata");

        //When
        this.fizzBuzzService.saveRequest(params);

        //Then
        Mockito.verify(this.fizzBuzzRepository).save(paramsCaptor2.capture());
        TruncateRequestParams paramsToSave = paramsCaptor2.getValue();
        Assertions.assertEquals(1, paramsToSave.getNbHits());
    }

    @Test
    void givenNotExistingParams_whenGetMostFrequentRequest_thenThrowNotFound() throws RequestNotFound{
        Assertions.assertThrows(RequestNotFound.class, () -> {
            this.fizzBuzzService.getMostFrequentRequest();
        });
    }

    @Test
    void givenExistingTopRequest_whenGetMostFrequentRequest_thenReturnDTO() throws RequestNotFound{
        //Given
        TruncateRequestParams expectedParams = new TruncateRequestParams(2, 3, 15, "fizz", "buzz");
        when(this.fizzBuzzRepository.findFirstByOrderByNbHitsDesc())
                .thenReturn(of(expectedParams));
        //when
        FrequentRequestDTO frequentRequestDTO = this.fizzBuzzService.getMostFrequentRequest();

        //then
        assertThat(frequentRequestDTO).usingRecursiveComparison()
                .isEqualTo(new FrequentRequestDTO(expectedParams));
    }

}
