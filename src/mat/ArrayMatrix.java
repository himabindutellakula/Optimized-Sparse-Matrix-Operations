package mat;

/**
 * This class implements a 2D matrix of numbers using a 2D array. This implementation is efficient
 * if most of the entries of the matrix are not zero. But this wastes a lot of space and computing
 * time if most of its entries are zero.
 */
public class ArrayMatrix extends AbstractMatrix {

  private float[][] mat;

  /**
   * Constructs a new matrix of the given dimensions. All entries of this matrix are by default, 0
   *
   * @param size the number of rows and columns in this matrix
   * @throws IllegalArgumentException if the size is a non-positive number
   */
  public ArrayMatrix(int size) throws IllegalArgumentException {
    super(size);
    mat = new float[size][size];
    for (int i = 0; i < mat.length; i += 1) {
      for (int j = 0; j < mat[i].length; j += 1) {
        mat[i][j] = 0.0f;
      }
    }
  }

  /**
   * Constructs a new matrix of the given values stored in a 2d array
   *
   * @param mat the 2d array that contains values that are to be copied into Array Matrix
   * @throws IllegalArgumentException when all rows do not have equal number of columns
   */
  private ArrayMatrix(float[][] mat) throws IllegalArgumentException {
    super(mat.length);
    int numRows = mat.length;
    int numCols = mat[0].length;
    for (int i = 0; i < numRows; i += 1) {
      if (mat[i].length != numCols) {
        throw new IllegalArgumentException("Unequal number of columns");
      }
    }
    this.mat = new float[numRows][numCols];
    for (int i = 0; i < numRows; i += 1) {
      for (int j = 0; j < numCols; j += 1) {
        this.mat[i][j] = mat[i][j];
      }
    }
  }

  @Override
  public void setIdentity() {
    for (int i = 0; i < mat.length; i += 1) {
      for (int j = 0; j < mat.length; j += 1) {
        if (i == j) {
          mat[i][j] = 1;
        } else {
          mat[i][j] = 0;
        }
      }
    }
  }

  @Override
  public void set(int i, int j, float value) throws IllegalArgumentException {
    checkRowAndColumnIndices(i, j);
    mat[i][j] = value;
  }

  @Override
  public float get(int i, int j) throws IllegalArgumentException {
    checkRowAndColumnIndices(i, j);
    return mat[i][j];
  }

  @Override
  public SquareMatrix add(SquareMatrix other) throws IllegalArgumentException {
    checkValidityForAddOrMultiply(other);
    float[][] result = new float[this.size()][this.size()];
    for (int i = 0; i < this.size(); i += 1) {
      for (int j = 0; j < size(); j += 1) {
        result[i][j] = this.mat[i][j] + other.get(i, j);
      }
    }
    return new ArrayMatrix(result);
  }

  @Override
  public SquareMatrix postmul(SquareMatrix other) throws IllegalArgumentException {
    checkValidityForAddOrMultiply(other);
    if (other instanceof ArrayMatrix) {
      return multiply((ArrayMatrix) other, "postMul");
    } else {
      return other.premul(this);
    }
  }

  @Override
  public SquareMatrix premul(SquareMatrix other) throws IllegalArgumentException {
    checkValidityForAddOrMultiply(other);
    if (other instanceof ArrayMatrix) {
      return multiply((ArrayMatrix) other, "preMul");
    } else {
      return other.postmul(this);
    }
  }

  /**
   * Method to perform the multiplication of the two Array Matrices based on multiplication type
   *
   * @param other              the array matrix that has to be multiplied with the current one
   * @param multiplicationType represents whether the multiplication is of type premul or postmul
   * @return SquareMatrix which is the product of the current and given matrix
   */
  private SquareMatrix multiply(ArrayMatrix other, String multiplicationType) {
    float[][] result = new float[this.size()][other.size()];
    for (int i = 0; i < other.size(); i += 1) {
      for (int j = 0; j < this.size(); j += 1) {
        result[i][j] = 0;
        for (int k = 0; k < this.size(); k += 1) {
          if (multiplicationType.equals("preMul")) {
            result[i][j] += other.get(i, k) * this.mat[k][j];
          }
          if (multiplicationType.equals("postMul")) {
            result[i][j] += this.mat[i][k] * other.get(k, j);
          }
        }
      }
    }
    return new ArrayMatrix(result);
  }

  /**
   * To get the value of the Array Matrix mat
   *
   * @return the current mat which is Array Matrix
   */
  public float[][] getMat() {
    return mat;
  }

}
