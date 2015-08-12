package common;

/**
 * Created by mateusz on 08.08.2015.
 */
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Pipe extends Remote {
    <T> void put(String pipeName, Message<T> m, boolean close) throws RemoteException, PipeFullException, PipeClosedException;
    <T> Message<T> get(String pipeName) throws RemoteException, PipeEmtyException, PipeClosedException;
}
