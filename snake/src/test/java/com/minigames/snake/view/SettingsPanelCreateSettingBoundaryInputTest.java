package com.minigames.snake.view;

import java.util.Collection;
import java.util.List;

import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
@SuppressWarnings("java:S2699")
public class SettingsPanelCreateSettingBoundaryInputTest {

	private FrameFixture window;
	private SnakeWindowView snakeView;
	private String componentName;
	private String enteredValue;
	private String expectedMessage;

	public SettingsPanelCreateSettingBoundaryInputTest(String componentName, String enteredValue,
			String expectedMessage) {
		this.componentName = componentName;
		this.enteredValue = enteredValue;
		this.expectedMessage = expectedMessage;
	}

	@BeforeClass
	public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}

	@Before
	public void setup() {
		snakeView = GuiActionRunner.execute(() -> new SnakeWindowView(null,null));
		window = new FrameFixture(snakeView);
		window.show();
	}

	@After
	public void tearDown() {
		window.cleanUp();
	}

	@Parameters(name = "{index}: {0} with {1} should show message \"{2}\"")
	public static Collection<Object[]> data() {
		return List.of(new Object[][] {
				{ "heightTextBox", "4", "the width and height of the map must be contained in [5, 30]" },
				{ "heightTextBox", "31", "the width and height of the map must be contained in [5, 30]" },
				{ "widthTextBox", "4", "the width and height of the map must be contained in [5, 30]" },
				{ "widthTextBox", "31", "the width and height of the map must be contained in [5, 30]" },
				{ "obstaclesTextBox", "-1",
						"the number of obstacles on the map must be contained in [0, width*height/2]" },
				{ "obstaclesTextBox", "51",
						"the number of obstacles on the map must be contained in [0, width*height/2]" },
				{ "nameTextBox", "12345678910", "the lenght of the setting's name must be contained in [0, 20]" }, });
	}

	@Test
	public void testSettingsPanelCreateSettingBoundaryvalues() {
		GuiActionRunner.execute(() -> {
			snakeView.show("Settings panel");
		});
		window.textBox(componentName).enterText(enteredValue);
		window.button("submitButton").click();
		window.dialog().label("OptionPane.label").requireText(expectedMessage);
	}

}
