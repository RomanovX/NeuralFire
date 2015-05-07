package neuralfire.core;

public interface IWalkAlgorithm {

	public Constants.Dir performWalk(Grid grid, int row, int col);
}
