package com.gsench;

// Kotlin:
// data class Customer(val id: Int, var name: String, var email: String)

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 9, 0},
   k = 1,
   d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÆ\u0003J'\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0005HÖ\u0001R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\t\"\u0004\b\u000f\u0010\u000b¨\u0006\u0019"},
   d2 = {"Lcom/gsench/Customer;", "", "id", "", "name", "", "email", "(ILjava/lang/String;Ljava/lang/String;)V", "getEmail", "()Ljava/lang/String;", "setEmail", "(Ljava/lang/String;)V", "getId", "()I", "getName", "setName", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "Sandbox"}
)
public final class Customer {
   private final int id;
   @NotNull
   private String name;
   @NotNull
   private String email;

   public final int getId() {
      return this.id;
   }

   @NotNull
   public final String getName() {
      return this.name;
   }

   public final void setName(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.name = var1;
   }

   @NotNull
   public final String getEmail() {
      return this.email;
   }

   public final void setEmail(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "<set-?>");
      this.email = var1;
   }

   public Customer(int id, @NotNull String name, @NotNull String email) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(email, "email");
      super();
      this.id = id;
      this.name = name;
      this.email = email;
   }

   public final int component1() {
      return this.id;
   }

   @NotNull
   public final String component2() {
      return this.name;
   }

   @NotNull
   public final String component3() {
      return this.email;
   }

   @NotNull
   public final Customer copy(int id, @NotNull String name, @NotNull String email) {
      Intrinsics.checkNotNullParameter(name, "name");
      Intrinsics.checkNotNullParameter(email, "email");
      return new Customer(id, name, email);
   }

   // $FF: synthetic method
   public static Customer copy$default(Customer var0, int var1, String var2, String var3, int var4, Object var5) {
      if ((var4 & 1) != 0) {
         var1 = var0.id;
      }

      if ((var4 & 2) != 0) {
         var2 = var0.name;
      }

      if ((var4 & 4) != 0) {
         var3 = var0.email;
      }

      return var0.copy(var1, var2, var3);
   }

   @NotNull
   public String toString() {
      return "Customer(id=" + this.id + ", name=" + this.name + ", email=" + this.email + ")";
   }

   public int hashCode() {
      int var10000 = Integer.hashCode(this.id) * 31;
      String var10001 = this.name;
      var10000 = (var10000 + (var10001 != null ? var10001.hashCode() : 0)) * 31;
      var10001 = this.email;
      return var10000 + (var10001 != null ? var10001.hashCode() : 0);
   }

   public boolean equals(@Nullable Object var1) {
      if (this != var1) {
         if (var1 instanceof Customer) {
            Customer var2 = (Customer)var1;
            if (this.id == var2.id && Intrinsics.areEqual(this.name, var2.name) && Intrinsics.areEqual(this.email, var2.email)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }
}
