package xyz.zeppelin.bankaccounts.command.commands;

import dev.jorel.commandapi.CommandAPICommand;
import xyz.zeppelin.bankaccounts.BankPlugin;
import xyz.zeppelin.bankaccounts.command.BaseCommand;
import xyz.zeppelin.bankaccounts.menu.menus.HomeMenu;

public class BankCommand extends BaseCommand {

    private static BankPlugin plugin;

    public BankCommand() {
        plugin = BankPlugin.getInstance();
    }


    @Override
    public CommandAPICommand getCommand() {
        return new CommandAPICommand("bank")
                .withSubcommands(
                )
                .executesPlayer((player, args) -> {
                    new HomeMenu(player).openGui(player);
                });
    }

}
