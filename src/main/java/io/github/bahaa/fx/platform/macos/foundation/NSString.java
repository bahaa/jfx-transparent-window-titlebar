package io.github.bahaa.fx.platform.macos.foundation;


import io.github.bahaa.fx.platform.macos.NSObject;
import io.github.bahaa.fx.platform.macos.Runtime;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

import static io.github.bahaa.fx.platform.macos.Runtime.msgSend;


public class NSString extends NSObject {

    protected NSString(final MemorySegment id) {
        super(id);
    }

    public static NSString from(final MemorySegment id) {
        return new NSString(id);
    }

    public static NSString from(final String str) {
        try (final var arena = Arena.ofConfined()) {
            final var cstr = arena.allocateFrom(str);
            final var id = NSObject.alloc(Meta.CLASS);
            return new NSString(msgSend(id, Meta.INIT_WITH_UTF8_STRING, cstr));
        }
    }

    public String toUtf8String() {
        final var cstr = msgSend(this.id, Meta.UTF8_STRING);
        return cstr.reinterpret(Integer.MAX_VALUE).toString();
    }

    protected enum Meta {
        ;
        public static final MemorySegment CLASS;

        public static final MemorySegment INIT_WITH_UTF8_STRING, UTF8_STRING;

        static {
            try (final var arena = Arena.ofConfined()) {
                CLASS = Runtime.getClass(arena, "NSString");

                INIT_WITH_UTF8_STRING = Runtime.registerName(arena, "initWithUTF8String:");
                UTF8_STRING = Runtime.registerName(arena, "UTF8String");
            }
        }
    }
}
