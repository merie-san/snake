package com.minigames.snake;


import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.minigames.snake.presenter.SnakeLobbyPresenter;
import com.minigames.snake.presenter.SnakeMatchPresenter;
import com.minigames.snake.view.SnakeWindowView;

@RunWith(GUITestRunner.class)
@SuppressWarnings("java:S2699")
public class SnakeAppTest extends AssertJSwingJUnitTestCase {

	@Override
	protected void onSetUp() throws Exception {
		
	}

	
	@Test
	public void test() {
	}
}
