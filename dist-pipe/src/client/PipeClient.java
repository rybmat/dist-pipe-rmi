package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.*;
import common.Message;

public class PipeClient {
    public static void main(String args[]) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "Pipe";
            Registry registry = LocateRegistry.getRegistry("localhost");
            Pipe pipe = (Pipe) registry.lookup(name);

//            Message<String> msg = new client.Message<>();
//            msg.set("fsdafsadfa");
//            pipe.put(msg);

            Message<String> msg = pipe.get();
            System.out.println(msg.get());

//            Pi task = new Pi(Integer.parseInt(args[1]));
//            BigDecimal pi = comp.executeTask(task);
            System.out.println("done");
        } catch (Exception e) {
            System.err.println("exception:");
            e.printStackTrace();
        }
    }
}