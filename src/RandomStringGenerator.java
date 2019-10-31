

import java.util.Random;
/**
 * Generates a string of random characters and numbers.
 *
 * @author Brandon Blank
 * @version 0.01
 */
public class RandomStringGenerator
{
    //Private variables
    private StringBuilder builder;
    private Random gen;
    
    //Constructor
    public RandomStringGenerator()
    {
        builder = new StringBuilder("");
        gen = new Random(System.nanoTime());
    }

    //Generate a number between 65 and 90, and 97 and 122, and does it 16 times.
    public String createRandomString()
    {
        int j;

        for (int i = 0; i < 16; i++)
        {
            boolean decider = gen.nextBoolean();

            if (decider)
            {
                j = gen.nextInt((90 - 65) + 1) + 65;
            }
            else
            {
                j = gen.nextInt((122 - 97) + 1) + 97;
            }

            builder.append(Character.toString ((char) j));
        }

        return builder.toString();
    }

    public void close()
    {
        builder = null;
        gen = null;
    }
}
