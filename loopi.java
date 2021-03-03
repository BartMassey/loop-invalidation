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
