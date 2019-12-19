package com.funniray.twofa.commands;

import com.funniray.twofa.utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.security.InvalidKeyException;

public class authenticate implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return false;
        }

        if (args.length < 1) {
            sender.sendMessage("You must supply a code.");
            return false;
        }

        //TODO: check permissions
        Player player = (Player) sender;
        int code = Integer.parseInt(args[0]);
        try {
            if (utils.validateCode(code,player)) {
                player.sendMessage("Valid code!");
                return true;
            } else {
                player.sendMessage("Invalid code");
                return true;
            }
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            sender.sendMessage("An error has occured");
            return false;
        }
    }
}
