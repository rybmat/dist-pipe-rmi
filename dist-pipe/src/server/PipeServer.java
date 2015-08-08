package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import common.*;

public class PipeServer implements Pipe {

    public PipeServer() {
        super();
    }

    private static final int PIPE_SIZE = 10;
    public Queue<Message> pipe = new LinkedBlockingQueue<>();

    public <T> Message<T> get() throws PipeEmtyException {
        if (pipe.isEmpty())
            throw new PipeEmtyException();
        else
            return pipe.poll();

    }

    public <T> void put(Message<T> message) throws PipeFullException {
        if (pipe.size() >= PIPE_SIZE)
            throw new PipeFullException();
        else {
            pipe.add(message);
            System.out.println("added message");
        }
    }

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "Pipe";
            Pipe engine = new PipeServer();
            Pipe stub =
                    (Pipe) UnicastRemoteObject.exportObject(engine, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("PipeServer bound");
        } catch (Exception e) {
            System.err.println("PipeServer exception:");
            e.printStackTrace();
        }
    }
}