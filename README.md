# java-chess

### 체스미션 3,단계- 승패 및 점수

### 1. 기능 요구 사항
+ King이 잡혔을 때 게임을 종료해야한다.
+ 체스 게임은 현재 남아있는 말에 대한 점수를 구할 수 있어야한다.
  + Queen-9점, Rook-5점, Bishop-3점, Knight-2.5점, Pawn-1점 또는 0.5점
  + 같은 팀 Pawn이 같은 세로 줄에 2개 이상 있으면 0.5점, 그 외 1점 
+ `status` 명령을 입력하면 각 진의 점수와 결과를 출력한다.

+ 애플리케이션을 재시작하더라도 이전에 하던 체스 게임을 다시 시작할 수 있어야 한다.
+ DB를 적용할 때 도메인 객체의 변경을 최소화해야한다.

**schema**
```angular2html
CREATE TABLE chess_game (
id INT AUTO_INCREMENT PRIMARY KEY,
turn ENUM('white', 'black')
);

CREATE TABLE pieces (
id INT AUTO_INCREMENT PRIMARY KEY,
chess_game_id INT,
FOREIGN KEY (chess_game_id) REFERENCES chess_game(id),
file VARCHAR(1),
symbol VARCHAR(5),
piece_rank INT
);

```

### 2. 기능 목록

#### 2-1. 입력
- [x] `` 명령어를 입력받는다.
  - [x] ``는 `start`명령어 이후로 가능하다.


#### 2-2. 메인로직 실행 
- [x] 현재 게임에 대한 점수를 계산한다.
  - [x] Pawn이 같은 세로줄에 2개 이상인지 확인한다.

- [x] 승패를 계산한다.

- [x] King이 잡혔으면 게임을 끝낸다.
  - [x] Check인지 확인한다.
  - [x] Checkmate인지 확인한다.

- [x] 게임을 저장한다.
  - [x] `end` 명령어를 입력하면 진행 중인 게임을 저장하고 게임을 끝낸다.
  - [x] `stauts` 명령어를 누르면 진행 중인 게임을 끝낸고 점수와 승리자를 구한다.
  - [x] `start` 명령어를 누르면 가장 최근 진행한 게임을 불러온다.

#### 2-3. 출력
- [x] 승/패 결과를 기물의 색으로 구분하여 출력한다.
- [x] 현재 점수를 출력한다.

## 우아한테크코스 코드리뷰

- [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)
