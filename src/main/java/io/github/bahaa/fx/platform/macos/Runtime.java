package io.github.bahaa.fx.platform.macos;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

public enum Runtime {
    ;


    public static MemorySegment getClass(final Arena arena, final String name) {
        try {
            return (MemorySegment) _ObjCRuntime.objc_getClass.invokeExact(arena.allocateFrom(name));
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static MemorySegment registerName(final Arena arena, final String name) {
        try {
            return (MemorySegment) _ObjCRuntime.sel_registerName.invokeExact(arena.allocateFrom(name));
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static void dispose(final MemorySegment object) {
        try {
            _ObjCRuntime.object_dispose.invokeExact(object);
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static MemorySegment msgSend(final MemorySegment self, final MemorySegment sel) {
        try {
            return (MemorySegment) _ObjCRuntime.objc_msgSend.invokeExact(self, sel);
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static MemorySegment msgSend(final MemorySegment self, final MemorySegment sel, final byte param) {
        try {
            return (MemorySegment) _ObjCRuntime.objc_msgSendByte.invokeExact(self, sel, param);
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static MemorySegment msgSend(final MemorySegment self, final MemorySegment sel, final int param) {
        try {
            return (MemorySegment) _ObjCRuntime.objc_msgSendInt.invokeExact(self, sel, param);
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static MemorySegment msgSend(final MemorySegment self, final MemorySegment sel, final long param) {
        try {
            return (MemorySegment) _ObjCRuntime.objc_msgSendLong.invokeExact(self, sel, param);
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static MemorySegment msgSend(final MemorySegment self, final MemorySegment sel, final MemorySegment param) {
        try {
            return (MemorySegment) _ObjCRuntime.objc_msgSendAddr.invokeExact(self, sel, param);
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static MemorySegment msgSend(final MemorySegment self, final MemorySegment sel, final float param) {
        try {
            return (MemorySegment) _ObjCRuntime.objc_msgSendFloat.invokeExact(self, sel, param);
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static class _ObjCRuntime {
        private static final String LIB_OBJC = "libobjc.A.dylib";

        private static final MethodHandle objc_getClass;
        private static final MethodHandle sel_registerName;

        private static final MethodHandle object_dispose;

        private static final MethodHandle objc_msgSend;
        private static final MethodHandle objc_msgSendByte;
        private static final MethodHandle objc_msgSendInt;
        private static final MethodHandle objc_msgSendLong;
        private static final MethodHandle objc_msgSendFloat;
        private static final MethodHandle objc_msgSendAddr;

        static {
            final var arena = Arena.ofAuto();

            final var linker = Linker.nativeLinker();
            final var objc = SymbolLookup.libraryLookup(LIB_OBJC, arena);

            objc_getClass = linker.downcallHandle(objc.find("objc_getClass").orElseThrow(),
                    FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS));

            sel_registerName = linker.downcallHandle(objc.find("sel_registerName").orElseThrow(),
                    FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS));

            object_dispose = linker.downcallHandle(objc.find("object_dispose").orElseThrow(),
                    FunctionDescriptor.ofVoid(ValueLayout.ADDRESS));

            objc_msgSend = linker.downcallHandle(
                    objc.find("objc_msgSend").orElseThrow(),
                    FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS),
                    Linker.Option.critical(false));

            objc_msgSendByte = linker.downcallHandle(
                    objc.find("objc_msgSend").orElseThrow(),
                    FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_BYTE),
                    Linker.Option.critical(false));

            objc_msgSendInt = linker.downcallHandle(
                    objc.find("objc_msgSend").orElseThrow(),
                    FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT),
                    Linker.Option.critical(false));

            objc_msgSendLong = linker.downcallHandle(
                    objc.find("objc_msgSend").orElseThrow(),
                    FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_LONG),
                    Linker.Option.critical(false));

            objc_msgSendFloat = linker.downcallHandle(
                    objc.find("objc_msgSend").orElseThrow(),
                    FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_FLOAT),
                    Linker.Option.critical(false));

            objc_msgSendAddr = linker.downcallHandle(
                    objc.find("objc_msgSend").orElseThrow(),
                    FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS),
                    Linker.Option.critical(false));
        }
    }
}
