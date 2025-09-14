package com.minigames.snake.view;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.DefaultListModel;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.minigames.snake.model.GameRecord;
import com.minigames.snake.model.GameSetting;
import com.minigames.snake.presenter.SnakeLobbyPresenter;
import com.minigames.snake.presenter.SnakeMatchPresenter;

@RunWith(GUITestRunner.class)
@SuppressWarnings("java:S2699")
public class HistoryWindowTest extends AssertJSwingJUnitTestCase {

	private FrameFixture window;
	private SnakeLobbyPresenter lobbyPresenter;
	private SnakeWindowView snakeView;

	@Override
	protected void onSetUp() {
		lobbyPresenter = mock(SnakeLobbyPresenter.class);
		snakeView = GuiActionRunner.execute(() -> {
			return new SnakeWindowView(lobbyPresenter, mock(SnakeMatchPresenter.class));
		});
		window = new FrameFixture(robot(), snakeView);
		window.show();
	}


	@Test
	public void testDeleteSelectedButtonEnabledAtItemSelection() {
		GuiActionRunner.execute(() -> {
			snakeView.show("History panel");
			snakeView.getHistoryPanel().getListModel()
					.addElement(new GameRecord("1", 10, LocalDate.now(), new GameSetting("2", 10, 10, 10)));
		});
		window.button("deleteSelectedButton").requireDisabled();
		window.list("historyList").clickItem(0);
		window.button("deleteSelectedButton").requireEnabled();
	}

	@Test
	public void testDeleteSelectedButtonEnabledAtSelectionChange() {
		GuiActionRunner.execute(() -> {
			snakeView.show("History panel");
			GameSetting setting = new GameSetting("2", 10, 10, 10);
			GameRecord record1 = new GameRecord("1", 10, LocalDate.now(), setting);
			GameRecord record2 = new GameRecord("3", 10, LocalDate.now(), setting);
			Collection<GameRecord> records = new ArrayList<>();
			records.add(record1);
			records.add(record2);
			snakeView.getHistoryPanel().getListModel().addAll(records);
		});
		window.list("historyList").clickItem(0);
		window.button("deleteSelectedButton").requireEnabled();
		window.list("historyList").clickItem(1);
		window.button("deleteSelectedButton").requireEnabled();
	}

	@Test
	public void testHistoryPanelDeleteAll() {
		GuiActionRunner.execute(() -> {
			snakeView.show("History panel");
		});
		Collection<GameRecord> history = new ArrayList<>();
		GameSetting setting = new GameSetting("2", 10, 10, 10);
		GameRecord record1 = new GameRecord("1", 10, LocalDate.now(), setting);
		GameRecord record2 = new GameRecord("3", 10, LocalDate.now(), setting);
		history.add(record1);
		history.add(record2);
		GuiActionRunner.execute(() -> {
			snakeView.getHistoryPanel().getListModel().addAll(history);
		});
		window.button("deleteAllButton").click();
		verify(lobbyPresenter).clearGameHistory(snakeView);
		verifyNoMoreInteractions(lobbyPresenter);
	}

	@Test
	public void testHistoryPanelDeleteSelected() {
		GuiActionRunner.execute(() -> {
			snakeView.show("History panel");
		});
		Collection<GameRecord> history = new ArrayList<>();
		GameSetting setting = new GameSetting("2", 10, 10, 10);
		GameRecord record1 = new GameRecord("1", 10, LocalDate.now(), setting);
		GameRecord record2 = new GameRecord("3", 10, LocalDate.now(), setting);
		history.add(record1);
		history.add(record2);
		DefaultListModel<GameRecord> listModel = snakeView.getHistoryPanel().getListModel();
		GuiActionRunner.execute(() -> {
			listModel.addAll(history);
		});
		window.list("historyList").clickItem(0);
		window.button("deleteSelectedButton").click();
		verify(lobbyPresenter).removeRecord(listModel.getElementAt(0), snakeView);
		verifyNoMoreInteractions(lobbyPresenter);
	}

}
