package game;

import java.util.Arrays;
import java.util.Scanner;

public class BaseballGame {
    public static void main(String[] args) {
        System.out.println("숫자 야구 게임 시작!!");
        startGame();
    }

    public static void startGame() {
        // 1. 랜덤으로 3자리의 서로 다른 수를 정답으로 생성
        int[] answer = generateRandomAnswer();

        playGame(answer);
    }

    public static int[] generateRandomAnswer() {
        int[] answer = new int[3];
        int count = 0;

        while (count < 3) {
            int num = (int) (Math.random() * 9) + 1;
            if (isNotDuplicate(num, answer)) {
                answer[count] = num;
                count++;
            }
        }
        System.out.println(Arrays.toString(answer));
        return answer;
    }

    public static void playGame(int[] answer) {
        while (true) {
            // 2. 사용자로부터 3자리의 숫자 입력 받음
            int[] inputNumsFromUser = inputNumsFromUser();
            // 3. 정답인지 확인하는 로직
            if (doesInputMatchAnswer(answer, inputNumsFromUser)) {
                // 4. 게임 종료 후 게임을 다시 시작할지, 종료할지 선택
                chooseRestartOrExit();
                break;
            }
        }
    }

    public static int[] inputNumsFromUser() {
        Scanner scanner = new Scanner(System.in);

        int[] inputNumsFromUser = new int[3];

        while (true) {
            System.out.println("1부터 9까지 서로 다른 수로 이루어진 3자리 수를 입력해주세요: ");
            // 입력한 그대로 한 줄로 들어감
            String inputNums = scanner.nextLine();
            inputNums = inputNums.replaceAll(" ", "");

            // 전체 길이 확인
            if (inputNums.length() != inputNumsFromUser.length) {
                System.out.println("3자리 숫자를 입력해주세요.");
                continue;
            }

            // 한 자리씩 확인
            boolean isValid = true;
            for (int i = 0; i < inputNums.length(); i++) {
                char eachChar = inputNums.charAt(i);

                // 숫자인지 확인
                if (isNotNumber(eachChar)) {
                    System.out.println("숫자를 입력해주세요.");
                    isValid = false;
                    break;
                }

                // 범위 확인
                int eachCharToNumber = Character.getNumericValue(eachChar);
                if (eachCharToNumber < 1 || eachCharToNumber > 9) {
                    System.out.println("1부터 9까지의 숫자를 입력해야합니다.");
                    isValid = false;
                    break;
                }

                // 중복 확인
                if (isDuplicate(eachCharToNumber, inputNumsFromUser)) {
                    System.out.println("입력한 숫자는 중복이 없어야합니다.");
                    isValid = false;
                    break;
                }

                inputNumsFromUser[i] = eachCharToNumber;
            }

            if (isValid) {
                return inputNumsFromUser;
            }
        }
    }

    public static boolean doesInputMatchAnswer(int[] answer, int[] inputNumsFromUser) {
        int countStrike = 0;
        int countBall = 0;

        // 같은 자리 + 같은 숫자 = 스트라이크
        for (int i = 0; i < answer.length; i++) {
            if (answer[i] == inputNumsFromUser[i]) {
                countStrike++;
            }
        }

        // 다른 자리 + 존재하는 숫자 = 볼
        for (int i = 0; i < answer.length; i++) {
            for (int j = 0; j < inputNumsFromUser.length; j++) {
                countBall = isBall(answer[i], inputNumsFromUser[j], i, j, countBall);
            }
        }

        // 결과 출력
        if (countStrike == 3) {
            System.out.println("정답입니다!!");
            return true;
        }

        if (countStrike == 0 && countBall == 0) {
            System.out.println("낫싱");
        }

        System.out.println(countStrike + " 스트라이크, " + countBall + " 볼 입니다.");

        return false;
    }

    public static void chooseRestartOrExit() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1번. 게임을 다시 시작합니다!");
            System.out.println("2번. 게임을 종료합니다.");
            System.out.println("1번과 2번 중 원하는 번호를 입력해주세요.");

            int chooseNum = scanner.nextInt();
            if (chooseNum == 1) {
                System.out.println("게임을 다시 시작합니다.");
                startGame();
            }
            if (chooseNum == 2) {
                System.out.println("게임을 종료합니다.");
                break;
            }
            System.out.println("1번과 2번 중에서 입력해주세요. (숫자만 입력)");
        }
    }

    private static boolean isNotDuplicate(int num, int[] answer) {
        for (int eachAnswer : answer) {
            if (eachAnswer == num) {
                return false;
            }
        }
        return true;
    }

    private static boolean isDuplicate(int num, int[] inputNums) {
        return !isNotDuplicate(num, inputNums);
    }

    private static boolean isNotNumber(char inputNumToChar) {
        if (Character.isDigit(inputNumToChar)) {
            return false;
        }
        return true;
    }

    private static int isBall(
            int answer,
            int inputNumFromUser,
            int indexOfAnswer,
            int indexOfInputNumFromUser,
            int countBall
    ) {
        if (answer == inputNumFromUser && indexOfAnswer != indexOfInputNumFromUser) {
            countBall++;
        }
        return countBall;
    }
}
