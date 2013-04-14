
import java.util.List;
import java.util.Random;

import org.ggp.base.apps.player.detail.DetailPanel;
import org.ggp.base.apps.player.detail.SimpleDetailPanel;
import org.ggp.base.player.gamer.event.GamerSelectedMoveEvent;
import org.ggp.base.player.gamer.exception.GameAnalysisException;
import org.ggp.base.player.gamer.statemachine.StateMachineGamer;
import org.ggp.base.util.game.Game;
import org.ggp.base.util.statemachine.Move;
import org.ggp.base.util.statemachine.StateMachine;
import org.ggp.base.util.statemachine.cache.CachedStateMachine;
import org.ggp.base.util.statemachine.exceptions.GoalDefinitionException;
import org.ggp.base.util.statemachine.exceptions.MoveDefinitionException;
import org.ggp.base.util.statemachine.exceptions.TransitionDefinitionException;
import org.ggp.base.util.statemachine.implementation.prover.ProverStateMachine;

public final class NobodyGamerPlayer extends StateMachineGamer {

	public Move stateMachineSelectMove(long timeout) throws MoveDefinitionException {
		List <Move> moves = 
			getStateMachine().getLegalMoves(
					getCurrentState(), getRole());
		Move selection = 
				moves.get(new Random().nextInt(moves.size()));
		return selection;
	}

	public String getName() {
		return "NobodyRandom";
	}
	
	public DetailPanel getDetailPanel(){
		return new SimpleDetailPanel();
	}
	
	public StateMachine getInitialStateMachine() {
		return new ProverStateMachine();
	}
	
	public void analyze(Game game, long startclock){
		//Empty
	}
	
	public void stateMachineAbort(){
		//Empty
	}
	
	public void stateMachineStop() {
		//Empty
	}
	
	public void stateMachineMetaGame(long startclock) {
		//Empty
	}
}
