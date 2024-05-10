package xyz.zeppelin.bankaccounts.command.commands.sub;

import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.ChatColor;
import xyz.zeppelin.bankaccounts.command.BaseSubCommand;
import xyz.zeppelin.bankaccounts.utils.Message;

public class HelpCommand extends BaseSubCommand {
    @Override
    public CommandAPICommand getCommand() {
        return new CommandAPICommand("help")
                .executesPlayer((player, args) -> {
                    new Message("").messageLine(32, ChatColor.DARK_GRAY, "-").send(player);
                    new Message("&6/bank balances - View all the balances of your bank account.").color().send(player);
                    new Message("&6/bank account [create/delete/list] - Create a new bank account").color().send(player);
                    new Message("&6/bank transfer [accountFrom] [accountTo]").color().send(player);
                    new Message("&6/bank pay [accountFrom] [playerTo]").color().send(player);
                    new Message("&6/bank configure recipientAccount [accountName]").color().send(player);
                    new Message("").messageLine(32, ChatColor.DARK_GRAY, "-").send(player);
                });
    }
}
