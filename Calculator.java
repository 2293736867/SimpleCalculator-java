import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.*;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class Calculator {
    private final JFrame mainWindow = new JFrame("计算器");
    private final JPanel boxPanel = new JPanel();
    private final JTextField box = new JTextField();

    private final JPanel panel1 = new JPanel();
    private final JButton esc = new JButton("Esc");
    private final JButton backspace = new JButton("<-");

    private final JPanel panel2 = new JPanel();
    private final JButton num0 = new JButton("0");
    private final JButton num1 = new JButton("1");
    private final JButton num2 = new JButton("2");
    private final JButton num3 = new JButton("3");
    private final JButton num4 = new JButton("4");
    private final JButton num5 = new JButton("5");
    private final JButton num6 = new JButton("6");
    private final JButton num7 = new JButton("7");
    private final JButton num8 = new JButton("8");
    private final JButton num9 = new JButton("9");
    private final JButton plus = new JButton("+");
    private final JButton minus = new JButton("-");
    private final JButton multiply = new JButton("*");
    private final JButton divide = new JButton("/");
    private final JButton dot = new JButton(".");
    private final JButton equal = new JButton("=");

    private final Color defaultBackground = num0.getBackground();
    private final Color enteredBackground = new Color(180,180,180);

    private static final String[] buttonNames = {"num7", "num8", "num9", "multiply", "num4","num5", "num6", "divide","num1", "num2", "num3", "plus", "minus", "num0", "dot", "equal","esc","backspace"};
    private static final String iconBase64 = "iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAADzUlEQVRYR+2Xb2hbVRjGn/fctMvwH+ISEZwoDL9UBkJBIZm2tENh91YQCqKld9A/WzsmdLOriLUtVNlaU4Vhpd1sSeewOBBnkg9lty612TqFougQhh/mUAsu2xjD1bRJzivnJqm3rU2rZkOhBy5cLvd5z3Pec97fOYfgaD7DHAZQRsDDzu8FfL/O4LNCaK2TJ4e/V3EpF9xv7DwH8BMF7CxvqPS8676psQ+u2Qb8VTurwfzx7epc9cPAO2dCwX0ZA3ptJ4g6sgZuEOFNlvRVIQ0RyS0MeklNcTZuNBYKli83QDTmbug9CMjcjwXyIaKJwf3PAPRqfgPMXe5dvQCJXEYKY4BlV2KgLQri0+sG/tsZIMbx4ua+t0jK9woz+ZkoBO5PDLxyD4OOrJIBXAPxKFicKKQBkFQV8OTqZVjQXvMGW4ED6wacGWDucm19+sf0d1/cy0DfouQwn2VQPwmaB3M1APXk2gyYjwAiSsSPL9Nm/so/BSREidoyt4evnD+lb3ps2/M1D3BSm7FXNOP4ZDhY4zTk2E9mBbG/qLG3hCDqBDDw+1DLRE7r0OQ18FMsFHyoMhQvA+E0GOWsictz/fvaQXiBifdubAicdxqYfb/1utD4azBGXXXvNrmKUpfAeE7tKZZxf6dfNz9S2rUZIExvbOw9xKDmbNlEwXIiMXDgThD2A2y4d/XVAuzJVnn85uG23Vpx6ioYgQ0NPSOkkWXpXq8ahGV4on7dfDujXWirVIFEaSwSnK4Mx9nSPZld0zDVqEsAPhYLjdSuMAXfpudd5XfsPfg6gBaAhix9U92f2rUaAH1JMt3hbgrcnRhsnWFGtwMi6kBRL4SYShLPain2EeFDh6EQwIOx0Eh4W5XpW6rNT8J1DizhwIq1nJ8DAHPXv+bAhvoer1rBaiXPDbdcWMqBytCvnSCxxdI9Nc5zpWKI0ubG4tSutQpsDigIMfEJBp4a3+GpyNWyzYHGwEWSIik1CMHpu272H/jB5gBgaysi8fEstASnxJ65oxmGrM0AYTr2WbC0MhLvIEa1BLrHdc9orpbF5kfN4h2NjxDjNwbPE1NSXr50Ye7Tw58jq90evvoGE0tICcvwdv8jDribAq+xoHZ1OLF0T/lSDlSErzQTZImle/csOtpLlLp39xi50dokXGDI3+TAZOTYWFn7UE3qm4m6v+KA9mz91mRkUFvMgQxDlPb/wQFflVlFjJO3j0H23awnFg62LVxOfYZ58RbeipeNzVVUtDn6ydGfFwzYm41uHgL4RRA9eIuycQNgK814eSo88ovq4w/Ej1g/Fj6GKgAAAABJRU5ErkJggg==";

    public Calculator() {
        init();
        setButtonsFont();
        addKeyboardEvent();
        addMouseEvent();
    }

    private void init() {
        boxPanel.setLayout(new FlowLayout());
        box.setPreferredSize(new Dimension(350, 30));
        box.setDocument(new LimitedDocument());
        boxPanel.add(box);
        panel2.setLayout(new GridLayout(4, 4, 20, 20));
        panel2.setPreferredSize(new Dimension(350, 350));

        panel1.setLayout(new GridLayout(1,2,20,20));
        panel1.setPreferredSize(new Dimension(350,75));
        panel1.add(esc);
        panel1.add(backspace);

        try
        {
            for(String button:buttonNames)
            {
                if(!button.equals("esc") && !button.equals("backspace"))
                    panel2.add((Component) getClass().getDeclaredField(button).get(this));
            }
        }
        catch (NoSuchFieldException | IllegalAccessException e)
        {
            e.printStackTrace();
            showMessage("创建界面失败，程序退出！");
            mainWindow.dispose();
        }

        mainWindow.setIconImage(new ImageIcon(Base64.getDecoder().decode(iconBase64)).getImage());
        mainWindow.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        mainWindow.setVisible(true);
        mainWindow.setSize(450, 575);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.getContentPane().add(boxPanel);
        mainWindow.getContentPane().add(panel1);
        mainWindow.getContentPane().add(panel2);
        mainWindow.setResizable(false);

        box.requestFocus();
    }

    private void setButtonsFont() {
        Font font = new Font("微软雅黑", Font.BOLD, 17);
        try {
            Method setFontMethod = Class.forName("javax.swing.JButton").getMethod("setFont", Font.class);
            for (String buttonName : buttonNames) {
                Field buttonField = getClass().getDeclaredField(buttonName);
                buttonField.setAccessible(true);
                setFontMethod.invoke(buttonField.get(this), font);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showMessage("字体设置失败，程序退出！");
            mainWindow.dispose();
        }
    }

    private void addMouseEvent() {
        try {
            final Method addEventListenerMethod = Class.forName("javax.swing.JButton").getMethod("addMouseListener", MouseListener.class);
            for (String buttonName : buttonNames) {
                final Field buttonField = getClass().getDeclaredField(buttonName);
                buttonField.setAccessible(true);

                addEventListenerMethod.invoke(buttonField.get(this), new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e)
                    {
                        box.requestFocus();
                        JButton button = (JButton) e.getSource();
                        if(button.equals(equal))
                        {
                            setResult();
                        }
                        else if(button.equals(backspace))
                        {
                            String s = box.getText();
                            if(!s.isEmpty())
                            {
                                box.setText(s.substring(0, s.length()-1));
                            }
                        }
                        else if(button.equals(esc))
                        {
                            box.setText("");
                        }
                        else
                        {
                            char c = button.getText().charAt(0);
                            String boxText = box.getText();
                            if (ExpressionHandle.canInput(boxText, c))
                                box.setText(boxText + c);
                            else
                                showMessage("请点击合法的字符！");
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent mouseEvent) {}

                    @Override
                    public void mouseReleased(MouseEvent mouseEvent) {}

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        ((JButton)e.getSource()).setBackground(enteredBackground);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        ((JButton) e.getSource()).setBackground(defaultBackground);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            showMessage("反射失败，程序退出！");
            mainWindow.dispose();
        }
    }

    private void setResult() {
        if (!box.getText().isEmpty()){
            ExpressionHandle.setExpression(box.getText());
            try {
                showMessage(ExpressionHandle.valid() ? ExpressionHandle.result() : "表达式错误,请重新输入");
            } catch (Exception e) {
                showMessage("除数为0,请重新输入");
            }
            box.setText("");
        } else {
            showMessage("表达式为空,请重新输入");
        }
    }

    private void addKeyboardEvent() {
        box.addKeyListener(new KeyListener() {
            private final Map<Integer,JButton> map = new HashMap<>();
            {
                map.put(KeyEvent.VK_0,num0);
                map.put(KeyEvent.VK_1,num1);
                map.put(KeyEvent.VK_2,num2);
                map.put(KeyEvent.VK_3,num3);
                map.put(KeyEvent.VK_4,num4);
                map.put(KeyEvent.VK_5,num5);
                map.put(KeyEvent.VK_6,num6);
                map.put(KeyEvent.VK_7,num7);
                map.put(KeyEvent.VK_8,num8);
                map.put(KeyEvent.VK_9,num9);
                map.put(KeyEvent.VK_PLUS,plus);
                map.put(KeyEvent.VK_MINUS,minus);
                map.put(KeyEvent.VK_MULTIPLY,multiply);
                map.put(KeyEvent.VK_SLASH,divide);
                map.put(KeyEvent.VK_EQUALS,equal);
                map.put(KeyEvent.VK_ENTER,equal);
                map.put(KeyEvent.VK_PERIOD,dot);
                map.put(KeyEvent.VK_ESCAPE,esc);
                map.put(KeyEvent.VK_BACK_SPACE,backspace);
            }
            @Override
            public void keyTyped(KeyEvent keyEvent) {}

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                int code = keyEvent.getKeyCode();
                if(keyEvent.isShiftDown())
                {
                    if(code == KeyEvent.VK_8)
                        multiply.setBackground(enteredBackground);
                    else if(code == KeyEvent.VK_EQUALS)
                        plus.setBackground(enteredBackground);
                }
                else if(map.containsKey(code))
                {
                    map.get(code).setBackground(enteredBackground);
                    if (code == KeyEvent.VK_ENTER)
                    {
                        setResult();
                    }
                    else if(code == KeyEvent.VK_ESCAPE)
                    {
                        box.setText("");
                    }
                    else if(code == KeyEvent.VK_EQUALS)
                    {
                        setResult();
                    }
                    else
                    {
                        map.get(keyEvent.getKeyCode()).setBackground(enteredBackground);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                map.forEach((k,v)->v.setBackground(defaultBackground));
            }
        });
    }

    public static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "提示信息", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new Calculator();
    }

    class LimitedDocument extends PlainDocument {
        public void insertString(int offset, String str, AttributeSet attrSet) throws BadLocationException {
            if(str.length() == 1)
            {
                String allowCharAsString = "0123456789+-*/.=";
                char c = str.charAt(0);
                if (!allowCharAsString.contains(str)){
                    Calculator.showMessage("请输入合法字符！");
                    return;
                }
                if(!ExpressionHandle.canInput(box.getText(),c)) {
                    if(c == '=')
                        equal.setBackground(defaultBackground);
                    else if(c == '+')
                        plus.setBackground(defaultBackground);
                    else if(c == '-')
                        minus.setBackground(defaultBackground);
                    else if(c == '*')
                        multiply.setBackground(defaultBackground);
                    else if(c == '/')
                        divide.setBackground(defaultBackground);
                    else if(c == '.')
                        dot.setBackground(defaultBackground);
                    if(!box.getText().isEmpty())
                        Calculator.showMessage("请输入合法字符！");
                    return;
                }
                if(c == '=')
                    return;
            }
            super.insertString(offset, str, attrSet);
        }
    }
}

class ExpressionHandle {
    private static String str;
    private static final Deque<Character> operators = new ArrayDeque<>();
    private static final Deque<Double> operands = new ArrayDeque<>();

    public static boolean canInput(String s, char c) {
        str = s;
        if (s == null || s.isEmpty())
            return isDigit(c);
        char lastChar = s.charAt(s.length() - 1);
        if (isDigit(lastChar))
            return true;
        return (!isSign(c) && !isDot(c)) || (!isSign(lastChar) && !isDot(lastChar));
    }

    public static String result() throws Exception {
        int len = str.length();
        for (int i = 0; i < len; ++i) {
            if (isDigit(i)) {
                int i1 = i + 1;
                while (i1 < len && (isDigit(i1) || isDot(i1)))
                    ++i1;
                push(Double.parseDouble(str.substring(i, i1)));
                i = i1 - 1;
            } else {
                while (canCalculate(str.charAt(i)))
                    calculate();
                push(str.charAt(i));
            }
        }
        while (!empty())
            calculate();

        String res = String.format("%.5f", getResultNum());
        if (res.contains(".")) {
            char[] c = res.toCharArray();
            for (len = c.length - 1; len >= 0; --len) {
                if (c[len] != '0')
                    break;
            }
            if (c[len] == '.')
                --len;
            return res.substring(0, len + 1);
        }
        return res;
    }

    private static char getSign() {
        if(!operators.isEmpty())
            return operators.peekLast();
        Calculator.showMessage("运行时错误，运算符栈为空");
        throw new RuntimeException("Operators stack is empty.");
    }

    private static double getNum() {
        if(!operands.isEmpty())
            return operands.peekLast();
        Calculator.showMessage("运行时错误，操作数栈为空");
        throw new RuntimeException("Operators stack is empty.");
    }

    private static void popSign() {
        operators.removeLast();
    }

    private static void popNum() {
        operands.removeLast();
    }

    private static double popAndGetNum() {
        double num = getNum();
        popNum();
        return num;
    }

    private static char popAndGetSign() {
        char sign = getSign();
        popSign();
        return sign;
    }

    private static void push(double num) {
        operands.addLast(num);
    }

    private static void push(char sign) {
        operators.addLast(sign);
    }

    private static double getResultNum() {
        if (!operands.isEmpty())
            return getNum();
        return 0.0;
    }

    private static void calculate() throws Exception {
        double post = popAndGetNum();
        char sign = popAndGetSign();
        double pre = popAndGetNum();
        double result = 0.0;

        switch (sign) {
            case '+':
                result = pre + post;
                break;
            case '-':
                result = pre - post;
                break;
            case '*':
                result = pre * post;
                break;
            case '/':
                if (Math.abs(post) < 1e-6) {
                    throw new Exception("除数为0,请重新输入");
                } else
                    result = pre / post;
                break;
        }
        push(result);
    }

    private static boolean canCalculate(char sign) {
        if (operators.isEmpty())
            return false;
        char t = getSign();
        switch (t) {
            case '+':
            case '-':
                return sign == '+' || sign == '-';
            case '*':
            case '/':
                return sign == '+' || sign == '-' || sign == '*' || sign == '/';
        }
        return false;
    }

    private static boolean empty() {
        return operators.isEmpty();
    }

    public static boolean valid() {
        if (isSign(0) || isDot(0))
            return false;
        int len = str.length();
        for (int i = 0; i < len; ++i) {
            if (isSign(i)) {
                if (isSign(i + 1) || isDot(i + 1))
                    return false;
            } else if (isDot(i)) {
                if (isSign(i + 1) || isDot(i + 1))
                    return false;
            }
        }
        return !isSign(len - 1) && !isDot(len - 1);
    }

    public static void setExpression(String s) {
        str = s;
        operands.clear();
        operators.clear();
    }

    private static boolean isSign(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static boolean isSign(int index) {
        return index < str.length() && isSign(str.charAt(index));
    }

    private static boolean isDot(int index) {
        return index < str.length() && isDot(str.charAt(index));
    }

    private static boolean isDot(char c) {
        return c == '.';
    }

    private static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private static boolean isDigit(int index) {
        return index < str.length() && isDigit(str.charAt(index));
    }
}
