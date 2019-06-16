package core.calculator;

/**
 * Created by 806284 on 06.05.2016.
 */
public interface Parser {
    Expression parse(String expression) throws Exception;
}
