package com.mingyuchoo.pgsqldemo.entity;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address implements Serializable {
    private static final long serialVersionUID = 6205985970353546844L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fields;

    @OneToOne @MapsId private Person person;

    public Address() {}

    public Address(String fields) {
        super();
        this.fields = fields;
    }

    public Address(String fields, Person person) {
        super();
        this.fields = fields;
        this.person = person;
    }

    @Override
    public String toString() {
        return String.format("Address[id=%d, fields=%s]", this.id, this.fields);
    }
}
