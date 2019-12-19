package com.funniray.twofa.commands;

import com.funniray.twofa.utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.security.NoSuchAlgorithmException;

public class createKey implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return false;
        }

        //TODO: check permissions
        Player player = (Player) sender;
        String key;
        try {
            key = utils.createKeyForPlayer(player.getUniqueId());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            sender.sendMessage("An error has occured");
            return false;
        }
        TextComponent component = new TextComponent("Click here to see your key");
        component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,
                String.format("https://funniray.github.io/2fa?secret=%s&username=%s",key,player.getName())));
        player.sendMessage(component);
        return true;
    }

}
