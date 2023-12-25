package mat;

import org.junit.Test;
public abstract class AbstractMatrixTest {
  private SquareMatrix matrixA;
  private SquareMatrix matrixB;

  abstract public SquareMatrix createMatrix(int size);

}
