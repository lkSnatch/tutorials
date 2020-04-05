package net.snatchTech.consistentHashing;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class App {

    public static void main(String[] args) {

        Server s1 = new Server("S1");
        Server s2 = new Server("S2");
        Server s3 = new Server("S3");

        ConcurrentMap<Server, Cache<String, String>> serverCacheMap = new ConcurrentHashMap<>();
        serverCacheMap.put(s1, new Cache<>());
        serverCacheMap.put(s2, new Cache<>());
        serverCacheMap.put(s3, new Cache<>());

        ServerTree serverTree = new ServerTree();

        // add virtual servers to the hash ring (server tree)
        serverTree.addServer(s1.createLabel());
        serverTree.addServer(s1.createLabel());
        serverTree.addServer(s1.createLabel());

        serverTree.addServer(s2.createLabel());
        serverTree.addServer(s2.createLabel());
        serverTree.addServer(s2.createLabel());

        serverTree.addServer(s3.createLabel());
        serverTree.addServer(s3.createLabel());
        serverTree.addServer(s3.createLabel());

        // fill the cache
        DecisionService<String, String> decisionService = new DecisionService<>(serverTree, serverCacheMap);

        decisionService.putValue("Article 224 of the Law of May 13, 1863", "fully justified");
        decisionService.putValue("Section 202 of the National Securities Act of 1 947", "partially justified");
        decisionService.putValue("section 458-B of the Pure Food and Drug Act", "almost guilty");
        decisionService.putValue("Article 2 of the Act of March 10, 1792", "not guilty");
        decisionService.putValue("Section 412 of the Patriot Act", "we doubt");

        // just print
        System.out.println(serverTree);
        System.out.println(serverCacheMap.get(s1));
        System.out.println(serverCacheMap.get(s2));
        System.out.println(serverCacheMap.get(s3));

        System.out.println(decisionService.getValue("Section 412 of the Patriot Act"));

        System.out.println("----------------------");

        // s3 fails
        s3.getLabels().forEach(serverTree::removeServer);
        //serverCacheMap.remove(s3);

        // just print
        System.out.println(serverTree);
        System.out.println(serverCacheMap.get(s1));
        System.out.println(serverCacheMap.get(s2));
        System.out.println(serverCacheMap.get(s3));

        System.out.println(decisionService.getValue("Section 412 of the Patriot Act"));

        System.out.println("----------------------");

        // and put it again
        decisionService.putValue("Section 412 of the Patriot Act", "we doubt");

        System.out.println(serverCacheMap.get(s1));
        System.out.println(serverCacheMap.get(s2));
        System.out.println(serverCacheMap.get(s3));
        // and check in the cache
        System.out.println(decisionService.getValue("Section 412 of the Patriot Act"));

    }
}
