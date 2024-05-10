package xyz.zeppelin.bankaccounts.economy;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IntegrationTestCallback {

    ERROR_MISSING_DEPENDENCIES(ShortCallback.DISABLED, "You are missing some of the required dependencies of this integration."),
    ERROR_UNKNOWN(ShortCallback.DISABLED, "We attempted to run the Economy integration but could not due to an unknown error."),
    SUCCESS(ShortCallback.READY_TO_RUN, "Successfully enabled Economy integration.");

    private ShortCallback callback;
    private String text;

}

enum ShortCallback {
    READY_TO_RUN,
    DISABLED;
}
