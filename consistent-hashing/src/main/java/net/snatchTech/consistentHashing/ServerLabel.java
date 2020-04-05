package net.snatchTech.consistentHashing;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerLabel {

    private static AtomicInteger counter = new AtomicInteger();

    private final Server server;
    private final String label;
    private final int rangeStart;

    public ServerLabel(Server server) {
        this.server = server;
        int random;
        this.rangeStart = (random = new Random().nextInt()) < 0 ? random * -1 : random;
        this.label = server.getName() + "_" + counter.getAndIncrement();
    }

    public Server getServer() {
        return server;
    }

    public String getLabel() {
        return label;
    }

    public int getRangeStart() {
        return rangeStart;
    }

    @Override
    public String toString() {
        return "(label='" + label + '\'' +
                ", rangeStart=" + rangeStart +
                ')';
    }
}
