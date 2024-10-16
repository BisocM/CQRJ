package bisocm.cqrtest.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandAttribute {
    Class<? extends Enum<? extends MenuState>> menu();
    int ordinal();
    int priority();
}
/*
* Users can define custom enums that extend the MenuState interface and pass them into here. Example:
*
* public enum CustomMenuState implements MenuState{
* UNAUTHORIZED,
* AUTHORIZED,
* SETTINGS
* }
*
*/