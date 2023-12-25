package mat;

public abstract class AbstractMatrix implements SquareMatrix {

  protected final int size;

  protected AbstractMatrix(int size) throws IllegalArgumentException {
    if (size < 0) {
      throw new IllegalArgumentException("The size of a matrix cannot be non-positive");
    }
    this.size = size;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public abstract void setIdentity();

  @Override
  public abstract void set(int i, int j, float value) throws IllegalArgumentException;

  @Override
  public abstract float get(int i, int j) throws IllegalArgumentException;

  @Override
  public abstract SquareMatrix add(SquareMatrix other) throws IllegalArgumentException;

  @Override
  public abstract SquareMatrix premul(SquareMatrix other) throws IllegalArgumentException;

  @Override
  public abstract SquareMatrix postmul(SquareMatrix other) throws IllegalArgumentException;

  protected void checkRowAndColumnIndices(int i,int j) throws IllegalArgumentException {
    if ((i < 0) || (i >= size)) {
      throw new IllegalArgumentException(
          "Row number in set cannot be beyond the bounds of the matrix");
    }

    if ((j < 0) || (j >= size)) {
      throw new IllegalArgumentException(
          "Column number in set cannot be beyond the bounds of the matrix");
    }
  }

  protected void checkValidityForAddOrMultiply(SquareMatrix other) throws IllegalArgumentException {
    if (size != other.size()) {
      throw new IllegalArgumentException("The dimensions of the two matrices do not match "
          + "and therefore cannot be added or multiplied together");
    }
  }
}
