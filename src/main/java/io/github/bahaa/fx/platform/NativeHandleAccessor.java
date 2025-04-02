package io.github.bahaa.fx.platform;

import javafx.stage.Window;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

public enum NativeHandleAccessor {
    ;

    /**
     * Needs these JVM options:
     * --add-opens javafx.graphics/javafx.stage=io.github.bahaa.fx.jfxtransparentwindowtitlebar
     * --add-opens javafx.graphics/com.sun.javafx.tk.quantum=io.github.bahaa.fx.jfxtransparentwindowtitlebar
     *
     * @param window JavaFX window/stage
     * @return native handle
     */
    public static long getRawHandle(final Window window) {
        Objects.requireNonNull(window);

        try {
            final var getPeerMethod = Window.class.getDeclaredMethod("getPeer");
            getPeerMethod.setAccessible(true);
            final var getPeerHandle = MethodHandles.lookup().unreflect(getPeerMethod);
            final var tkStage = (Object) getPeerHandle.invoke(window);
            return (long) tkStage.getClass().getMethod("getRawHandle").invoke(tkStage);
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
