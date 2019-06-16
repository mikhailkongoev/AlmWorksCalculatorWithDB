package core.calculator;

/**
 * Created by 806284 on 06.05.2016.
 */
public class Add extends BinaryOperator implements Expression {
    public Add(Expression firstArg, Expression secondArg) {
        super(firstArg, secondArg);
    }

    @Override
    protected double apply(double firstArg, double secondArg) {
        return firstArg + secondArg;
    }
}
