package com.chodaton.fizzbuzz.model.params.entity;

import jakarta.persistence.*;
import lombok.*;



@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "request_params")
 public class TruncateRequestParams {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @Column(name = "valeur_1")
    private Integer int1;

    @NonNull
    @Column(name = "valeur_2")
    private Integer int2;

    @NonNull
    @Column(name = "result_limit")
    private Integer limit;

    @NonNull
    private String str1;

    @NonNull
    private String str2;

    private Integer nbHits;

    @Override
    public String toString(){
       return "int1=" + int1 + "&int2=" + int2 + "&limit="
               + limit + "&str1=" + str1 +  "&str2=" + str2;
    }
}
