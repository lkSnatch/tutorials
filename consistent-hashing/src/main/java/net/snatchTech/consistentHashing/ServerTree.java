package net.snatchTech.consistentHashing;

import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ServerTree {

    private final ConcurrentNavigableMap<Integer, ServerLabel> serverTree;

    public ServerTree() {
        this.serverTree = new ConcurrentSkipListMap<>();
    }

    public void addServer(ServerLabel server) {
        serverTree.put(server.getRangeStart(), server);
    }

    public void removeServer(ServerLabel server) {
        serverTree.remove(server.getRangeStart());
    }

    public Server getServer(int hash) {

        Map.Entry<Integer, ServerLabel> entry = serverTree.lowerEntry(hash);
        if (entry == null)
            entry = serverTree.lastEntry();

        return entry.getValue().getServer();
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("Server tree: ");
        serverTree.forEach((integer, serverLabel) -> {
            sb.append(serverLabel).append(" ");
        });

        return sb.toString();
    }
}
