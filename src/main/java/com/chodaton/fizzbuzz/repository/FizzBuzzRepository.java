package com.chodaton.fizzbuzz.repository;

import com.chodaton.fizzbuzz.model.entity.TruncateRequestParams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FizzBuzzRepository extends JpaRepository<TruncateRequestParams, Long> {

    Optional<TruncateRequestParams> findByInt1AndInt2AndLimitAndStr1AndStr2(Integer int1,
                                                                            Integer int2,
                                                                            Integer limit,
                                                                            String str1,
                                                                            String str2);

    Optional<TruncateRequestParams> findFirstByOrderByNbHitsDesc();

}
