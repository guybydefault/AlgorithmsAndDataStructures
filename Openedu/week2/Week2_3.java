package week2;

import mooc.EdxIO;



public class Week2_3 {

    private static EdxIO edxIO;

    private static void swap(int[] array, int i, int k) {
        int tmp = array[i];
        array[i] = array[k];
        array[k] = tmp;
    }


    public static void main(String[] args) {
        edxIO = EdxIO.create();

        int n = edxIO.nextInt();

        int[] a = new int[n];

        for (int i = 0; i < a.length; i++) {
            a[i] = i + 1;

            int m = (i) / 2;

            int tmp = a[i];
            a[i] = a[m];
            a[m] = tmp;
        }

        int ind2 = 0;
        while (ind2 < a.length - 1 && a[ind2] != 2) {
            ind2++;
        }
        int ind1 = a.length - 1;
        while (ind1 > 0 && a[ind1] != 1) {
            ind1--;
        }
        swap(a, ind1, ind2);

        for (int i = 0; i < a.length; i++) {
            edxIO.print(a[i] + " ");
        }


        edxIO.close();
    }
}
