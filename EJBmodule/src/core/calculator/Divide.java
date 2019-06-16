package core.calculator;

/**
 * Created by 806284 on 06.05.2016.
 */
public class Divide extends BinaryOperator implements Expression {
    Divide(Expression first, Expression second) {
        super(first, second);
    }

    @Override
    protected double apply(double firstArg, double secondArg) {
        return firstArg / secondArg;
    }
}
