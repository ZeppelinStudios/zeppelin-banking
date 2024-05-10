package xyz.zeppelin.bankaccounts.storage;

import java.util.Optional;

/**
 * Load specific storage methods
 * @param <T> The retrival object
 */
public interface StorageType<T> {
    void enable();
    void disable();
    Optional<T> get();
}
