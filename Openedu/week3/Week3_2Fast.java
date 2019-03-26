package week3;

import mooc.EdxIO;

import java.io.IOException;

public class Week3_2Fast {

    private static EdxIO edxIO;

    private static void countingSort(byte[] array, int l, int r, int[] mapping, boolean[] ranging) {
        int[] count = new int['z' - 'a' + 1];

        int length = r - l;
        for (int j = l; j < r; j++) {
            count[array[j] - 'a']++;
        }

        if (count[0] > 0) {
            ranging[l + count[0] - 1] = true;
        }
        for (int k = 1; k < count.length; k++) {
            count[k] = count[k] + count[k - 1];
            if (count[k] > count[k - 1]) {
                ranging[l + count[k] - 1] = true;
            }
        }

        int[] newMapping = new int[length];
        for (int j = length - 1; j >= 0; j--) {
            newMapping[count[array[l + j] - 'a'] - 1] = mapping[l + j];
            count[array[l + j] - 'a']--;
        }

        for (int i = 0; i < length; i++) {
            mapping[l + i] = newMapping[i];
        }
    }

    private static void propagate(byte[] array, int[] mapping) {
        byte[] tmp = array.clone();
        for (int i = 0; i < mapping.length; i++) {
            array[i] = tmp[mapping[i]];
        }
    }

    public static void main(String[] args) throws IOException {
        edxIO = EdxIO.create();

        int stringCount = edxIO.nextInt();
        int stringLength = edxIO.nextInt();
        int totalPhases = edxIO.nextInt();

        // skipping not required char sequences
        for (int i = 0; i < stringLength - totalPhases; i++) {
            edxIO.nextBytes();
        }

        // BEGIN initialization
        byte src[];
        boolean ranging[] = new boolean[stringCount];
        ranging[stringCount - 1] = true;

        int[] mapping = new int[stringCount];
        for (int i = 0; i < mapping.length; i++) {
            mapping[i] = i;
        }

        int minL = 0;
        // END initialization

        for (int i = 0; i < totalPhases; i++) {
            src = edxIO.nextBytes();
            propagate(src, mapping);

            int j = minL;
            while (j < stringCount && ranging[j]) {
                minL++;
                j++;
            }
            if (minL > stringCount - 1) {
                break;
            }

            int l = minL;
            while (j < stringCount) {
                if (ranging[j]) {
                    countingSort(src, l, j + 1, mapping, ranging);
                    l = j + 1;
                    j++;
                    // skipping sorted units
                    while (j < stringCount && ranging[j]) {
                        l++;
                        j++;
                    }
                } else {
                    j++;
                }
            }
        }

        // BEGING printing result
        for (int i = 0; i < mapping.length; i++) {
            edxIO.print(mapping[i] + 1 + " ");
        }
        // END printing result

        edxIO.close();
    }
}
