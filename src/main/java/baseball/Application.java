package baseball;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        BaseballGame baseballGame = new BaseballGame();
        baseballGame.play();
    }
}

class BaseballGame {

    public void play() {
        System.out.println("숫자 야구 게임을 시작합니다.");

        while (true) {
            List<Integer> computerNumbers = getGenerateComputerNumbers();
            boolean isWin = false;

            while (!isWin) {
                System.out.print("숫자를 입력해주세요 : ");
                String userInput = Console.readLine();
                validateInput(userInput);
                List<Integer> userNumbers = getParseInput(userInput);

                Result result = compare(computerNumbers, userNumbers);
                result.print();

                if (result.isThreeStrike()) {
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

    private List<Integer> getGenerateComputerNumbers() {
        List<Integer> computer = new ArrayList<>();
        while (computer.size() < 3) {
            int randomNumber = Randoms.pickNumberInRange(1, 9);
            if (!computer.contains(randomNumber)) {
                computer.add(randomNumber);
            }
        }
        return computer;
    }

    private void validateInput(String input) {
        if (input.length() != 3) {
            throw new IllegalArgumentException("입력은 세 자리 숫자여야 합니다.");
        }
        if (!input.matches("[1-9]{3}")) {
            throw new IllegalArgumentException("1~9 사이의 서로 다른 숫자 3개를 입력해야 합니다.");
        }
        if (hasDuplicateDigits(input)) {
            throw new IllegalArgumentException("숫자가 중복되면 안 됩니다.");
        }
    }

    private boolean hasDuplicateDigits(String input) {
        return input.charAt(0) == input.charAt(1)
                || input.charAt(0) == input.charAt(2)
                || input.charAt(1) == input.charAt(2);
    }

    private List<Integer> getParseInput(String input) {
        List<Integer> numbers = new ArrayList<>();
        for (char c : input.toCharArray()) {
            numbers.add(Character.getNumericValue(c));
        }
        return numbers;
    }

    private Result compare(List<Integer> computer, List<Integer> user) {
        int strikes = 0;
        int balls = 0;

        for (int i = 0; i < 3; i++) {
            if (computer.get(i).equals(user.get(i))) {
                strikes++;
            } else if (computer.contains(user.get(i))) {
                balls++;
            }
        }

        return new Result(balls, strikes);
    }
}

class Result {
    private final int balls;
    private final int strikes;

    public Result(int balls, int strikes) {
        this.balls = balls;
        this.strikes = strikes;
    }

    public void print() {
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

    public boolean isThreeStrike() {
        return strikes == 3;
    }
}

