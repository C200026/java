public class NotationConverter {
    public static String convert(String expression) {
        // 後置記法への変換
        StringQueue queue = infixToPostfix(expression);

        // キューから文字列への変換
        StringBuilder result = new StringBuilder();
        while(queue.size() > 0) {
            result.append(queue.deque());
            result.append(' ');
        }
        return result.toString();
    }

    // 中置記法（infix notation）から後置記法（逆ポーランド記法/reverse polish notation）への変換
    private static StringQueue infixToPostfix(String expression) {
        String[] inputs = expression.split(" ");    // 数式を半角スペースで分割した配列
        StringQueue queue = new StringQueue(64);    // 文字列を入れるキュー
        StringStack stack = new StringStack(64);    // 文字列を入れるスタック

        for(int i=0; i<inputs.length; i++) {
            String token = inputs[i];           // 今回処理する演算子（operator）・オペランド（operand）
            int priority = getPriority(token);  // その演算子（operator）・オペランド（operand）の優先度

            // tokenの優先度により分岐
            switch(priority) {
                // オペランド（operand）の場合
                case 0:
                    // キューに追加
                    /** 問題4-a
                     * 後置記法に変換する処理（オペランドの場合）を書く*/
                    queue.enque(token);
                    break;
                
                // 演算子（operatore）の場合
                case 1:
                case 2:
                    // 演算子の優先度に応じて、スタックにある演算子をキューに移動する（スタックが空ではない間）
                    /** 問題4-b
                     * 後置記法に変換する処理（演算子の場合）を書く*/
                //     // tokenをスタックに追加
                //     /** 問題4-c
                //      * 後置記法に変換する処理（演算子の場合）を書く*/    
             
                    // while(!stack.isEmpty()){
                    //     if(priority <= getPriority(stack.peek()))                    
                    //         queue.enque(stack.pop());
                    //     else                            
                    //         break;   
                    // }
                    while(!stack.isEmpty() && priority <= getPriority(stack.peek())) {
                        queue.enque(stack.pop());
                    }

                    stack.push(token);
                    break;                 
            }
        }

        // 全てのスタックにある演算子を、キューに移動する
        /**問題4-d
         * 後置記法に変換する処理（演算子の場合）を書く**/
        while(stack.isEmpty()){
            queue.enque(stack.pop());
        }

        return queue;
    }


    // 演算子（operator）・オペランド（operand）の優先順位
    private static int getPriority(String token) {
        switch(token) {
            case "+":
            case "-":
                return 1;

            case "*":
            case "/":
                return 2;
        }
        return 0;
    }

}
