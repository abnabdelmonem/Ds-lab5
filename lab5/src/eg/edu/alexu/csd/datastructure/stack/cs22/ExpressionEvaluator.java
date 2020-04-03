package eg.edu.alexu.csd.datastructure.stack.cs22;

public class ExpressionEvaluator implements IExpressionEvaluator {
    @Override
    /**
     * this method take symbols and numbers of intfix
     * then convert it to the postfix state
     * *@param expression
     * infix expression
     * @return postfix expression
     */
    public String infixToPostfix(String expression) {
        String postfix = "";
        Stack operator = new Stack();
        operator.push('$');
        char ch;
        expression = removeSpace(expression);
        expression = negative(expression);
        for (int i = 0; i < expression.length(); i++){
            ch = expression.charAt(i);
            if (Character.isDigit(ch) || Character.isLetter(ch)){
                postfix += ch;
            }
            else if (ch == '('){
                operator.push(ch);
            }
            else if (ch == ')'){
                while ((Character) operator.peek() != '$' && (Character) operator.peek() != '('){
                    postfix += operator.peek();
                    operator.pop();
                }
                operator.pop();
                if (operator.isEmpty()){ //there is a closed bracket without an open one
                    throw  new RuntimeException("Invalid input");
                }
            }
            else {
                if (preced(ch) > preced((Character)operator.peek())){
                    operator.push(ch);
                    postfix += ' ';  //to handel the multi digit case ex: 200+3
                }
                else {
                    while((Character) operator.peek() != '$' && preced(ch) <= preced((Character)operator.peek())){
                        postfix += operator.peek();
                        operator.pop();
                    }
                    operator.push(ch);
                }
            }
        }
        while ((Character) operator.peek() != '$'){
            if ((Character) operator.peek() == '('){ //there is an open bracket without a close one
                throw  new RuntimeException("Invalid input");
            }
            postfix += operator.peek();
            operator.pop();
        }
        return postfix;
    }

    @Override
    /**
     * this method evaluate the result of postfix expression
     * dealing with +,-,*and / operators
     * @param expression
     * postfix expression
     * @return the result of this expression
     */
    public int evaluate(String expression) {
        Stack s = new Stack();
        String temp = "";
        for (int i = 0; i < expression.length(); i++){
            char ch = expression.charAt(i);
            if (Character.isDigit(ch) && ch == '0'){
                while (ch == '0'){
                    temp += '-';
                    i++;
                    ch = expression.charAt(i);
                }
                while (Character.isDigit(ch)){
                    temp += ch;
                    i++;
                    ch = expression.charAt(i);
                }
                i--;
                s.push(Integer.parseInt(temp));
                System.out.println(s.peek());
                temp = "";
            }
            else if (Character.isDigit(ch)){
                while (Character.isDigit(ch)){
                    temp += ch;
                    i++;
                    ch = expression.charAt(i);
                }
                i--;
                s.push(Integer.parseInt(temp));
                System.out.println(s.peek());
                temp = "";
            }
            else if (ch != ' '){
                int val1 , val2;
                val2 = (int)s.pop();
                val1 = (int)s.pop();
                switch (ch){
                    case '-':{
                        s.push(val1 - val2);
                        break;
                    }
                    case '+':{
                        s.push(val1 + val2);
                        break;
                    }
                    case '*':{
                        s.push(val1 * val2);
                        break;
                    }
                    case '/':{
                        s.push(val1 / val2);
                        break;
                    }
                }
            }
        }
        return (int)s.pop();
    }

    static int preced(char ch) {
        if(ch == '+' || ch == '-') {
            return 1;
        }
        else if(ch == '*' || ch == '/') {
            return 2;
        }
        else {
            return 0;
        }
    }

    static String removeSpace(String expression){ //remove spaces and handel invalid inputs
        String infix = "";
        for (int i = 0; i < expression.length(); i++){
            char ch = expression.charAt(i);
            if (ch != ' '){
                infix += ch;
            }
        }
        if (valid(infix)){
            throw new RuntimeException("Invalid input");
        }
        for (int i = 1; i < infix.length(); i++){ // check for valid infix
            char ch = infix.charAt(i);
            char chPrevious = infix.charAt(i-1);
            if ((ch == '*' || ch == '/' || ch == '+' ) && (chPrevious == '*' || chPrevious == '/' || chPrevious == '+')){ //two continues operators ex: 5++6, 6+*5
                throw new RuntimeException("Invalid input");
            }
            else if (Character.isLetter(ch) && Character.isLetter(chPrevious)){ //in case of more than one letter ex: ab + c
                throw new RuntimeException("Invalid input");
            }
            ch = infix.charAt(0); //the first char is operator ex: *5+2
            if (ch == '*' || ch == '/' || ch == '+'){
                throw new RuntimeException("Invalid input");
            }
            ch = infix.charAt(infix.length()-1); //the last char is operator ex: 5+4/
            if (ch == '*' || ch == '/' || ch == '+' || ch == '-'){
                throw new RuntimeException("Invalid input");
            }
        }
        return infix;
    }
    public static boolean valid(String expression){
        for (int i = 0 ; i < expression.length(); i++){
            char ch = expression.charAt(i);
            if (!(Character.isLetter(ch)) && !(Character.isDigit(ch)) && ch != '+' && ch != '*' && ch != '-' && ch != '/' && ch != '-' && ch != '(' && ch != ')'){
                return true;
            }
        }
        return false;
    }

    public static String negative(String expression){
        String newExpression = String.valueOf(expression.charAt(0));
        for (int i = 1; i < expression.length(); i++){
            char ch = expression.charAt(i);
            char chPrevious = expression.charAt(i-1);
            if (ch == '-' && !(Character.isDigit(chPrevious) || Character.isLetter(chPrevious))){ // a minus operator before it an operand
                newExpression += '0';
            }
            else {
                newExpression += ch;
            }
        }
        if (newExpression.charAt(0) == '-'){ // if the first letter was a negative sign
            String temp;
            temp = '0' + newExpression.substring(1,newExpression.length());
            newExpression = temp;
        }
        return newExpression;
    }
}
