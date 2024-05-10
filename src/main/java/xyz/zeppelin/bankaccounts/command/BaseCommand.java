package xyz.zeppelin.bankaccounts.command;

import dev.jorel.commandapi.CommandAPICommand;
import xyz.zeppelin.bankaccounts.BankPlugin;

public abstract class BaseCommand {

    public abstract CommandAPICommand getCommand();

    public BaseCommand() {

    }

    public void register() {
        getCommand().register();
        BankPlugin.getInstance().getLogger().info("Registered Command: " + getClass().getName());
    }
}
