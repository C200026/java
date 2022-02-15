public class RpnCalculator {
    // 計算を実行する
    public static int calculate(String expression) {
        String[] inputs = expression.split(" ");    // 数式を半角スペースで分割した配列
        IntStack stack = new IntStack(64);          // 数値を入れるスタック

        for(int i=0; i<inputs.length; i++) {
            String token = inputs[i];               // 今回処理する演算子（operator）・オペランド（operand）
            int num;                                // スタックに格納する数値

            // tokenが演算子（operator）・オペランド（operand）のどちらなのか
            if(isOperator(token)) {
                // tokenが演算子（operator）
                switch(token) {
                    // 加算
                    case "+":
                        num = stack.pop();
                        num += stack.pop();
                        stack.push(num);
                        break;
                    
                    // 減算
                    case "-":
                        /** 問題1
                         * 減算を行う処理を書く*/
                        num = -(stack.pop());
                        num += stack.pop();
                        stack.push(num);                        
                        break;
                    
                    // 乗算
                    case "*":
                        /**問題2
                         * 乗算を行う処理を書く*/
                        num = stack.pop();
                        num *= stack.pop();
                        stack.push(num);                        
                        break;
                    
                    // 除算
                    case "/":
                        /** 問題3
                         * 除算を行う処理を書く*/
                        num = (stack.pop());
                        num = stack.pop()/num;
                        stack.push(num);
                        break;
                }
            } else {
                // tokenがオペランド（operand）
                num = Integer.parseInt(token);
                stack.push(num);
            }

        }

        // 最後にスタックから取り出したものが答え
        return stack.pop();
    }

    // 演算子（operator）であるか判定
    private static boolean isOperator(String token) {
        switch(token) {
            case "+":
            case "-":
            case "*":
            case "/":
                return true;
        }
        return false;
    }
}
