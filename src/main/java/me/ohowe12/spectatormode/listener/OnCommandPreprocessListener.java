/*
 * MIT License
 *
 * Copyright (c) 2021 carelesshippo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN
 */

package me.ohowe12.spectatormode.listener;

import me.ohowe12.spectatormode.SpectatorMode;
import me.ohowe12.spectatormode.util.Messenger;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class OnCommandPreprocessListener implements Listener {

    private final SpectatorMode plugin;

    public OnCommandPreprocessListener(SpectatorMode plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCommandEvent(final PlayerCommandPreprocessEvent e) {
        final Player player = e.getPlayer();

        if (player.hasPermission("smpspectator.bypass")) {
            return;
        }
        if (!plugin.getSpectatorManager().getStateHolder().hasPlayer(player)) {
            return;
        }

        String rawCommand =
                e.getMessage().substring(1).split(" ")[0]; // /back -> back || /essentials:back ->
        // essentials:back
        String[] splited = rawCommand.split(":");

        rawCommand = splited[splited.length - 1];
        if (plugin.getConfigManager().getList("bad-commands").contains(rawCommand)) {
            Messenger.send(player, "bad-command-message");
            e.setCancelled(true);
        }
    }
}
