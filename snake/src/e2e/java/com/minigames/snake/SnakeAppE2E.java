package com.minigames.snake;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JFrame;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.image.ScreenshotTaker;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.assertj.swing.launcher.ApplicationLauncher;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.minigames.snake.model.BaseEntity;
import com.minigames.snake.model.GameRecord;
import com.minigames.snake.model.GameSetting;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceConfiguration;

@RunWith(GUITestRunner.class)
@SuppressWarnings({ "java:S2699", "java:S3577" })
public class SnakeAppE2E extends AssertJSwingJUnitTestCase {

	private FrameFixture window;
	private EntityManagerFactory emf;

	@Override
	protected void onSetUp() throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put(PersistenceConfiguration.JDBC_DRIVER, "com.mysql.cj.jdbc.Driver");
		map.put(PersistenceConfiguration.JDBC_URL, "jdbc:mysql://localhost:3306/snakedb");
		map.put(PersistenceConfiguration.JDBC_USER, "root");
		map.put(PersistenceConfiguration.JDBC_PASSWORD, "root");

		emf = new PersistenceConfiguration("SnakePU").properties(map).managedClass(BaseEntity.class)
				.managedClass(GameRecord.class).managedClass(GameSetting.class).createEntityManagerFactory();
		emf.getSchemaManager().create(true);
		ApplicationLauncher.application("com.minigames.snake.SnakeApp").start();
		window = WindowFinder.findFrame(new GenericTypeMatcher<JFrame>(JFrame.class) {
			@Override
			protected boolean isMatching(JFrame component) {
				return "Turn-based Snake".equals(component.getTitle()) && component.isShowing();
			}
		}).using(robot());
	}

	@Override
	protected void onTearDown() throws Exception {
		emf.getSchemaManager().drop(true);
		emf.close();
	}

	private void addRecord(GameRecord... records) {
		GuiActionRunner.execute(() -> {
			emf.runInTransaction(em -> Arrays.stream(records).forEach(em::persist));
			SnakeApp.updateUI();
		});
	}

	private void addSetting(GameSetting... settings) {
		GuiActionRunner.execute(() -> {
			emf.runInTransaction(em -> Arrays.stream(settings).forEach(em::persist));
			SnakeApp.updateUI();
		});
	}

	@Test
	public void testHistoryDeleteSelected() {
		GameSetting gSetting = new GameSetting("2", 10, 10, 10);
		GameRecord gRecord1 = new GameRecord("1", 10, LocalDate.now(), gSetting);
		GameRecord gRecord2 = new GameRecord("3", 20, LocalDate.now(), gSetting);
		addSetting(gSetting);
		addRecord(gRecord1, gRecord2);
		window.button("historyButton").click();
		window.list("historyList").selectItem(0);
		String[] selected = window.list("historyList").selection();
		window.button("deleteSelectedButton").click();
		assertThat(window.list("historyList").contents()).doesNotContain(selected);
	}

	@Test
	public void testHistoryDeleteAll() {
		GameSetting gSetting = new GameSetting("2", 10, 10, 10);
		GameRecord gRecord1 = new GameRecord("1", 10, LocalDate.now(), gSetting);
		GameRecord gRecord2 = new GameRecord("3", 20, LocalDate.now(), gSetting);
		addSetting(gSetting);
		addRecord(gRecord1, gRecord2);
		window.button("historyButton").click();
		window.button("deleteAllButton").click();
		window.list("historyList").requireItemCount(0);
	}

	@Test
	public void testHistoryHighScore() {
		GameSetting gSetting = new GameSetting("2", 10, 10, 10);
		GameRecord gRecord1 = new GameRecord("1", 10, LocalDate.now(), gSetting);
		GameRecord gRecord2 = new GameRecord("3", 20, LocalDate.now(), gSetting);
		addSetting(gSetting);
		addRecord(gRecord1, gRecord2);
		window.button("historyButton").click();
		window.label("highScoreLabel").requireText("All time high score: 20");
	}

	@Test
	public void testSettingSelection() {
		GameSetting gSetting = new GameSetting("1", 10, 10, 10);
		addSetting(gSetting);
		window.button("settingsButton").click();
		window.list("settingsList").selectItem(0);
		window.textBox("newNameTextBox").requireText("SETTING_1");
	}

	@Test
	public void testSettingsUse() {
		GameSetting gSetting = new GameSetting("2", 10, 10, 10);
		addSetting(gSetting);
		window.button("settingsButton").click();
		window.list("settingsList").selectItem(0);
		window.button("useSettingButton").click();
		window.button("matchButton").click();
		window.button("startButton").requireEnabled();
		robot().waitForIdle();
		BufferedImage img = new ScreenshotTaker().takeScreenshotOf(window.panel("matchCanvas").target());
		assertThat(img.getHeight()).isEqualTo(300);
		assertThat(img.getWidth()).isEqualTo(300);
		IntStream.range(0, 300).forEach(x -> IntStream.range(0, 300)
				.forEach(y -> assertThat(img.getRGB(x, y)).isEqualTo(Color.LIGHT_GRAY.getRGB())));
	}

	@Test
	public void testSettingsDelete() {
		GameSetting gSetting1 = new GameSetting("2", 10, 10, 10);
		GameSetting gSetting2 = new GameSetting("1", 20, 10, 30);
		addSetting(gSetting1, gSetting2);
		window.button("settingsButton").click();
		window.list("settingsList").selectItem(0);
		String[] selected = window.list("settingsList").selection();
		window.button("deleteSettingButton").click();
		assertThat(window.list("settingsList").contents()).doesNotContain(selected);
	}

	@Test
	public void testSettingsRename() {
		GameSetting gSetting = new GameSetting("1", 10, 10, 10);
		addSetting(gSetting);
		window.button("settingsButton").click();
		window.list("settingsList").selectItem(0);
		window.textBox("newNameTextBox").deleteText().enterText("brand new name");
		window.button("renameButton").click();
		window.list("settingsList").requireItemCount(1);
		assertThat(window.list("settingsList").contents()[0].split(":")[0]).isEqualTo("brand new name");
	}

	@Test
	public void testSettingsCreate() {
		window.button("settingsButton").click();
		window.textBox("nameTextBox").deleteText().enterText("Many obstacles");
		window.textBox("widthTextBox").enterText("20");
		window.textBox("heightTextBox").enterText("5");
		window.textBox("obstaclesTextBox").enterText("40");
		window.button("submitButton").click();
		assertThat(window.list("settingsList").contents()).contains("Many obstacles: dimension (20×5) - obstacles 40");
	}

	@Test
	public void testMatchStart() {
		GameSetting gSetting = new GameSetting("1", 5, 5, 1);
		addSetting(gSetting);
		window.button("settingsButton").click();
		window.list("settingsList").selectItem(0);
		window.button("useSettingButton").click();
		window.button("matchButton").click();
		window.button("startButton").click();
		window.label("scoreLabel").requireText("Current score: 0");
		window.label("messageLabel").requireText("In game");
		window.button("startButton").requireDisabled();
		window.button("quitButton").requireEnabled();
		window.button("historyButton").requireDisabled();
		window.button("settingsButton").requireDisabled();
		robot().waitForIdle();
		BufferedImage img = new ScreenshotTaker().takeScreenshotOf(window.panel("matchCanvas").target());

		Collection<Point> rectList = IntStream.range(0, 5)
				.mapToObj(i -> IntStream.range(0, 5).mapToObj(j -> new Point(i, j))).flatMap(Function.identity())
				.collect(Collectors.toList());

		assertThat(rectList).filteredOn(p -> checkRectInImgIsOfColor(img, p.x * 60, p.y * 60, 60, 60, Color.LIGHT_GRAY))
				.hasSize(22);
		assertThat(rectList).filteredOn(p -> checkRectInImgIsOfColor(img, p.x * 60, p.y * 60, 60, 60, Color.RED))
				.hasSize(1);
		assertThat(rectList).filteredOn(p -> checkRectInImgIsOfColor(img, p.x * 60, p.y * 60, 60, 60, Color.DARK_GRAY))
				.hasSize(1);
		assertThat(rectList).filteredOn(p -> checkRectInImgIsOfColor(img, p.x * 60, p.y * 60, 60, 60, Color.GREEN))
				.hasSize(1);
	}

	@Test
	public void testMatchUpdateGameContinues() {
		GameSetting gSetting = new GameSetting("1", 5, 5, 0);
		addSetting(gSetting);
		window.button("settingsButton").click();
		window.list("settingsList").selectItem(0);
		window.button("useSettingButton").click();
		window.button("matchButton").click();
		window.button("startButton").click();
		robot().waitForIdle();
		BufferedImage img1 = new ScreenshotTaker().takeScreenshotOf(window.panel("matchCanvas").target());
		Point start = findColor(5, 5, img1, Color.GREEN);
		Point apple = findColor(5, 5, img1, Color.RED);
		Point newPos = moveOneCellWhileAvoidingApple(start, apple, new Dimension(5, 5));
		robot().waitForIdle();
		BufferedImage img2 = new ScreenshotTaker().takeScreenshotOf(window.panel("matchCanvas").target());
		window.label("scoreLabel").requireText("Current score: 0");
		window.label("messageLabel").requireText("In game");
		window.button("startButton").requireDisabled();
		window.button("quitButton").requireEnabled();
		window.button("historyButton").requireDisabled();
		window.button("settingsButton").requireDisabled();
		assertThat(checkRectInImgIsOfColor(img2, start.x * 60, start.y * 60, 60, 60, Color.LIGHT_GRAY)).isTrue();
		assertThat(checkRectInImgIsOfColor(img2, newPos.x * 60, newPos.y * 60, 60, 60, Color.GREEN)).isTrue();
	}

	@Test
	public void testMatchUpdateAquiredScore() {
		GameSetting gSetting = new GameSetting("1", 5, 5, 0);
		addSetting(gSetting);
		window.button("settingsButton").click();
		window.list("settingsList").selectItem(0);
		window.button("useSettingButton").click();
		window.button("matchButton").click();
		window.button("startButton").click();
		robot().waitForIdle();
		BufferedImage img1 = new ScreenshotTaker().takeScreenshotOf(window.panel("matchCanvas").target());
		Point firstApple = findColor(5, 5, img1, Color.RED);
		moveFromXToYAvoidingZ(findColor(5, 5, img1, Color.GREEN), firstApple, null);
		window.label("scoreLabel").requireText("Current score: 1");
		window.label("messageLabel").requireText("In game");
		robot().waitForIdle();
		BufferedImage img2 = new ScreenshotTaker().takeScreenshotOf(window.panel("matchCanvas").target());
		Point newPos = moveOneCellWhileAvoidingApple(firstApple, findColor(5, 5, img2, Color.RED), new Dimension(5, 5));
		robot().waitForIdle();
		BufferedImage img3 = new ScreenshotTaker().takeScreenshotOf(window.panel("matchCanvas").target());
		assertThat(checkRectInImgIsOfColor(img3, firstApple.x * 60, firstApple.y * 60, 60, 60, Color.GREEN)).isTrue();
		assertThat(checkRectInImgIsOfColor(img3, newPos.x * 60, newPos.y * 60, 60, 60, Color.GREEN)).isTrue();
	}

	@Test
	public void testMatchUpdateEndsGameOutOfMapBounds() {
		GameSetting gSetting = new GameSetting("1", 5, 5, 0);
		addSetting(gSetting);
		window.button("settingsButton").click();
		window.list("settingsList").selectItem(0);
		window.button("useSettingButton").click();
		window.button("matchButton").click();
		window.button("startButton").click();
		robot().waitForIdle();
		BufferedImage img1 = new ScreenshotTaker().takeScreenshotOf(window.panel("matchCanvas").target());
		goOutOfMapBoundsWhileAvoidingApples(findColor(5, 5, img1, Color.GREEN), findColor(5, 5, img1, Color.RED),
				new Dimension(5, 5));
		window.label("scoreLabel").requireText("Current score: 0");
		window.label("messageLabel").requireText("No game");
		window.button("startButton").requireEnabled();
		window.button("quitButton").requireDisabled();
		window.button("historyButton").requireEnabled();
		window.button("settingsButton").requireEnabled();
		window.button("historyButton").click();
		assertThat(window.list("historyList").contents())
				.contains(String.format("game: date %s - score 0 - SETTING_1", LocalDate.now()));
	}

	@Test
	public void testMatchUpdateEndsGameSnake() {
		GameSetting gSetting = new GameSetting("1", 5, 5, 0);
		addSetting(gSetting);
		window.button("settingsButton").click();
		window.list("settingsList").selectItem(0);
		window.button("useSettingButton").click();
		window.button("matchButton").click();
		window.button("startButton").click();
		robot().waitForIdle();
		BufferedImage img1 = new ScreenshotTaker().takeScreenshotOf(window.panel("matchCanvas").target());
		Point firstApple = findColor(5, 5, img1, Color.RED);
		moveFromXToYAvoidingZ(findColor(5, 5, img1, Color.GREEN), firstApple, null);
		robot().waitForIdle();
		BufferedImage img2 = new ScreenshotTaker().takeScreenshotOf(window.panel("matchCanvas").target());
		Point newPos = moveOneCellWhileAvoidingApple(firstApple, findColor(5, 5, img2, Color.RED), new Dimension(5, 5));
		moveFromXToYAvoidingZ(newPos, firstApple, null);
		window.label("scoreLabel").requireText("Current score: 1");
		window.label("messageLabel").requireText("No game");
		window.button("startButton").requireEnabled();
		window.button("quitButton").requireDisabled();
		window.button("historyButton").requireEnabled();
		window.button("settingsButton").requireEnabled();
		window.button("historyButton").click();
		assertThat(window.list("historyList").contents())
				.contains(String.format("game: date %s - score 1 - SETTING_1", LocalDate.now()));
	}

	@Test
	public void testMatchUpdateEndsGameObstacle() {
		GameSetting gSetting = new GameSetting("1", 5, 5, 1);
		addSetting(gSetting);
		window.button("settingsButton").click();
		window.list("settingsList").selectItem(0);
		window.button("useSettingButton").click();
		window.button("matchButton").click();
		window.button("startButton").click();
		robot().waitForIdle();
		BufferedImage img1 = new ScreenshotTaker().takeScreenshotOf(window.panel("matchCanvas").target());
		moveFromXToYAvoidingZ(findColor(5, 5, img1, Color.GREEN), findColor(5, 5, img1, Color.DARK_GRAY),
				findColor(5, 5, img1, Color.RED));
		window.label("scoreLabel").requireText("Current score: 0");
		window.label("messageLabel").requireText("No game");
		window.button("startButton").requireEnabled();
		window.button("quitButton").requireDisabled();
		window.button("historyButton").requireEnabled();
		window.button("settingsButton").requireEnabled();
		window.button("historyButton").click();
		assertThat(window.list("historyList").contents())
				.contains(String.format("game: date %s - score 0 - SETTING_1", LocalDate.now()));
	}

	@Test
	public void testMatchEnd() {
		GameSetting gSetting = new GameSetting("1", 5, 5, 0);
		addSetting(gSetting);
		window.button("settingsButton").click();
		window.list("settingsList").selectItem(0);
		window.button("useSettingButton").click();
		window.button("matchButton").click();
		window.button("startButton").click();
		robot().waitForIdle();
		BufferedImage img1 = new ScreenshotTaker().takeScreenshotOf(window.panel("matchCanvas").target());
		moveFromXToYAvoidingZ(findColor(5, 5, img1, Color.GREEN), findColor(5, 5, img1, Color.RED), null);
		window.button("quitButton").click();
		window.label("scoreLabel").requireText("Current score: 1");
		window.label("messageLabel").requireText("No game");
		window.button("startButton").requireEnabled();
		window.button("quitButton").requireDisabled();
		window.button("historyButton").requireEnabled();
		window.button("settingsButton").requireEnabled();
		window.button("historyButton").click();
		assertThat(window.list("historyList").contents())
				.contains(String.format("game: date %s - score 1 - SETTING_1", LocalDate.now()));
	}

	private void goOutOfMapBoundsWhileAvoidingApples(Point start, Point apple, Dimension mapDim) {
		int difx = apple.x - start.x;
		if (apple.y == start.y) {
			if (difx < 0) {
				IntStream.range(0, mapDim.width - start.x)
						.forEach(i -> window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_RIGHT));
			} else {
				IntStream.range(0, start.x + 1)
						.forEach(i -> window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_LEFT));
			}
		} else {
			IntStream.range(0, start.x + 1)
					.forEach(i -> window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_LEFT));
		}
	}

	private Point moveOneCellWhileAvoidingApple(Point start, Point apple, Dimension dim) {
		int distx = apple.x - start.x;
		int disty = apple.y - start.y;
		int mvx = 0;
		int mvy = 0;
		if (start.x == 0) {
			if (distx == 1 && disty == 0 && start.y != 0) {
				window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_UP);
				mvy--;
			} else if (distx == 1 && disty == 0 && start.y != dim.height - 1) {
				window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_DOWN);
				mvy++;
			} else {
				window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_RIGHT);
				mvx++;
			}
		} else if (start.x == dim.width - 1) {
			if (distx == -1 && disty == 0 && start.y != 0) {
				window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_UP);
				mvy--;
			} else if (distx == -1 && disty == 0 && start.y != dim.height - 1) {
				window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_DOWN);
				mvy++;
			} else {
				window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_LEFT);
				mvx--;
			}
		} else if (start.y == 0) {
			if (disty == 1 && distx == 0 && start.x != 0) {
				window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_LEFT);
				mvx--;
			} else if (disty == 1 && distx == 0 && start.x != dim.width - 1) {
				window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_RIGHT);
				mvx++;
			} else {
				window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_DOWN);
				mvy++;
			}
		} else {
			if (disty == 1 && distx == 0 && start.x != 0) {
				window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_LEFT);
				mvx--;
			} else if (disty == 1 && distx == 0 && start.x != dim.width - 1) {
				window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_RIGHT);
				mvx++;
			} else {
				window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_UP);
				mvy--;
			}
		}
		return new Point(start.x + mvx, start.y + mvy);
	}

	private boolean checkRectInImgIsOfColor(BufferedImage image, int posX, int posY, int width, int height,
			Color checkColor) {
		return IntStream.range(posX, posX + width).allMatch(
				x -> IntStream.range(posY, posY + height).allMatch(y -> image.getRGB(x, y) == checkColor.getRGB()));
	}

	private Point findColor(int width, int height, BufferedImage img, Color color) {
		return IntStream.range(0, width).mapToObj(i -> IntStream.range(0, height).mapToObj(j -> new Point(i, j)))
				.flatMap(Function.identity())
				.filter(p -> checkRectInImgIsOfColor(img, p.x * (img.getWidth() / width),
						p.y * (img.getHeight() / height), img.getWidth() / width, img.getHeight() / height, color))
				.findFirst().orElse(null);
	}

	private void moveFromXToYAvoidingZ(Point pointX, Point pointY, Point pointZ) {
		int distx = pointY.x - pointX.x;
		int disty = pointY.y - pointX.y;

		if (pointZ != null && pointZ.y == pointX.y && pointZ.x >= Math.min(pointX.x, pointY.x)
				&& pointZ.x <= Math.max(pointX.x, pointY.x)) {
			if (pointX.y != 0) {
				window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_UP);
				disty++;
			} else {
				window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_DOWN);
				disty--;
			}
		}

		if (distx < 0) {
			IntStream.range(0, -distx).forEach(i -> window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_LEFT));
		} else {
			IntStream.range(0, distx).forEach(i -> window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_RIGHT));
		}

		int finalStep = 0;

		if (pointZ != null && pointZ.x == pointY.x && pointZ.y > Math.min(pointX.y, pointY.y)
				&& pointZ.y < Math.max(pointX.y, pointY.y)) {
			if (pointX.x != 0) {
				window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_LEFT);
				finalStep = 1;
			} else {
				window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_RIGHT);
				finalStep = -1;
			}
		}

		if (disty < 0) {
			IntStream.range(0, -disty).forEach(i -> window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_UP));
		} else {
			IntStream.range(0, disty).forEach(i -> window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_DOWN));
		}

		if (finalStep == 1) {
			window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_RIGHT);
		} else if (finalStep == -1) {
			window.panel("matchCanvas").pressAndReleaseKeys(KeyEvent.VK_LEFT);
		}
	}

}
