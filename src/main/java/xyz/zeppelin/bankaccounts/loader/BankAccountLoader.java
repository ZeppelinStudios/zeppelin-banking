package xyz.zeppelin.bankaccounts.loader;

import org.bukkit.configuration.file.YamlConfiguration;
import xyz.zeppelin.bankaccounts.models.BankAccount;

import java.util.UUID;

public class BankAccountLoader implements ZeppelinLoader<UUID, BankAccount>, EnableDisable {

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    @Override
    public BankAccount load(UUID ref) {
        return null;
    }

    @Override
    public BankAccount all() {
        return null;
    }

    @Override
    public void save(BankAccount obj) {

    }
}
