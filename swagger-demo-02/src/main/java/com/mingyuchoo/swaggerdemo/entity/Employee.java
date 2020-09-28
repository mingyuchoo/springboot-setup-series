package com.mingyuchoo.swaggerdemo.entity;

import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated product ID")
    public Long id;

    @ApiModelProperty(notes = "The first name of a employee")
    public String firstName;

    @ApiModelProperty(notes = "The last name of a employee")
    public String lastName;

    @ApiModelProperty(notes = "The e-mail address of a employee")
    public String emailId;

    public Employee() {}

    public Employee(String firstName, String lastName, String emailId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
    }

    @Override
    public String toString() {
        return String.format(
                "[id=%d, firstname=%s, lastName=%s, emailId=%s]",
                this.id, this.firstName, this.lastName, this.emailId);
    }
}
