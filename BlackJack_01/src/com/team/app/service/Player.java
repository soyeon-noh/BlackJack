package com.team.app.service;

import java.util.List;

import com.team.app.model.CardVO;
import com.team.app.model.PlayerVO;

public interface Player {
	public List<CardVO> getPCardList();
	public void getCard(CardDeck cardDeck);
	public void openCard();
	public Integer sumPoint();	
	public Boolean pSelect();
}
