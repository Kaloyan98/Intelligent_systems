import java.util.ArrayList;
import java.util.List;

public class Model {
    private static final int ATTRIBUTE_NUMBER = 16;
    private final List<String> learningData;
    private final List<String> testData;
    private final int[] attributeYesCounterForRepublicans;
    private final int[] attributeYesCounterForDemocrats;
    private final int[] attributeNoCountersForRepublicans;
    private final int[] attributeNoCountersForDemocrats;
    private int republicanCounter;
    private int democratCounter;

    public Model(List<String> learningData, List<String> testData) {
        this.learningData = new ArrayList<>(learningData);
        this.testData = new ArrayList<>(testData);

        attributeYesCounterForRepublicans = new int[ATTRIBUTE_NUMBER];
        attributeYesCounterForDemocrats = new int[ATTRIBUTE_NUMBER];
        attributeNoCountersForRepublicans = new int[ATTRIBUTE_NUMBER];
        attributeNoCountersForDemocrats = new int[ATTRIBUTE_NUMBER];
        republicanCounter = 0;
        democratCounter = 0;
    }

    public void trainModel() {
        for (String entry : learningData) {
            String[] splitByAttribute = entry.split(",");
            boolean isRepublican = splitByAttribute[0].equals("republican");

            if (isRepublican) {
                republicanCounter++;
            } else {
                democratCounter++;
            }

            for (int i = 1; i < splitByAttribute.length; i++) {
                if (splitByAttribute[i].equals("y") && isRepublican) {
                    attributeYesCounterForRepublicans[i - 1]++;
                } else if (splitByAttribute[i].equals("y") && !isRepublican) {
                    attributeYesCounterForDemocrats[i - 1]++;
                } else if (splitByAttribute[i].equals("n") && isRepublican) {
                    attributeNoCountersForRepublicans[i - 1]++;
                } else if (splitByAttribute[i].equals("n") && !isRepublican) {
                    attributeNoCountersForDemocrats[i - 1]++;
                }
            }
        }
    }

    public double evaluateTestData() {
        int correctCounter = 0;

        for (String entry : testData) {
            double probabilityRepublican = calculateProbabilityOfAttributeForRepublican(entry);
            double probabilityDemocrat = calculateProbabilityOfAttributeForDemocrat(entry);

            if (probabilityRepublican > probabilityDemocrat && entry.startsWith("republican")) {
                correctCounter++;
            } else if (probabilityRepublican < probabilityDemocrat && entry.startsWith("democrat")) {
                correctCounter++;
            }
        }

        return ((double) correctCounter / testData.size()) * 100;
    }

    private double calculateProbabilityOfAttributeForRepublican(String entry) {
        double logOfProbability = Math.log(laplaceSmoothing(republicanCounter, learningData.size()));
        String[] attributes = entry.split(",");

        for (int i = 1; i < attributes.length; i++) {
            String attributeValue = attributes[i];
            logOfProbability += Math.log(getProbabilityOfAttributeRepublican(i - 1, attributeValue));
        }

        return logOfProbability;
    }

    private double getProbabilityOfAttributeRepublican(int attributeIndex, String attributeValue) {
        if (attributeValue.equals("y")) {
            return laplaceSmoothing(attributeYesCounterForRepublicans[attributeIndex], republicanCounter);
        } else {
            return laplaceSmoothing(attributeNoCountersForRepublicans[attributeIndex], republicanCounter);
        }
    }

    private double calculateProbabilityOfAttributeForDemocrat(String entry) {
        double logOfProbability = Math.log(laplaceSmoothing(democratCounter, learningData.size()));
        String[] attributes = entry.split(",");

        for (int i = 1; i < attributes.length; i++) {
            String attributeValue = attributes[i];
            logOfProbability += Math.log(getProbabilityOfAttributeDemocrat(i - 1, attributeValue));
        }

        return logOfProbability;
    }

    private double getProbabilityOfAttributeDemocrat(int attributeIndex, String attributeValue) {
        if (attributeValue.equals("y")) {
            return laplaceSmoothing(attributeYesCounterForDemocrats[attributeIndex], democratCounter);
        } else {
            return laplaceSmoothing(attributeNoCountersForDemocrats[attributeIndex], democratCounter);
        }
    }

    private double laplaceSmoothing(int a, int b) {
        final int alpha = 1;

        return (double) (a + alpha) / (b + 3 * alpha); //3? Performance difference is unnoticeable
    }
}
