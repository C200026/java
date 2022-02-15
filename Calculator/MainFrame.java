import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame implements ActionListener {
    // 入力状態
    enum Status {
        NUMBER, OPERATOR, NONE, RESULT;
    }

    // 記法
    enum Notation {
        RPN, NORMAL;
    }

    private Status status;      // 現在の入力状態
    private Notation notation;  // 現在の記法
    private StringBuilder currentInput; // 入力中のテキスト
    private int currentNumber;  // 入力中の数値
    private int operatorNum;    // 演算子（operator）の個数
    private int operandNum;     // オペランド（operand）の個数

    private JLabel display;
    private JButton modeNormalKey;
    private JButton modeRpnKey;
    private JButton spaceKey;

    MainFrame(String title) {
        // フィールドの初期化
        currentInput = new StringBuilder();
        notation = Notation.RPN;
        clear();

        setTitle(createTitle());        // タイトル
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 閉じるボタンの処理
        setBounds(10, 10, 300, 240);    // ウィンドウウサイズ

        // フレーム内全体
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        // テキスト表示エリア ---------------------------------------------------------
        JPanel displayArea = new JPanel();
        displayArea.setLayout(new BorderLayout());
        displayArea.setMaximumSize(new Dimension(Short.MAX_VALUE, 32));
        displayArea.setPreferredSize(new Dimension(Short.MAX_VALUE, 32));
        displayArea.setBackground(Color.BLACK);
        panel.add(displayArea);

        // テキスト表示ラベル
        JLabel display = new JLabel(createDispText());
        display.setForeground(Color.WHITE);
        display.setHorizontalAlignment(JLabel.RIGHT);
        display.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        displayArea.add(display);
        this.display = display;


        // ボタンエリア ---------------------------------------------------------------
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4));
        buttonPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        panel.add(buttonPanel);

        // 数値ボタンの生成
        JButton[] btn = new JButton[10];
        for (int i = 0; i < 10; i++) {
            JButton b = new JButton("" + i);
            btn[i] = b;
            b.addActionListener(this);
            b.setActionCommand("" + i);
        }

        // 1段目 -----------------------------------
        {
            // 7～9ボタン
            for (int i = 7; i < 10; i++) {
                buttonPanel.add(btn[i]);
            }

            // ÷ボタン
            JButton btnDiv = new JButton("÷");
            btnDiv.setActionCommand("divide");
            btnDiv.addActionListener(this);
            buttonPanel.add(btnDiv);
        }

        // 2段目 -----------------------------------
        {
            // 4～6ボタン
            for (int i = 4; i < 7; i++) {
                buttonPanel.add(btn[i]);
            }

            // ×ボタン
            JButton btnMul = new JButton("×");
            btnMul.setActionCommand("multiply");
            btnMul.addActionListener(this);
            buttonPanel.add(btnMul);
        }

        // 3段目 -----------------------------------
        {
            // 1～3ボタン
            for (int i = 1; i < 4; i++) {
                buttonPanel.add(btn[i]);
            }

            // -ボタン
            JButton btnSub = new JButton("-");
            btnSub.setActionCommand("subtract");
            btnSub.addActionListener(this);
            buttonPanel.add(btnSub);
        }

        // 4段目 -----------------------------------
        {
            // 符号（+/-）ボタン
            JButton btnSign = new JButton("+/-");
            btnSign.setActionCommand("sign");
            btnSign.addActionListener(this);
            buttonPanel.add(btnSign);

            // 0ボタン
            buttonPanel.add(btn[0]);

            // スペースボタン
            JButton btnSpace = new JButton("␣");
            btnSpace.setActionCommand("space");
            btnSpace.addActionListener(this);
            buttonPanel.add(btnSpace);
            spaceKey = btnSpace;

            // +ボタン
            JButton btnAdd = new JButton("+");
            btnAdd.setActionCommand("add");
            btnAdd.addActionListener(this);
            buttonPanel.add(btnAdd);
        }

        // 5段目 -----------------------------------
        {
            // クリア（C）ボタン
            JButton btnClear = new JButton("C");
            btnClear.setActionCommand("clear");
            btnClear.addActionListener(this);
            buttonPanel.add(btnClear);

            // NORMALボタン
            JButton btnNormal = new JButton("NORMAL");
            btnNormal.setActionCommand("mode_NORMAL");
            btnNormal.addActionListener(this);
            btnNormal.setFont(new Font(Font.DIALOG, Font.PLAIN, 8));
            buttonPanel.add(btnNormal);
            modeNormalKey = btnNormal;

            // RPNボタン
            JButton btnRPN = new JButton("RPN");
            btnRPN.setActionCommand("mode_RPN");
            btnRPN.addActionListener(this);
            buttonPanel.add(btnRPN);
            modeRpnKey = btnRPN;
            
            // 計算（=）ボタン
            JButton btnCalc = new JButton("=");
            btnCalc.setActionCommand("calculate");
            btnCalc.addActionListener(this);
            buttonPanel.add(btnCalc);
        }

        // NORMALボタン、RPNボタンの状態反映
        checkModeButton();

        // ウィンドウに追加
        getContentPane().add(panel);
    }

    // ボタンを押したときの処理
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        switch (cmd) {
            // 0～9ボタン
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                if(status == Status.RESULT) { 
                    clear();
                } else if(status == Status.OPERATOR) {
                    currentInput.append(" ");
                }
                status = Status.NUMBER;
                currentNumber *= 10;
                currentNumber += Integer.parseInt(cmd);
                break;
            
            // 演算子（+,-,×,÷）ボタン
            case "add":
            case "subtract":
            case "multiply":
            case "divide":
                if(operatorNum + 1 >= operandNum && notation == Notation.RPN) {
                    if(status != Status.NUMBER || operatorNum >= operandNum) { break; }
                }
                if(status == Status.NUMBER) {
                    currentInput.append(currentNumber + " ");
                    currentNumber = 0;
                    operandNum++;
                } else if(status == Status.OPERATOR) {
                    if(notation == Notation.NORMAL) {
                        currentInput.deleteCharAt(currentInput.length() - 1);
                        operatorNum--;
                    } else {
                        currentInput.append(" ");
                    }
                } else if(status == Status.RESULT) {
                    currentInput.append(" ");
                }
                status = Status.OPERATOR;
                switch(cmd) {
                    case "add": currentInput.append("+"); break;
                    case "subtract": currentInput.append("-"); break;
                    case "multiply": currentInput.append("*"); break;
                    case "divide": currentInput.append("/"); break;
                }   
                operatorNum++;
                break;

            // 計算（=）ボタン
            case "calculate":
                if(status == Status.NONE) { break; }
                if(status == Status.OPERATOR && notation == Notation.NORMAL) { break; }
                int result = calculate(createDispText());
                System.out.println(result);
                clear();
                currentInput.append(""+result);
                status = Status.RESULT;
                operandNum++;
                break;

            // 符号（+/-）ボタン
            case "sign":
                if(status != Status.NUMBER) { break; }
                currentNumber *= -1;
                break;

            // クリア（C）ボタン
            case "clear":
                if(status == Status.NUMBER) {
                    currentNumber = 0;
                    status = Status.NONE;
                } else {
                    clear();
                }
                break;

            // NORMALボタン
            case "mode_NORMAL":
                changeMode(Notation.NORMAL);
                break;

            // RPNボタン
            case "mode_RPN":
                changeMode(Notation.RPN);
                break;

            // スペース（␣）ボタン
            case "space":
                if(status == Status.NONE || status == Status.OPERATOR || notation == Notation.NORMAL) { break; }
                if(status == Status.RESULT) { currentInput.append(" "); }
                else {
                    currentInput.append(currentNumber + " ");
                    currentNumber = 0;
                    operandNum++;
                }
                status = Status.NONE;
                break;
        }

        // テキスト表示
        display.setText(createDispText());
    }

    // タイトル名の生成
    String createTitle() {
        StringBuilder title = new StringBuilder("Calculator - ");
        title.append(notation);
        return title.toString();
    }

    // ボタンの有効・無効の切り替え
    void checkModeButton() {
        modeNormalKey.setEnabled(notation != Notation.NORMAL);
        modeRpnKey.setEnabled(notation != Notation.RPN);
        spaceKey.setEnabled(notation == Notation.RPN);
    }

    // 計算モードの切り替え
    void changeMode(Notation m) {
        notation = m;
        setTitle(createTitle());
        checkModeButton();
        clear();
    }

    // 計算式の画面表示
    String createDispText() {
        StringBuilder text = new StringBuilder();
        text.append(currentInput.toString());
        if(status == Status.NUMBER || currentInput.length() < 1) { text.append(currentNumber); }
        return text.toString();
    }

    // 入力データの消去
    void clear() {
        currentInput.delete(0, currentInput.length());
        currentNumber = 0;
        status = Status.NONE;
        operatorNum = 0;
        operandNum = 0;
    }

    // 計算を実行
    int calculate(String str) {
        switch(notation) {
            case NORMAL:
                str = NotationConverter.convert(str);

            case RPN:
                return RpnCalculator.calculate(str);
        }

        return 0;
    }
}
