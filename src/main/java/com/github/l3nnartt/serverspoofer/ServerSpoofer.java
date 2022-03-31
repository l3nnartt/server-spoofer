package com.github.l3nnartt.serverspoofer;

import net.labymod.api.LabyModAddon;
import net.labymod.labyconnect.packets.PacketPlayServerStatus;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Consumer;
import net.labymod.utils.ServerData;

import java.util.List;

public class ServerSpoofer extends LabyModAddon {

    private static String server;

    @Override
    public void onEnable() {
        api.getEventService().registerListener((Consumer<ServerData>) data -> ServerSpoofer.this.updateStatus());
    }

    @Override
    public void loadConfig() {
        if (getConfig().has("status")) {
          server = getConfig().has( "server" ) ? getConfig().get( "server" ).getAsString() : "play.timolia.de";
        }
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {

    }


    public void updateStatus() {
      PacketPlayServerStatus packet = new PacketPlayServerStatus(server, 25565, "");
      api.getLabyModChatClient().getClientConnection().sendPacket(packet);
    }
}
