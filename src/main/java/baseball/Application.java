package baseball;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Application {
    public static void main(String[] args) {
        printStartMessage();
        runGame();
    }

    private static void printStartMessage() {
        System.out.println("숫자 야구 게임을 시작합니다.");
    }

    private static void runGame() {
        while (true) {
            playRound();
            if (!askRestart()) break;
        }
    }

    private static void playRound() {
        List<Integer> computer = getComputerNumbers();
        boolean isCorrect = false;
        while (!isCorrect) {
            isCorrect = processTurn(computer);
        }
        printCorrectMessage();
    }

    private static List<Integer> getComputerNumbers() {
        Set<Integer> set = new HashSet<>();
        while (set.size() < 3) {
            set.add(Randoms.pickNumberInRange(1, 9));
        }
        return new ArrayList<>(set);
    }

    private static boolean processTurn(List<Integer> computer) {
        String input = getUserInput();
        validateInput(input);
        List<Integer> user = parseInput(input);
        int[] result = countBallsStrikes(computer, user);
        printResult(result[0], result[1]);
        return result[1] == 3;
    }

    private static String getUserInput() {
        System.out.print("숫자를 입력해주세요 : ");
        return Console.readLine();
    }

    private static void validateInput(String input) {
        if (input.length() != 3 || hasInvalidDigit(input) || hasDuplicate(input)) {
            throw new IllegalArgumentException("1~9 사이의 서로 다른 숫자 3개를 입력해야 합니다.");
        }
    }

    private static boolean hasInvalidDigit(String input) {
        for (char c : input.toCharArray()) {
            if (c < '1' || c > '9') return true;
        }
        return false;
    }

    private static boolean hasDuplicate(String input) {
        Set<Character> set = new HashSet<>();
        for (char c : input.toCharArray()) set.add(c);
        return set.size() != 3;
    }

    private static List<Integer> parseInput(String input) {
        List<Integer> numbers = new ArrayList<>();
        for (char c : input.toCharArray()) {
            numbers.add(Character.getNumericValue(c));
        }
        return numbers;
    }

    private static int[] countBallsStrikes(List<Integer> computer, List<Integer> user) {
        int balls = 0, strikes = 0;
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
        if (balls > 0) {
            sb.append(balls).append("볼 ");
        }
        if (strikes > 0) {
            sb.append(strikes).append("스트라이크");
        }
        System.out.println(sb.toString().trim());
    }

    private static void printCorrectMessage() {
        System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
    }

    private static boolean askRestart() {
        System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
        return Console.readLine().equals("1");
    }
}