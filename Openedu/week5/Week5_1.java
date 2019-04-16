package week5;

import mooc.EdxIO;

public class Week5_1 {
    private static EdxIO edxIO;

    public static void main(String[] args) {
        edxIO = EdxIO.create();

        int n = edxIO.nextInt();
        int[] a = new int[n];
        a[0] = edxIO.nextInt();
        for (int i = 1; i < n; i++) {
            a[i] = edxIO.nextInt();
            if (i % 2 == 0 && a[i] < a[i / 2 - 1] || i % 2 == 1 && a[i] < a[i / 2]) {
                edxIO.println("NO");
                edxIO.close();
                return;
            }
        }

        edxIO.println("YES");
        edxIO.close();
    }
}
