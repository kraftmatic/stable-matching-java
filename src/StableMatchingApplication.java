import java.util.Arrays;

public class StableMatchingApplication {

    static int numberOfOneSex = 25000;

    public static void main(String... args){

        // first four rows represent the men's preference of women
        // and the second half represent the women's preference of men
        int preferenceMatrix[][] = generatePrefernceMatrix(numberOfOneSex);

        //benchmark start
        long startTime = System.currentTimeMillis();

        stableMarriage(preferenceMatrix, false);

        //benchmark finish
        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime-startTime) + "ms");

    }

    static int[][] generatePrefernceMatrix(int totalNumberOfOneSex){
        int[][] generatedMatrix = new int[totalNumberOfOneSex * 2][totalNumberOfOneSex];
        for(int man = 0 ; man < totalNumberOfOneSex ; man++){
            for(int preference = totalNumberOfOneSex ; preference < totalNumberOfOneSex * 2 ; preference++){
                generatedMatrix[man][preference - totalNumberOfOneSex] = preference;
            }
        }
        for(int woman = totalNumberOfOneSex ; woman < totalNumberOfOneSex * 2 ; woman++){
            for(int preference = 0 ; preference < totalNumberOfOneSex ; preference++){
                generatedMatrix[woman][preference] = preference;
            }
        }
        shuffle(generatedMatrix, totalNumberOfOneSex);
        return generatedMatrix;
    }

    // Method to shuffle preferences
    static void shuffle(int [][] arr, int totalNumberOfOneSex) {
        for (int row = 0 ; row < totalNumberOfOneSex * 2; row++ ){
            for (int i = 0; i < totalNumberOfOneSex; i++) {
                int index = (int) (Math.random() * totalNumberOfOneSex);
                int temp = arr[row][i];
                arr[row][i] = arr[row][index];
                arr[row][index] = temp;
            }
        }
    }

    /**
     *  Method to determine a woman's prefence for marriage
     * @param preferenceMatrix
     * @param woman woman
     * @param man first man
     * @param otherMan second man
     * @return whether second man is preferred over first man
     */
    static boolean womanPreference(int preferenceMatrix[][], int woman,
                                   int man, int otherMan)
    {
        // Go through the woman's preferences from highest to lowest
        for (int i = 0; i < numberOfOneSex; i++) {
            // If otherMan's number comes first that means she does prefer him to the original man
            if (preferenceMatrix[woman][i] == otherMan)
                return true;
            // Otherwise she doesn't prefer the other man and this returns false
            if (preferenceMatrix[woman][i] == man)
                return false;
        }
        return false;
    }

    /**
     * Execute Stable Marriage algorithm using the supplied preference matrix
     *
     * @param preferenceMatrix input matrix of men/women and their preferences
     * @param displayResults should results be displayed
     */
    static void stableMarriage(int preferenceMatrix[][], boolean displayResults)
    {
        // This will store the partner of the women and ultimately be our final solution matrix
        int womansPartners[] = new int[numberOfOneSex];

        // This array stores all of the men and tracks whether or not they are spoken for
        boolean freeMen[] = new boolean[numberOfOneSex];

        // Initialize all men and women as free
        Arrays.fill(womansPartners, -1);
        int freeCount = numberOfOneSex;

        // We'll keep going while there are still men who aren't matched
        while (freeCount > 0)
        {
            // We'll use man as the counter since it represent the man in the index of the freeMen array
            int man;
            for (man = 0; man < numberOfOneSex; man++)
                if (freeMen[man] == false)
                    break;


            for (int i = 0; i < numberOfOneSex && freeMen[man] == false; i++) {
                int woman = preferenceMatrix[man][i];

                // This tests if the woman is currently single
                if (womansPartners[woman - numberOfOneSex] == -1)
                {
                    //If they are single then assign this man to them and mark him as 'spoken for'
                    womansPartners[woman - numberOfOneSex] = man;
                    freeMen[man] = true;
                    freeCount--;
                }

                else
                {
                    // find the current male partner of the woman
                    int currentMan = womansPartners[woman - numberOfOneSex];

                    // Check if she prefers the new proposal to her current partner
                    if (womanPreference(preferenceMatrix, woman, man, currentMan) == false)
                    {
                        // if she prefers the new man, swap him in as her new partner, mark him as taken and mark
                        // the discarded man as available.
                        womansPartners[woman - numberOfOneSex] = man;
                        freeMen[man] = true;
                        freeMen[currentMan] = false;
                    }
                }
            }

        }

        if (displayResults) {
            // Print the solution
            System.out.println("Woman Man");
            for (int i = 0; i < numberOfOneSex; i++) {
                System.out.print(" ");
                System.out.println(i + numberOfOneSex + "     " +
                        womansPartners[i]);
            }
        }
    }
}
