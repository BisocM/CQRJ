package bisocm.cqrtest.sample;

import bisocm.cqrtest.annotations.MenuState;

public enum CustomMenuState implements MenuState {
    UNAUTHORIZED,
    AUTHORIZED,
    SETTINGS
}