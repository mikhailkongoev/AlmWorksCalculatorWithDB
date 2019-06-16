package core.calculator;

/**
 * Created by 806284 on 06.05.2016.
 */
public class ExpressionsParser implements Parser {
    private LEXEM curlex;
    private String v1;
    private int c;
    private String a;

    private enum LEXEM {
        NUM, OPEN, CLOSE, END, PLUS, MUL, MINUS, DIV;
    }

    public ExpressionsParser() {
    }

    public Expression parse(String s) throws Exception {
        c = -1;
        a = s.trim();
        Expression answer = null;

        nextlexem();
        answer = expr();

        return answer;

    }

    private void nextlexem() throws Exception {
        c++;
        if (c >= a.length()) {
            curlex = LEXEM.END;
            return;
        }
        if (Character.isWhitespace(a.charAt(c))) {
            nextlexem();
            return;
        }
        switch (a.charAt(c)) {
            case '(':
                curlex = LEXEM.OPEN;
                break;
            case ')':
                curlex = LEXEM.CLOSE;
                break;
            case '+':
                curlex = LEXEM.PLUS;
                break;
            case '-':
                if (curlex == LEXEM.NUM || curlex == LEXEM.CLOSE) {
                    curlex = LEXEM.MINUS;
                } else {
                    if (Character.isDigit(a.charAt(c + 1))) {
                        c++;
                        v1 = "-" + findNumber();
                        curlex = LEXEM.NUM;
                        return;
                    }
                }
                break;
            case '*':
                curlex = LEXEM.MUL;
                break;
            case '/':
                curlex = LEXEM.DIV;
                break;
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case '0':
                v1 = findNumber();
                curlex = LEXEM.NUM;
                break;
            case ' ':
                break;
        }
    }


    private Expression expr() throws Exception {

        Expression a = item();
        while (curlex == LEXEM.PLUS || curlex == LEXEM.MINUS) {
            LEXEM t = curlex;
            nextlexem();
            if (t == LEXEM.PLUS) {
                a = new Add(a, item());
            }
            if (t == LEXEM.MINUS) {
                a = new Subtract(a, item());
            }
        }
        return a;

    }

    private Expression item() throws Exception {
        Expression a = mult();
        while (curlex == LEXEM.MUL || curlex == LEXEM.DIV) {
            LEXEM t = curlex;
            nextlexem();
            if (t == LEXEM.MUL) {
                a = new Multiply(a, mult());
            }
            if (t == LEXEM.DIV) {
                a = new Divide(a, mult());
            }
        }
        return a;
    }

    private Expression mult() throws Exception {
        Expression answer = null;
        switch (curlex) {
            case NUM:
                answer = new Const(Double.parseDouble(v1));
                nextlexem();
                break;
            case OPEN:
                nextlexem();
                answer = expr();
                if (curlex == LEXEM.CLOSE) nextlexem();
                break;


        }
        return answer;
    }

    private String findNumber() throws Exception {
        boolean f = false;
        String answer = "";
        while (c < a.length() && (Character.isDigit(a.charAt(c)) || a.charAt(c) == '.')) {
            if (a.charAt(c) == '.') {
                if (!f) {
                    f = true;
                } else {
                    throw new Exception("Incorrect insertion of data!");
                }
            }
            answer += a.charAt(c);
            c++;
        }
        c--;
        return answer;
    }
}
