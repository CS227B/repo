
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.ggp.base.apps.player.detail.DetailPanel;
import org.ggp.base.apps.player.detail.SimpleDetailPanel;
import org.ggp.base.player.gamer.event.GamerSelectedMoveEvent;
import org.ggp.base.player.gamer.exception.GameAnalysisException;
import org.ggp.base.player.gamer.statemachine.StateMachineGamer;
import org.ggp.base.util.game.Game;
import org.ggp.base.util.statemachine.MachineState;
import org.ggp.base.util.statemachine.Move;
import org.ggp.base.util.statemachine.Role;
import org.ggp.base.util.statemachine.StateMachine;
import org.ggp.base.util.statemachine.cache.CachedStateMachine;
import org.ggp.base.util.statemachine.exceptions.GoalDefinitionException;
import org.ggp.base.util.statemachine.exceptions.MoveDefinitionException;
import org.ggp.base.util.statemachine.exceptions.TransitionDefinitionException;
import org.ggp.base.util.statemachine.implementation.prover.ProverStateMachine;

public final class NobodyGamerSingle extends StateMachineGamer {

	public Move stateMachineSelectMove(long timeout) throws MoveDefinitionException, TransitionDefinitionException, GoalDefinitionException {
		List<Move> moves = 
				getStateMachine().getLegalMoves(
						getCurrentState(), getRole());
		int score = 0;
		Move action = moves.get(0);
		for(int i = 0; i < moves.size(); i++) {
			List<Move> thisTurn = new ArrayList<Move>();
			thisTurn.add(moves.get(i));
			MachineState nextState = getStateMachine().getNextState(getCurrentState(), thisTurn);
			int result = stateUtility(nextState, getRole());
			if (result > score) {
				score = result;
				action = moves.get(i);
				if (score == 100) break;
			}
		}
		return action;
	}
	
	private int stateUtility(MachineState state, Role role) throws GoalDefinitionException, MoveDefinitionException, TransitionDefinitionException{
		if(getStateMachine().isTerminal(state)) return getStateMachine().getGoal(state, role);
		List<Move> nextPossibles = getStateMachine().getLegalMoves(state, role);
		int score = 0;
		for (int i = 0; i < nextPossibles.size(); i++){
			List<Move> thisTurn = new ArrayList<Move>();
			thisTurn.add(nextPossibles.get(i));
			int result = stateUtility(getStateMachine().getNextState(state, thisTurn), role);
			if(result > score) score = result;
			if (score == 100) break;
		}
		return score;
	}

	public String getName() {
		return "NobodySingle";
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
