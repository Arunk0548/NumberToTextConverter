import java.util.Scanner;

/**
 *
 * @author Arun Kumar
 */
public class NumberToTextConverter {

    private String[] UNITS_SCALE;
    private final String[] NUMBER_NAMES = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
        "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
    private final String[] DECADES_NAME = {"", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};

    private boolean IsIndianScale = true;
    private long RANGE_MIN = 0;
    private long RANGE_MAX = 1000000;

    public NumberToTextConverter() {
        
        if (IsIndianScale) {
            UNITS_SCALE = new String[]{"", "thousand", "lakh", "hundred crore", "lakh crore", "crore crore", "crore crore crore"};
        } else {
            UNITS_SCALE = new String[]{"", "thousand", "million", "billion", "trillion", "quadrillion", "quintillion", "sextillion"};
        }
        
        Scanner console = new Scanner(System.in);
        System.out.println("Enter a number [0,1000000] to convert in text/word:");
        int numberToConvert = console.nextInt();
        if (numberToConvert < RANGE_MIN || numberToConvert > RANGE_MAX) {
            System.out.println(numberToConvert + " \tNot Supported");
            return;
        }        
        System.out.println(convertToText(numberToConvert));
    }

    /**
     * Convert any value from 0 to 999 inclusive, to a string.
     *
     * @param value The value to convert.
     * @param and true if you want to use the word 'and' in the text
     * @return a String representation of the value.
     */
    private String tripleDigitAsText(int value, boolean and) {
        int subhun = value % 100;
        int hun = value / 100;
        StringBuilder sb = new StringBuilder(50);
        if (hun > 0) {
            sb.append(NUMBER_NAMES[hun]).append(" hundred ");
            if (subhun > 0 && and) {
                sb.append("and ");
            }
        }
        if (subhun < NUMBER_NAMES.length) {
            sb.append(NUMBER_NAMES[subhun]);
        } else {
            int tens = subhun / 10;
            int units = subhun % 10;
            sb.append(DECADES_NAME[tens]);
            if (units > 0) {
                sb.append("-");
            }
            sb.append(NUMBER_NAMES[units]);
        }

        return sb.toString();
    }

    /**
     * Convert any long input value to a text representation
     *
     * @param value The value to convert
     * @return
     */
    public final String convertToText(long value) {
        if (value == 0) {
            return "zero";
        }
        // break the value down in to sets of three digits (thousands).
        int[] thous = new int[UNITS_SCALE.length];
        boolean neg = value < 0;
        // do not make negative numbers positive
        int scale = 0;
        while (value != 0) {
            // use abs to convert thousand-groups to positive, if needed.
            thous[scale] = Math.abs((int) (value % 1000));
            value /= 1000;
            scale++;
        }

        StringBuilder sb = new StringBuilder(scale * 40);
        if (neg) {
            sb.append("minus").append(" ");
        }
        boolean first = true;
        while (--scale > 0) {
            if (!first) {
                sb.append(", ");
            }
            first = false;
            if (thous[scale] > 0) {
                sb.append(tripleDigitAsText(thous[scale], true)).append(" ").append(UNITS_SCALE[scale]);
            }

        }

        if (!first && thous[0] != 0) {
            sb.append(",");
        }
        sb.append(" ").append(tripleDigitAsText(thous[0], true));

        return sb.toString();
    }

    public static void main(String[] args) {

        new NumberToTextConverter();

    }
}
