package assertions;

import assertions.conditions.MessageCondition;
import assertions.conditions.StatusCodeCondition;

public class Conditions {

    public static MessageCondition hasMessage(String message) {
        return new MessageCondition(message);
    }

    public static StatusCodeCondition hasStatus(int status) {
        return new StatusCodeCondition(status);
    }
}
