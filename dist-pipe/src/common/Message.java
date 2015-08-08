package common;

/**
 * Created by mateusz on 08.08.2015.
 */
public interface Message<T> {
    T get();
    void set(T message);
}
