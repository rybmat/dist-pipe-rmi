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

        if (args.length < 1) {
            System.out.println("Usage: \njava PipeClient put <pipe name> <message> <close pipe (true/false)> \njava PipeClient get <pipe name>");
        }

        try {
            String name = "Pipe";
            Registry registry = LocateRegistry.getRegistry("localhost");
            Pipe pipe = (Pipe) registry.lookup(name);

            if (args[0].equals("put")) {
                Message<String> msg = new client.Message<>();
                msg.set(args[2]);
                pipe.put(args[1], msg, Boolean.parseBoolean(args[3]));
            } else {
                Message<String> msg = pipe.get(args[1]);
                System.out.println("Got message: " + msg.get());
            }
            System.out.println("done");
        } catch (PipeClosedException e) {
            System.out.println("Pipe " + args[1] + " was closed by sender or never existed");
        } catch (PipeFullException e) {
            System.out.println("Pipe " + args[1] + " is full.");
        } catch (PipeEmtyException e) {
            System.out.println("Pipe " + args[1] + " is empty.");
        } catch (Exception e) {
            System.err.println("exception:");
            e.printStackTrace();
        }
    }
}