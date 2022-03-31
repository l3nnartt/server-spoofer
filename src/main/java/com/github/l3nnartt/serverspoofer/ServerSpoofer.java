package com.github.l3nnartt.serverspoofer;

import net.labymod.api.LabyModAddon;
import net.labymod.labyconnect.packets.PacketPlayServerStatus;
import net.labymod.settings.elements.SettingsElement;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerSpoofer extends LabyModAddon {

    private final ExecutorService exService = Executors.newSingleThreadExecutor();
    private String server;
    private static ServerSpoofer instance;

    // Logger
    private static final Logger LOGGER = Logger.getLogger("ServerSpoofer");
    private final String PREFIX = "[ServerSpoofer-1.16] ";

    @Override
    public void onEnable() {
        // addon instance
        instance = this;

        // show all log levels
        LOGGER.getParent().getHandlers()[0].setLevel(Level.ALL);

        // register serversupport to spoof server (stolen from bugfixes)
        exService.execute(new ServerFetcher(servers -> api.registerServerSupport(this, new ServerSupport(servers))));

        // successful started
        LOGGER.log(Level.INFO, PREFIX + "Addon successful activated");
    }

    @Override
    public void loadConfig() {
        server = getConfig().has("server") ? getConfig().get("server").getAsString() : "play.timolia.de";
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {

    }

    // method to spoof server
    public void updateStatus() {
        PacketPlayServerStatus packet = new PacketPlayServerStatus(server, 25565);
        api.getLabyModChatClient().getClientConnection().sendPacket(packet);
    }

    public void logInfo(String log) {
        LOGGER.log(Level.INFO, PREFIX + log);
    }

    public static ServerSpoofer getInstance() {
        return instance;
    }

    public ExecutorService getExService() {
        return exService;
    }
}