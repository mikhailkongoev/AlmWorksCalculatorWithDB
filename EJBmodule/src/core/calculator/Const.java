package core.calculator;

/**
 * Created by 806284 on 06.05.2016.
 */
public class Const implements Expression {
    private double value;
    public Const(double value) {
        this.value = value;
    }

    public double evaluate() {
        return value;
    }
}
