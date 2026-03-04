package utils;

import com.github.javafaker.Faker;

public class FakerProvider {
    private static final ThreadLocal<Faker> FAKER_THREAD_LOCAL = ThreadLocal.withInitial(Faker::new);

    private FakerProvider() {
    }

    public static Faker getFaker() {
        return FAKER_THREAD_LOCAL.get();
    }
}
