import com.andreasbur.actions.Action;
import com.andreasbur.actions.ActionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ActionHandlerTest {

	private ActionHandler actionHandler;

	@BeforeEach
	void setUp() {
		actionHandler = new ActionHandler();
	}

	@Test
	void testActionHandlerExecute() {
		TestAction testAction = new TestAction();
		assert testAction.isExecuted() == false;
		actionHandler.execute(testAction);
		assert testAction.isExecuted() == true;
	}

	@Test
	void testActionHandlerStates() {
		TestAction testAction = new TestAction();
		assert actionHandler.canUndo() == false;
		assert actionHandler.canRedo() == false;
		actionHandler.execute(testAction);
		assert actionHandler.canUndo() == true;
		assert actionHandler.canRedo() == false;
		actionHandler.undo();
		assert actionHandler.canUndo() == false;
		assert actionHandler.canRedo() == true;
	}

	@Test
	void testActionHandlerUndo() {
		TestAction testAction = new TestAction();
		assert testAction.isExecuted() == false;
		actionHandler.execute(testAction);
		actionHandler.undo();
		assert testAction.isExecuted() == false;
	}

	@Test
	void testActionHandlerMultipleUndo() {
		TestAction testAction1 = new TestAction();
		TestAction testAction2 = new TestAction();

		actionHandler.execute(testAction1);
		actionHandler.execute(testAction2);
		assert testAction1.isExecuted() == true;
		assert testAction2.isExecuted() == true;
		actionHandler.undo();
		assert testAction1.isExecuted() == true;
		assert testAction2.isExecuted() == false;
		actionHandler.undo();
		assert testAction1.isExecuted() == false;
		assert testAction2.isExecuted() == false;
	}

	@Test
	void testActionHandlerRedo() {
		TestAction testAction = new TestAction();
		assert testAction.isExecuted() == false;
		actionHandler.execute(testAction);
		actionHandler.undo();
		actionHandler.redo();
		assert testAction.isExecuted() == true;
	}

	@Test
	void testActionHandlerMultipleRedo() {
		TestAction testAction1 = new TestAction();
		TestAction testAction2 = new TestAction();

		actionHandler.execute(testAction1);
		actionHandler.execute(testAction2);
		actionHandler.undo();
		actionHandler.undo();
		actionHandler.redo();
		actionHandler.redo();

		assert testAction1.isExecuted() == true;
		assert testAction2.isExecuted() == true;
	}

	@Test
	void testResetRedoAfterExecute() {
		TestAction testAction1 = new TestAction();
		TestAction testAction2 = new TestAction();

		actionHandler.execute(testAction1);
		actionHandler.undo();
		actionHandler.execute(testAction2);
		assert actionHandler.canRedo() == false;
		actionHandler.redo();
		assert testAction1.isExecuted() == false;
	}

	public static class TestAction extends Action {

		private boolean executed = false;

		@Override
		protected void execute() {
			executed = true;
		}

		@Override
		protected void undo() {
			executed = false;
		}

		public boolean isExecuted() {
			return executed;
		}
	}
}
