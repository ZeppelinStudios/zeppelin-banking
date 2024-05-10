package xyz.zeppelin.bankaccounts.loader;

/**
 * Zeppelin Loader is a utility class that allows you to efficiently
 * and cleanly load objects from configuration files, classes or similar.
 *
 * @implNote Zeppelin Loader has not integration with files, and you have to handle
 * the interaction yourself using the enable method.
 **
 * @param <R> The reference field you want to use to get the object.
 * @param <T> The object you want to be loaded.
 *
 * @author Magnus K. (@codebyxemu)
 */
public interface ZeppelinLoader<R, T> {
    T load(R ref);

    T all();

    void save(T obj);
}

interface EnableDisable {
    void enable();
    void disable();
}
