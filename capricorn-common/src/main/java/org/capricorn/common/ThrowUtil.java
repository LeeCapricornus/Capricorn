package org.capricorn.common;

import org.capricorn.common.util.internal.unsafe.UnsafeReferenceFieldUpdater;
import org.capricorn.common.util.internal.unsafe.UnsafeUpdater;

public final class ThrowUtil {

    private static final UnsafeReferenceFieldUpdater<Throwable, Throwable> causeUpdater =
            UnsafeUpdater.newReferenceFieldUpdater(Throwable.class, "cause");

}
