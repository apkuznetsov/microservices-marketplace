package kuznetsov.marketplace.utils;

import org.springframework.util.Assert;

public class UserContextHolder {

    private static final ThreadLocal<UserContext> userContextThreadLocal = new ThreadLocal<>();

    public static UserContext getContext() {
        UserContext context = userContextThreadLocal.get();

        if (context == null) {
            context = buildEmptyContext();
            userContextThreadLocal.set(context);

        }

        return userContextThreadLocal.get();
    }

    public static void setContext(UserContext context) {
        Assert.notNull(context, "Only non-null UserContext instances are permitted");
        userContextThreadLocal.set(context);
    }

    private static UserContext buildEmptyContext() {
        return new UserContext();
    }

}
