package com.github.l3nnartt.serverspoofer;

import net.labymod.api.event.events.client.gui.screen.overlay.PlayerTabListOverlayEvent;
import net.labymod.servermanager.ChatDisplayAction;
import net.labymod.servermanager.Server;
import net.labymod.settings.elements.SettingsElement;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.PacketBuffer;
import java.util.List;

public class ServerSupport extends Server {

    public ServerSupport(String... addressNames) {
        super( "ServerSpoofer", addressNames);
    }

    @Override
    public void onJoin(ServerData serverData) {
        ServerSpoofer.getInstance().logInfo("Joined Server: " + serverData.serverIP);
        ServerSpoofer.getInstance().getExService().execute(() -> {
            try {
                ServerSpoofer.getInstance().logInfo("Wait 2 seconds");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ServerSpoofer.getInstance().logInfo("Update status");
            ServerSpoofer.getInstance().updateStatus();
            ServerSpoofer.getInstance().logInfo("Status updatet");
        });
    }

    @Override
    public ChatDisplayAction handleChatMessage(String s, String s1) throws Exception {
        return ChatDisplayAction.NORMAL;
    }

    @Override
    public void handlePayloadMessage(String s, PacketBuffer packetBuffer) throws Exception {

    }

    @Override
    public void handleTabInfoMessage(PlayerTabListOverlayEvent.Type type, String s, String s1) throws Exception {

    }

    @Override
    public void fillSubSettings(List<SettingsElement> list) {

    }
}