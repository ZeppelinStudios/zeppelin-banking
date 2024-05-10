package xyz.zeppelin.bankaccounts.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@AllArgsConstructor
@Getter
public class Message {
    private String message;

    public Message color() {
        this.message = ChatColor.translateAlternateColorCodes('&', message);
        return this;
    }

    public Message messageLine(int length, ChatColor color, String character) {
        StringBuilder line = new StringBuilder();

        for (int i = 0; i < length; i++) {
            line.append(character);
        }

        this.message = color + line.toString();

        return this;
    }

    public Message placeholder(String placeholder, String replacement) {
        this.message = message.replaceAll(placeholder, replacement);
        return this;
    }

    public void send(Player player) {
        player.sendMessage(getMessage());
    }

}