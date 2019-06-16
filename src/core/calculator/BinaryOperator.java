package core.calculator;

/**
 * Created by 806284 on 06.05.2016.
 */
public abstract class BinaryOperator implements Expression {
    protected Expression firstArg;
    protected Expression secondArg;

    BinaryOperator (Expression first, Expression second) {
        firstArg = first;
        secondArg = second;
    }

    abstract protected double apply(double firstArg, double secondArg);

    public double evaluate() {
        return apply(firstArg.evaluate(), secondArg.evaluate());
    }
}
