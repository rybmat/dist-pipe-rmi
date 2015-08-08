package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.*;

public class PipeClient {
    public static void main(String args[]) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "Pipe";
            Registry registry = LocateRegistry.getRegistry("localhost");
//            Compute comp = (Compute) registry.lookup(name);
//            Pi task = new Pi(Integer.parseInt(args[1]));
//            BigDecimal pi = comp.executeTask(task);
            System.out.println("done");
        } catch (Exception e) {
            System.err.println("ComputePi exception:");
            e.printStackTrace();
        }
    }
}