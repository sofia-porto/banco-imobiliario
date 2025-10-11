package model;

import java.util.Random;

class Dado {
    private Random random;

    public Dado() {
        random = new Random();
    }

    int lancar() {
        return random.nextInt(6) + 1;
    }

    int[] lancarDoisDados() {
        return new int[]{lancar(), lancar()};
    }

}
