package org.example.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "account")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "owner_name")
    private String ownerName;
    @Column(name = "balance")
    private double balance;
    @Column(name = "access_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate accessTime;
    @Column(name = "locked")
    private boolean locked;

    public Account(String ownerName, double balance, LocalDate accessTime, boolean locked) {
        this.ownerName = ownerName;
        this.balance = balance;
        this.accessTime = accessTime;
        this.locked = locked;
    }
}
