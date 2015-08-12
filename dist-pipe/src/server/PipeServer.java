package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import common.*;

public class PipeServer implements Pipe {

    public PipeServer() {
        super();
    }

    private static final int PIPE_SIZE = 3;

    private Map<String, Boolean> closed = new HashMap<>();
    private Map<String, Queue<Message>> pipes =new HashMap<>();

    public <T> Message<T> get(String pipeName) throws PipeEmtyException, PipeClosedException {
        Queue<Message> pipe = this.getPipe(pipeName);

        if (pipe.isEmpty())
            throw new PipeEmtyException();

        else {
            Message<T> msg = pipe.poll();
            if (this.pipeClosed(pipeName) && pipe.isEmpty()) {
                this.pipes.remove(pipeName);
                this.closed.put(pipeName, false);
            }
            return msg;
        }
    }

    public <T> void put(String pipeName, Message<T> message, boolean close) throws PipeFullException, PipeClosedException {
        this.pipes.putIfAbsent(pipeName, new LinkedBlockingQueue<>());
        Queue<Message> pipe = this.getPipe(pipeName);

        if (this.pipeClosed(pipeName)) {
            throw new PipeClosedException();
        } else if (pipe.size() >= PIPE_SIZE)
            throw new PipeFullException();
        else {
            pipe.add(message);
            this.closed.put(pipeName, close);
            System.out.println("added message to " + pipeName);
        }
    }

    private boolean pipeClosed(String name) {
        return Optional.ofNullable(this.closed.get(name)).orElse(false);
    }

    private Queue<Message> getPipe(String name) throws PipeClosedException {
        return Optional.ofNullable(this.pipes.get(name)).orElseThrow(PipeClosedException::new);
    }

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "Pipe";
            Pipe engine = new PipeServer();
            Pipe stub = (Pipe) UnicastRemoteObject.exportObject(engine, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("PipeServer bound");
        } catch (Exception e) {
            System.err.println("PipeServer exception:");
            e.printStackTrace();
        }
    }
}