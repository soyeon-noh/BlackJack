package com.team.app.service.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.team.app.model.CardVO;
import com.team.app.service.CardDeck;
import com.team.app.service.Gamer;

public class Player implements Gamer {

	protected List<CardVO> pCardList;
	
	protected CardDeck cardDeck;
	protected Scanner scan;
	
	protected Integer pMoney;
	protected Integer intBet;

	public Player() {
		pCardList = new ArrayList<CardVO>();
		cardDeck = new CardDeckImpl();
		scan = new Scanner(System.in);

		//this.money();
	}

	@Override
	public void getCard() {
		// 카드 1장 뽑기

		CardVO cardVO = new CardVO();
		cardVO = cardDeck.hit();

		pCardList.add(cardVO);
	}

	@Override
	public Integer openCard() {
		
		CardVO cardVO = new CardVO();
		int pSize = pCardList.size();
		Integer pSum = null;

		for (int i = 0; i < pSize; i++) {
			cardVO = pCardList.get(i);
			pSum = this.sumPoint();
			System.out.println("플레이어의 카드 : " + cardVO.getCardPattern() + "\t" + cardVO.getCardNumber());
		}
		System.out.println("-".repeat(50));
		System.out.println("카드 점수 : " + pSum);
		System.out.println("-".repeat(50));

		return pSum;
	}


	@Override
	public Integer sumPoint() {
		// TODO player의 카드 점수 합산
		
		int pSum = 0;
		int pSize = pCardList.size();
		
		for (int i = 0; i < pSize; i++) {
			CardVO vo = pCardList.get(i);
			if (vo.getCardNumber().equals("K") || vo.getCardNumber().equals("Q") || vo.getCardNumber().equals("J")) {
				pSum += 10;
				continue;
			} else if (vo.getCardNumber().equals("A")) {
				pSum++;
				continue;
			}
			Integer score = Integer.valueOf(vo.getCardNumber());
			pSum += score;
		}
		return pSum;
	}
	
	
	public Integer pSelect() {

		while (true) {

			System.out.println("히트 하시겠습니까?");
			System.out.println("Hit : H // Stand : S");
			System.out.print(">> ");
			String hit = scan.nextLine();
			System.out.println("-".repeat(50));
			if (hit.equalsIgnoreCase("H")) {
				return 0;
			} else if (hit.equalsIgnoreCase("S")) {
				return null;
			} else {
				System.out.println("H 또는 S만 입력하세요 !!!");
				continue;
			}
		}

	}

	// 베팅

	public Integer getpMoney() {
		return pMoney;
	}

	public void setpMoney(Integer pMoney) {
		this.pMoney = pMoney;
	}

	public Integer getIntBet() {
		return intBet;
	}

	public void setIntBet(Integer intBet) {
		this.intBet = intBet;
	}

	public void money() {
		// 돈이 0원이거나 처음 시작하는 경우 10000원 지급

		if (pMoney == null || pMoney <= 0) {
			System.out.println("10000원이 충전되었습니다");
			pMoney = 10000;
		}else {
			System.out.println("아직 돈이 남았군요..?");
			System.out.println("올인인 경우만 충전할 수 있습니다");
		}
	}

	public void betting() {

		while (true) {

			System.out.println("베팅할 액수를 입력해주세요");
			System.out.print(">> ");
			String strBet = scan.nextLine();

			try {
				intBet = Integer.valueOf(strBet);
			} catch (NumberFormatException e) {
				System.out.println("정수만 입력하세요 !!");
				continue;
			}

			if (intBet > pMoney) {
				System.out.println("입력한 액수가 소지금액보다 큽니다 !!");
				continue;
			}
			pMoney -= intBet;
			break;
		}

		System.out.println("베팅 금액 : " + intBet);
		System.out.println("소지 금액 : " + pMoney);

	}

	public void saveMoney() {

		while (true) {
			FileWriter fileW;
			PrintWriter out;

			System.out.println("저장할 이름을 입력하세요");
			System.out.print(">> ");
			String strName = scan.nextLine();
			if (strName.equals("")) {
				System.out.println("이름은 꼭 입력하세요 !!!");
				continue;
			}

			String fileName = "src/com/team/app/" + strName + ".txt";

			try {
				fileW = new FileWriter(fileName);
				out = new PrintWriter(fileW);

				out.print(pMoney);

				out.flush();
				out.close();

				System.out.println("저장 완료 !");
				return;
				
			} catch (IOException e) {
				System.out.println("파일 생성 오류 !!");
			}
		}
	}

	public void loadMoney() {

		while (true) {

			FileReader fileR;
			BufferedReader buffer;

			System.out.println("불러올 이름을 입력하세요");
			System.out.print(">> ");
			String strName = scan.nextLine();
			if (strName.equals("")) {
				System.out.println("이름은 꼭 입력하세요 !!!");
				continue;
			}

			String fileName = "src/com/team/app/" + strName + ".txt";

			try { 
				fileR = new FileReader(fileName);
				buffer = new BufferedReader(fileR);

				String reader = buffer.readLine();

				pMoney = Integer.valueOf(reader);

				buffer.close();

				System.out.println(fileName + " 파일을 불러왔습니다");
				return;

			} catch (FileNotFoundException e) {
				System.out.println("파일을 찾을 수 없습니다 !!");
				continue;
			} catch (IOException e) {
				System.out.println("파일을 읽을 수 없습니다 !!");
			}
			
		}

	}

}