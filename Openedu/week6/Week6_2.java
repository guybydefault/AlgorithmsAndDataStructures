package week6;

import mooc.EdxIO;

public class Week6_2 {
    private static EdxIO edxIO;

    private static final double EPS = 0.000000000001;

    public static void main(String[] args) {
        edxIO = EdxIO.create();

        int n = edxIO.nextInt();
        double[] h = new double[n];
        h[0] = edxIO.nextDoublePrecise();

        double l = 0;
        double r = h[0];
        boolean above;

        while (r - l > EPS) {
            h[1] = (r + l) / 2;
            above = true;

            for (int i = 2; i < n; i++) {
                h[i] = 2 * h[i - 1] - h[i - 2] + 2;

                if (h[i] < 0) {
                    above = false;
                    break;
                }
            }

            if (above) {
                r = h[1];
            } else {
                l = h[1];
            }
        }

        edxIO.print(h[n - 1]);

        edxIO.close();
    }


}
