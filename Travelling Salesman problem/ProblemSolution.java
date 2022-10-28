import java.util.*;

public class ProblemSolution {
    private final int N;
    private Population population;
    private Individual bestPath;

    public ProblemSolution(int N) {
        this.N = N;
        DistanceTable.initialiseDistanceTable(N);
        DistanceTable.printDistanceTable();
        population = new Population(N);
        bestPath = population.getIndividuals().get(0);
    }

    public Individual getBestPath() {
        return bestPath;
    }

    public void findCheapestPathThroughNCities() {
        int populationCounter = 0;
        int numberOfIterations = 1000;
        final int PRINT_ON_EVERY = 10;

        while (populationCounter <= numberOfIterations) {
            List<Individual> bestParents = population.selectBestParents();
            List<Individual> children = reproduce(bestParents);
            mutateChildren(children);

            if (populationCounter % PRINT_ON_EVERY == 0) {
                System.out.printf("Population #%d: Current best path cost is %d\n",
                        populationCounter, bestPath.getPathCost());
            }

            population = buildNextGeneration(bestParents, children);
            bestPath = population.getIndividuals().get(0);
            populationCounter++;
        }
    }

    private List<Individual> reproduce(List<Individual> parents) {
        List<Individual> children = new ArrayList<>();

        for (int i = 0; i < parents.size(); i += 2) {
            Individual firstChild = crossover(parents.get(i), parents.get(i + 1));
            Individual secondChild = crossover(parents.get(i + 1), parents.get(i));

            children.add(firstChild);
            children.add(secondChild);
        }

        return children;
    }

    private Individual crossover(Individual firstParent, Individual secondParent) {
        int onePointIndex = firstParent.getPath().size() / 2;

        List<Integer> gene = new ArrayList<>();
        for (int i = 0; i < onePointIndex; i++) {
            gene.add(firstParent.getPath().get(i));
        }

        int index = onePointIndex;
        int secondParentIndex = 0;
        while (index < N && secondParentIndex < N) {
            if (!gene.contains(secondParent.getPath().get(secondParentIndex))) {
                gene.add(secondParent.getPath().get(secondParentIndex));
                index++;
            }

            secondParentIndex++;
        }

        return new Individual(gene);
    }

    private void mutateChildren(List<Individual> children) {
        final int MUTATION_CHANCE = 33;
        Random random = new Random();

        for (Individual child : children) {
            int roll = random.nextInt(100) + 1;

            if (roll <= MUTATION_CHANCE) {
                child.mutateBySwappingTwoCities();
            }
        }
    }

    private Population buildNextGeneration(List<Individual> bestParents, List<Individual> children) {
        List<Individual> newPopulation = new ArrayList<>();
        int childrenUsed = 0;
        int parentsUsed = 0;
        int counter = 0;

        while (childrenUsed < children.size() && parentsUsed < bestParents.size()) {
            if (bestParents.get(parentsUsed).compareTo(children.get(childrenUsed)) > 0) {
                newPopulation.add(bestParents.get(parentsUsed++));
            } else {
                newPopulation.add(children.get(childrenUsed++));
            }

            counter++;
        }

        while (counter < N && childrenUsed < children.size()) {
            newPopulation.add(children.get(childrenUsed++));
            counter++;
        }

        while (counter < N && parentsUsed < bestParents.size()) {
            newPopulation.add(bestParents.get(parentsUsed++));
            counter++;
        }

        return new Population(newPopulation);
    }
}
