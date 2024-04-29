package com.gsench;

/* Kotlin:
object Singleton {
    var title = "global object"
}
*/

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 9, 0},
   k = 1,
   d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\t"},
   d2 = {"Lcom/gsench/Singleton;", "", "()V", "title", "", "getTitle", "()Ljava/lang/String;", "setTitle", "(Ljava/lang/String;)V", "Sandbox"}
)
public final class Singleton {
   @NotNull
   private static String title;
   @NotNull
   public static final Singleton INSTANCE;

   @NotNull
   public final String getTitle() {
      return title;
   }

   public final void setTitle(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      title = var1;
   }

   private Singleton() {
   }

   static {
      Singleton var0 = new Singleton();
      INSTANCE = var0;
      title = "global object";
   }
}
