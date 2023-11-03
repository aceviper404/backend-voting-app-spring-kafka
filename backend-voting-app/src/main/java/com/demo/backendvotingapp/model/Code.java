package com.demo.backendvotingapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "codes")
@Data
@Builder
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", unique = true)
    private Integer code;

    @Version
    private int version;

    // Constructors, getters, setters, etc.
}