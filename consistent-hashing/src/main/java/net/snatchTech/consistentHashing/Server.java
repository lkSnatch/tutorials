package net.snatchTech.consistentHashing;

import java.util.ArrayList;
import java.util.List;

public class Server {

    private final String name;
    private final List<ServerLabel> labels;

    public Server(String name) {
        this.name = name;
        this.labels = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ServerLabel createLabel() {
        ServerLabel serverLabel = new ServerLabel(this);
        labels.add(serverLabel);
        return serverLabel;
    }

    public List<ServerLabel> getLabels() {
        return labels;
    }

    @Override
    public String toString() {
        return "Server{" +
                "name='" + name + '\'' +
                '}';
    }
}
