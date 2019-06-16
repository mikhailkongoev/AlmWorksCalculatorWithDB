package core.evals;

import core.calculator.ExpressionsParser;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;


@Path("/evaluator")
public class Evaluator {
    private static ExpressionsParser expressionsParser = new ExpressionsParser();
    // The Java method will process HTTP GET requests
    @GET
    public Double getClichedMessage(@QueryParam(value = "expression") String expression) {
        try {
            if (expression == null || expression.length() == 0) {
                return 0d;
            }
            return expressionsParser.parse(expression).evaluate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0d;
        }
    }
}