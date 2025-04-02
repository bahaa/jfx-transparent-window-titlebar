package io.github.bahaa.fx.platform.macos.foundation;


import io.github.bahaa.fx.platform.macos.NSObject;
import io.github.bahaa.fx.platform.macos.Runtime;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

public class NSArray extends NSObject {

    protected NSArray(final MemorySegment id) {
        super(id);
    }

    public static NSArray from(final MemorySegment id) {
        return new NSArray(id);
    }

    public int count() {
        return (int) Runtime.msgSend(id(), Meta.COUNT).address();
    }

    public MemorySegment objectAtIndex(final int index) {
        return Runtime.msgSend(id(), Meta.OBJECT_AT_INDEX, index);
    }

    protected enum Meta {
        ;

        private static final MemorySegment COUNT, OBJECT_AT_INDEX;

        static {
            try (final var arena = Arena.ofConfined()) {
                COUNT = Runtime.registerName(arena, "count");
                OBJECT_AT_INDEX = Runtime.registerName(arena, "objectAtIndex:");
            }
        }
    }
}
