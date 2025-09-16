package com.minigames.snake.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.image.ScreenshotTaker;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import com.minigames.snake.model.GameRecord;
import com.minigames.snake.model.GameSetting;
import com.minigames.snake.model.SnakeRepository;
import com.minigames.snake.presenter.ObstaclesSupplier;
import com.minigames.snake.presenter.PositionSupplier;
import com.minigames.snake.presenter.RandomObstaclesSupplier;
import com.minigames.snake.presenter.RandomPositionSupplier;
import com.minigames.snake.presenter.SnakeLobbyPresenter;
import com.minigames.snake.presenter.SnakeMap;
import com.minigames.snake.presenter.SnakeMatchPresenterImpl;

@RunWith(GUITestRunner.class)
public class ViewPresenterMatchIT extends AssertJSwingJUnitTestCase {

	private SnakeRepository repository;
	private FrameFixture window;
	private SnakeMatchPresenterImpl matchPresenter;
	private SnakeWindowView snakeView;
	private SnakeLobbyPresenter lobbyPresenter;

	@Override
	protected void onSetUp() throws Exception {
		repository = mock(SnakeRepository.class);
		matchPresenter = new SnakeMatchPresenterImpl(repository, new RandomObstaclesSupplier(),
				new RandomPositionSupplier());
		lobbyPresenter = mock(SnakeLobbyPresenter.class);
		snakeView = GuiActionRunner.execute(() -> {
			return new SnakeWindowView(lobbyPresenter, matchPresenter);
		});
		window = new FrameFixture(robot(), snakeView);
		window.show();
	}

