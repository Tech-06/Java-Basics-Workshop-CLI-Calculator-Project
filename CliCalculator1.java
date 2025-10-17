import java.util.Scanner;

public class CliCalculator1 {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            double currentResult = 0.0;
            boolean isFirstCalculation = true;
            
            System.out.println("--- Java CLI Hesap Makinesi ---");
            System.out.println("İlk işlemi girin");
            System.out.println("Çikmak için exit yazin");
            
            while (true) {
                try {
                    if (isFirstCalculation) {
                        System.out.print("\nİşlem: ");
                        String input = scanner.nextLine().trim();
                        
                        if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("q")) {
                            break;
                        }
                        
                        String[] parts = splitByMainOperator(input);
                        String operator = parts[1];
                        
                        double num1 = evaluateOperand(parts[0]);
                        double num2 = evaluateOperand(parts[2]);
                        
                        currentResult = calculate(num1, num2, operator);
                        isFirstCalculation = false; 
                        
                    } else {
                        System.out.print(currentResult + " ");
                        String input = scanner.nextLine().trim();
                        
                        if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("q")) {
                            break;
                        }
                        
                        String[] parts = input.split("\\s+", 2);
                        if (parts.length < 2) {
                            System.out.println("Hatali format! Lütfen 'operatör sayi' şeklinde girin (örn: / 3).");
                            continue;
                        }
                        
                        String operator = parts[0];
                        double num2 = evaluateOperand(parts[1]);
                        
                        currentResult = calculate(currentResult, num2, operator);
                    }
                    
                    System.out.println("Sonuç: " + currentResult);
                    
                } catch (Exception e) {
                    System.out.println("Hata: " + e.getMessage() + ". Lütfen tekrar deneyin.");
                    isFirstCalculation = true;
                }
            }
            
            System.out.println("Hesap makinesi kapatildi.");
        }
    }

    public static double calculate(double num1, double num2, String operator) {
        switch (operator) {
            case "+" -> {
                return num1 + num2;
            }
            case "-" -> {
                return num1 - num2;
            }
            case "*" -> {
                return num1 * num2;
            }
            case "/" -> {
                if (num2 == 0) {
                    throw new IllegalArgumentException("Sifira bölme hatasi!");
                }
                return num1 / num2;
            }
            case "%" -> {
                return num1 % num2;
            }
            default -> throw new IllegalArgumentException("Geçersiz operatör: " + operator);
        }
    }

    public static double evaluateOperand(String operandStr) {
        String cleanOperand = operandStr.trim().toLowerCase();

        if (cleanOperand.startsWith("sin(")) {
            return Math.sin(extractNumberFromFunction(cleanOperand));
        } else if (cleanOperand.startsWith("cos(")) {
            return Math.cos(extractNumberFromFunction(cleanOperand));
        } else if (cleanOperand.startsWith("tan(")) {
            return Math.tan(extractNumberFromFunction(cleanOperand));
        } else if (cleanOperand.startsWith("cot(")) {
            double angle = extractNumberFromFunction(cleanOperand);
            return 1.0 / Math.tan(angle);
        } else if (cleanOperand.startsWith("log10(")) {
            return Math.log10(extractNumberFromFunction(cleanOperand));
        } else if (cleanOperand.startsWith("log(")) {
            return Math.log(extractNumberFromFunction(cleanOperand));
        } else if (cleanOperand.startsWith("sqrt(")) {
            return Math.sqrt(extractNumberFromFunction(cleanOperand));
        } else {
            return Double.parseDouble(cleanOperand);
        }
    }

    private static double extractNumberFromFunction(String functionCall) {
        int openParen = functionCall.indexOf('(');
        int closeParen = functionCall.lastIndexOf(')');
        if (openParen == -1 || closeParen == -1 || openParen >= closeParen) {
            throw new IllegalArgumentException("Fonksiyon formati hatali: " + functionCall);
        }
        String numberPart = functionCall.substring(openParen + 1, closeParen);
        return Double.parseDouble(numberPart);
    }
    
    private static String[] splitByMainOperator(String expression) {
        char[] operators = {'+', '-', '*', '/', '%'};
        
        for (char op : operators) {
            int opIndex = expression.lastIndexOf(op);
            
            if (opIndex > 0) {
                String operand1 = expression.substring(0, opIndex).trim();
                String operator = String.valueOf(op);
                String operand2 = expression.substring(opIndex + 1).trim();
                return new String[]{operand1, operator, operand2};
            }
        }
        
        throw new IllegalArgumentException("İşlemde geçerli bir operatör bulunamadi (+, -, *, /, %).");
    }
}