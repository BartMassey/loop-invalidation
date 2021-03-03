# loop-invalidation: Iterator Invalidation In Several Languages
Bart Massey 2021

This repo contains examples of iterator invalidation in a
simple `for` loop.

---

Here's some C++ code.

    #include <iostream>
    #include <vector>
    
    using namespace std;
    
    int main() {
        auto v = vector<int>();
        v.resize(5);
        for (int i: v) {
            v.pop_back();
            cout << i << endl;
        }
    }

On my box, the output of this happens to be

    0
    0
    0
    0
    0

Note that the last few 0s are completely luck — they rely on the `vector` not freeing the memory after the `pop_back()`s and on no bounds checks for the iterator. So that's special. This is what C++ programmers mean when they say "my programs don't have memory bugs": even `valgrind` won't catch this on my box. This bug, if it manifests as a failure, will be very difficult to find and fix.

---

Now let's try Java.

    import java.util.Vector;
    
    class loopi {
        public static void main(String args[]) {
            Vector<Integer> v = new Vector<Integer>();
            v.setSize(5);
            for (int i: v) {
                v.remove(v.size() - 1);
                System.out.println(i);
            }
        }
    }

This code compiles cleanly. At runtime I get an error.

    Exception in thread "main" java.lang.NullPointerException: Cannot invoke "java.lang.Integer.intValue()" because the return value of "java.util.Iterator.next()" is null
    	at loopi.main(loopi.java:7)

So the error is caught at runtime, at least. This bug will rely on adequate testing to find, and will be relatively easy to fix when found. But some will slip through, and the user will get ugly failures.

---

Time for Rust. Let's try this:


Hmm. Compile-time error.

    error[E0502]: cannot borrow `v` as mutable because it is also borrowed as immutable
     --> loopi.rs:5:17
      |
    4 |     for i in &v {
      |              --
      |              |
      |              immutable borrow occurs here
      |              immutable borrow later used here
    5 |         let _ = v.pop();
      |                 ^^^^^^^ mutable borrow occurs here
    
    error: aborting due to previous error
    
    For more information about this error, try `rustc --explain E0502`.

This is what we wanted all along. Catch this bug at compile time instead of runtime. The bug is automatically found, and is easy to fix.




