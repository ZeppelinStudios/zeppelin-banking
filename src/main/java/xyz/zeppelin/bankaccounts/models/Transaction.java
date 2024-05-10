package xyz.zeppelin.bankaccounts.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class Transaction {

    private UUID uuid;
    private String data;

}
