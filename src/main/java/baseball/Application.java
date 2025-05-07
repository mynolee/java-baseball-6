package baseball;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Application {
    public static void main(String[] args) {
        System.out.println("숫자 야구 게임을 시작합니다.");

        while (true) {
            List<Integer> computerNumbers = getComputerNumbers();
            boolean isWin = false;

            while (!isWin) {
                System.out.print("숫자를 입력해주세요 : ");
                String userInput = Console.readLine();

                validateInput(userInput);
                List<Integer> userNumbers = getNumbers(userInput);

                int[] result = countBallsStrikes(computerNumbers, userNumbers);
                printResult(result[0], result[1]);

                if (result[1] == 3) {
                    System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
                    isWin = true;
                }
            }

            System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
            String restartInput = Console.readLine();
            if (!restartInput.equals("1")) {
                break;
            }
        }
    }

    private static List<Integer> getComputerNumbers() {
        List<Integer> computer = new ArrayList<>();
        while (computer.size() < 3) {
            int number = Randoms.pickNumberInRange(1, 9);
            if (!computer.contains(number)) {
                computer.add(number);
            }
        }
        return computer;
    }

    private static void validateInput(String input) {
        if (input == null || input.length() != 3) {
            throw new IllegalArgumentException("1~9 사이의 서로 다른 숫자 3개를 입력해야 합니다.");
        }

        for (char c : input.toCharArray()) {
            if (c < '1' || c > '9') {
                throw new IllegalArgumentException("1~9 사이의 숫자만 입력해야 합니다.");
            }
        }

        Set<Character> unique = new HashSet<>();
        for (char c : input.toCharArray()) {
            unique.add(c);
        }

        if (unique.size() != 3) {
            throw new IllegalArgumentException("숫자가 중복되면 안 됩니다.");
        }
    }

    private static List<Integer> getNumbers(String input) {
        List<Integer> numbers = new ArrayList<>();
        for (char c : input.toCharArray()) {
            numbers.add(Character.getNumericValue(c));
        }
        return numbers;
    }

    private static int[] countBallsStrikes(List<Integer> computer, List<Integer> user) {
        int balls = 0;
        int strikes = 0;

        for (int i = 0; i < 3; i++) {
            if (computer.get(i).equals(user.get(i))) {
                strikes++;
            } else if (computer.contains(user.get(i))) {
                balls++;
            }
        }
        return new int[]{balls, strikes};
    }

    private static void printResult(int balls, int strikes) {
        if (balls == 0 && strikes == 0) {
            System.out.println("낫싱");
            return;
        }

        StringBuilder sb = new StringBuilder();
        if (balls > 0) sb.append(balls).append("볼 ");
        if (strikes > 0) sb.append(strikes).append("스트라이크");

        System.out.println(sb.toString().trim());
    }
}