	@Test
	public void testMatchPanelEndGameCreateRecord() {
		GameSetting setting = new GameSetting("1", 10, 10, 2);

		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
			snakeView.updateLobby();
			matchPresenter.changeSetting(setting, snakeView);
		});
		window.button("startButton").click();
		window.button("quitButton").click();
		ArgumentCaptor<GameRecord> recordCaptor = ArgumentCaptor.forClass(GameRecord.class);
		verify(repository).createRecord(recordCaptor.capture());
		GameRecord capturedRecord = recordCaptor.getValue();
		assertThat(matchPresenter.isPlaying()).isFalse();
		assertThat(capturedRecord.getDate()).isToday();
		assertThat(capturedRecord.getScore()).isZero();
		assertThat(capturedRecord.getSetting()).isEqualTo(setting);
	}

	@Test
	public void testSettingsPanelChangeSettings() {
		GameSetting setting1 = new GameSetting("1", 15, 10, 2);
		GameSetting setting2 = new GameSetting("2", 20, 10, 2);
		Collection<GameSetting> settings = new ArrayList<>();
		settings.add(setting1);
		settings.add(setting2);
		when(lobbyPresenter.loadConfigurations()).thenReturn(settings);
		GuiActionRunner.execute(() -> {
			snakeView.show("Settings panel");
			snakeView.updateLobby();
		});
		window.list("settingsList").selectItem(0);
		window.button("useSettingButton").click();
		assertThat(snakeView.getMatchPanel().getCanvas().getPreferredSize()).isEqualTo(new Dimension(300, 200));
		assertThat(snakeView.getMatchPanel().getCanvas().getCellSize()).isEqualTo(20);
	}

	@Test
	public void testMatchPanelStartGameCanvas() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
			snakeView.updateLobby();
			GameSetting setting = new GameSetting("1", 5, 5, 1);
			matchPresenter.changeSetting(setting, snakeView);
		});
		window.button("startButton").click();
		assertThat(matchPresenter.isPlaying()).isTrue();
		robot().waitForIdle();
		BufferedImage canvas = new ScreenshotTaker().takeScreenshotOf(snakeView.getMatchPanel().getCanvas());
		Collection<Point> rectList = IntStream.range(0, 5)
				.mapToObj(i -> IntStream.range(0, 5).mapToObj(j -> new Point(i, j))).flatMap(Function.identity())
				.collect(Collectors.toList());
		assertThat(rectList)
				.filteredOn(p -> checkRectInImgIsOfColor(canvas, p.x * 60, p.y * 60, 60, 60, Color.LIGHT_GRAY))
				.hasSize(22);
		assertThat(rectList).filteredOn(p -> checkRectInImgIsOfColor(canvas, p.x * 60, p.y * 60, 60, 60, Color.RED))
				.hasSize(1);
		assertThat(rectList)
				.filteredOn(p -> checkRectInImgIsOfColor(canvas, p.x * 60, p.y * 60, 60, 60, Color.DARK_GRAY))
				.hasSize(1);
		assertThat(rectList).filteredOn(p -> checkRectInImgIsOfColor(canvas, p.x * 60, p.y * 60, 60, 60, Color.GREEN))
				.hasSize(1);
	}

	private boolean checkRectInImgIsOfColor(BufferedImage image, int posX, int posY, int width, int height,
			Color checkColor) {
		return IntStream.range(posX, posX + width).allMatch(
				x -> IntStream.range(posY, posY + height).allMatch(y -> image.getRGB(x, y) == checkColor.getRGB()));
	}

	private boolean checkRectInImgsSameColor(BufferedImage image1, BufferedImage image2, int posX, int posY, int width,
			int height) {
		return IntStream.range(posX, posX + width).allMatch(
				x -> IntStream.range(posY, posY + height).allMatch(y -> image1.getRGB(x, y) == image2.getRGB(x, y)));
	}

	@Test
	public void testMatchPanelKeyboardControlsGameCountinues() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
			snakeView.updateLobby();
			GameSetting setting = new GameSetting("1", 5, 5, 1);
			matchPresenter.changeSetting(setting, snakeView);
		});
		((SnakeMatchPresenterImpl) matchPresenter).setSupplierStrategy(new PositionSupplier() {

			@Override
			public Point generateSnakeHeadPosition(SnakeMap map) {
				return new Point(2, 2);
			}

			@Override
			public Point generateApplePosition(SnakeMap map) {
				return new Point(0, 0);
			}
		});
		((SnakeMatchPresenterImpl) matchPresenter).setSupplierStrategy(new ObstaclesSupplier() {

			@Override
			public Collection<Point> generateObstacles(GameSetting configuration) {
				ArrayList<Point> obstacles = new ArrayList<>();
				obstacles.add(new Point(4, 4));
				return obstacles;
			}
		});

		window.button("startButton").click();
		robot().waitForIdle();
		BufferedImage canvas1 = new ScreenshotTaker().takeScreenshotOf(snakeView.getMatchPanel().getCanvas());
		window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_DOWN);
		robot().waitForIdle();
		BufferedImage canvas2 = new ScreenshotTaker().takeScreenshotOf(snakeView.getMatchPanel().getCanvas());
		List<Point> rectList = IntStream.range(0, 5).mapToObj(i -> IntStream.range(0, 5).mapToObj(j -> new Point(i, j)))
				.flatMap(Function.identity())
				.filter(p -> !checkRectInImgsSameColor(canvas1, canvas2, p.x * 60, p.y * 60, 60, 60))
				.collect(Collectors.toList());
		assertThat(rectList).hasSize(2).containsExactlyInAnyOrder(new Point(2, 2), new Point(2, 3));
		assertThat(matchPresenter.isPlaying()).isTrue();
	}

	@Test
	public void testMatchPanelKeyboardControlsGameEnds() {
		GameSetting setting = new GameSetting("1", 5, 5, 24);
		GuiActionRunner.execute(() -> {
			snakeView.show("Match panel");
			snakeView.updateLobby();
			matchPresenter.changeSetting(setting, snakeView);
		});

		window.button("startButton").click();
		window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_DOWN);
		robot().waitForIdle();
		BufferedImage canvas = new ScreenshotTaker().takeScreenshotOf(snakeView.getMatchPanel().getCanvas());

		IntStream.range(0, 300)
				.allMatch(x -> IntStream.range(0, 300).allMatch(y -> canvas.getRGB(x, y) == Color.LIGHT_GRAY.getRGB()));

		ArgumentCaptor<GameRecord> recordCaptor = ArgumentCaptor.forClass(GameRecord.class);
		verify(repository).createRecord(recordCaptor.capture());
		GameRecord capturedRecord = recordCaptor.getValue();
		assertThat(matchPresenter.isPlaying()).isFalse();
		assertThat(capturedRecord.getDate()).isToday();
		assertThat(capturedRecord.getScore()).isZero();
		assertThat(capturedRecord.getSetting()).isEqualTo(setting);
	}

}
