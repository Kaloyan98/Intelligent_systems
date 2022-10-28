public class Main {
    public static void main(String[] args) {
        ProblemSolution solution = new ProblemSolution();
        solution.calculateDataPerAttribute();
        solution.fillInMissingDataValues();
        solution.executeTenfoldCrossValidation();
    }
}
