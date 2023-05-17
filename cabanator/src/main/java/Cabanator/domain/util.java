package Cabanator.domain;

import java.awt.Toolkit;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class util implements Serializable{

    public util() {
    }

    public int toPixel(double pouces) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        double dpi = toolkit.getScreenResolution();
        if (pouces == 0) {
            return 0;
        } else {
            return (int) Math.round((pouces * dpi) / 48);
        }
    }

    public double toInch(double pixel) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        double dpi = toolkit.getScreenResolution();
        if (pixel == 0) {
            return 0;
        } else {
            return (double) Math.round((pixel * 48) / dpi);
        }
    }

    public static double convertPouceFormat(String measurement) {
        Pattern pattern = Pattern.compile("(\\d+)(?:\\s(\\d+)/(\\d+))?|\\d+");
        Matcher matcher = pattern.matcher(measurement);
        if (matcher.matches()) {
            if (matcher.group(2) != null && matcher.group(3) != null) {
                int wholeNumber = Integer.parseInt(matcher.group(1));
                int numerator = Integer.parseInt(matcher.group(2));
                int denominator = Integer.parseInt(matcher.group(3));
                return wholeNumber + (double) numerator / denominator;
            } else {
                return Double.parseDouble(measurement);
            }
        } else {
             return Double.parseDouble(measurement);

        }
    }

}
