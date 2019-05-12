package andrade.amilcar.instagramsidebar.gson;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

// From - https://gist.github.com/JakeWharton/0d67d01badcee0ae7bc9
@Target(TYPE)
@Retention(RUNTIME)
public @interface AutoGson {
}