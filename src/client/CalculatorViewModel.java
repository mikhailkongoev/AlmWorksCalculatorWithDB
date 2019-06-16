package client;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ManagedBean
@ViewScoped
public class CalculatorViewModel implements Serializable {
    private Double result;
    private String expression = "";
    private WebTarget webTarget;
    private Set<Character> signs;
    private Set<Character> digits;
    private boolean resultOnly;

    public boolean getResultOnly() {
        return resultOnly;
    }

    public void setResultOnly(boolean resultOnly) {
        this.resultOnly = resultOnly;
    }

    public String getExpression() {
        return expression;

    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    @PostConstruct
    public void init() {
        Client client = ClientBuilder.newClient();
        webTarget
                = client.target("http://localhost:8080/calcWeb/core").path("evaluator");
        signs = new HashSet<>(Arrays.asList('+', '-', '*', '/'));
        digits = new HashSet<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));
        updateResult();
    }

    private void updateResult() {
        String tempExpr;
        if (expression.length() != 0 && !isDigit(expression.charAt(expression.length() - 1))) {
            tempExpr = expression.substring(0, expression.length() - 1);
        } else {
            tempExpr = expression;
        }
        result = webTarget.queryParam("expression", tempExpr).request().get().readEntity(Double.class);
    }

    private boolean isDigit(char character){
        return digits.contains(character);
    }

    private boolean isSign(char character) {
        return signs.contains(character);
    }

    public void updateExpression(char character) {
        resultOnly = false;
        if (character == 'C') {
            expression = "";
        } else if (character == 'c') {
            if (expression != null && expression.length() != 0) {
                expression = expression.substring(0, expression.length() - 1);
            }
        } else if (character == '=') {
            resultOnly = true;
            updateResult();
            expression = "";
            return;
        } else if (isDigit(character)) {
            expression += character;
        } else if (isSign(character)) {
            if (expression.length() == 0) {
                if (character == '-') {
                    expression += character;
                }
            } else {
                if (isSign(expression.charAt(expression.length() - 1)) || expression.charAt(expression.length() - 1) == ',') {
                    expression = expression.substring(0, expression.length() - 1);
                }
                expression += character;
            }
        } else if (character == '.') {
            if (expression.length() != 0 || isDigit(expression.charAt(expression.length() - 1))) {
                expression += character;
            }
        }
        updateResult();
    }
}
