import java.util.Scanner;

public class CliCalculator2 {

    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double currentResult = 0.0;
        boolean isFirstCalculation = true;

        System.out.println("--- Java CLI Hesap Makinesi V2 ---");
        System.out.println("Çikmak için 'exit' veya 'q' yazin");

        while (true) {
            try {
                String operator;
                double num2;

                if (isFirstCalculation) {
                    System.out.print("İlk sayiyi girin: ");
                    String num1Str = scanner.nextLine();
                    if ("q".equals(checkValue(num1Str))){
                        break;
                    }
                    
                    System.out.print("İşlemi girin (+, -, *, /, %): ");
                    operator = scanner.nextLine();
                    if ("q".equals(checkValue(operator))){
                        break;
                    }

                    System.out.print("İkinci sayiyi girin: ");
                    String num2Str = scanner.nextLine();
                    if ("q".equals(checkValue(num2Str))){
                        break;
                    }

                    double num1 = Double.parseDouble(num1Str);
                    num2 = Double.parseDouble(num2Str);
                    currentResult = calculate(num1, num2, operator);
                    isFirstCalculation = false;
                } else {
                    System.out.println("\n--- Mevcut Sonuç: " + currentResult + " ---");
                    System.out.print("Siradaki işlemi girin: ");
                    operator = scanner.nextLine();
                    if ("q".equals(checkValue(operator))){
                        break;
                    }

                    System.out.print("Sayiyi girin: ");
                    String num2Str = scanner.nextLine();
                    if ("q".equals(checkValue(num2Str))){
                        break;
                    }
                    
                    num2 = Double.parseDouble(num2Str);
                    currentResult = calculate(currentResult, num2, operator);
                }

                System.out.println("Sonuç: " + currentResult);

            } catch (NumberFormatException e) {
                System.out.println("Hata: " + e.getMessage() + ". Lütfen baştan başlayin.");
                isFirstCalculation = true;
            }
        }

        System.out.println("Hesap makinesi kapatildi.");
        scanner.close();
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

    public static String checkValue(String str) {
        String cleanStr = str.trim().toLowerCase();
        if (cleanStr.equalsIgnoreCase("exit") || cleanStr.equalsIgnoreCase("q")) {
            return "q";
        }
        else{
            return cleanStr;
        }
    }
}