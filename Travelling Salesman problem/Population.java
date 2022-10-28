import java.util.*;

public class Population {
    private final List<Individual> individuals;

    public Population(int size) {
        individuals = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            individuals.add(new Individual(size));
        }
        Collections.sort(individuals);
    }

    public Population(List<Individual> individualList) {
        individuals = new ArrayList<>();
        individuals.addAll(individualList);
        Collections.sort(individuals);
    }

    public List<Individual> getIndividuals() {
        return individuals;
    }

    public List<Individual> selectBestParents() {
        List<Individual> bestHalf = new ArrayList<>();
        int halfSize = individuals.size() / 2;
        if (halfSize % 2 == 1) {
            halfSize++;
        }

        for (int i = 0; i < halfSize; i++) {
            bestHalf.add(individuals.get(i));
        }

        return bestHalf;
    }
}
