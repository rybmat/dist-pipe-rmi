package common;

/**
 * Created by mateusz on 08.08.2015.
 */
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Pipe extends Remote {
    <T> void put(Message<T> m) throws RemoteException, PipeFullException;
    <T> T get() throws RemoteException, PipeEmtyException;
}