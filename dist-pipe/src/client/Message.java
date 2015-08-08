package client;

import java.io.Serializable;

/**
 * Created by mateusz on 08.08.2015.
 */
public class Message<T> implements common.Message<T>, Serializable{
    private static final long serialVersionUID = 227L;
    private T msg;

    public T get() {
        return msg;
    }

    public void set(T m) {
        msg = m;
    }
}
