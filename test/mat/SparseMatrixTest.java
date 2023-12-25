package mat;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Before;

public class SparseMatrixTest extends AbstractMatrixTest{


  @Test
  public void testSparseMatrix(){
    SparseMatrix s = new SparseMatrix(2);
    s.set(0,0,1.1f);
    s.set(0,1,2.2f);
    s.set(1,0,3.3f);
    s.set(1,1,4.4f);
    assertEquals(1.1f,s.get(0,0),0);
    assertEquals(2.2f,s.get(0,1),0);
    assertEquals(3.3f,s.get(1,0),0);
    assertEquals(4.4f,s.get(1,1),0);
  }

  @Test
  public void testSparseMatrix2WithGivenValues(){
    SparseMatrix s = new SparseMatrix(3);
    s.set(0,0,2.5f);
    s.set(0,1,8.0f);
    s.set(0,2,-3.2f);
    s.set(1,0,2.0f);
    s.set(1,1,-1.23f);
    s.set(1,2,5.0f);
    s.set(2,0,1.0f);
    s.set(2,1,-1.0f);
    s.set(2,2,2.0f);
    assertEquals(2.5f,s.get(0,0),0);
    assertEquals(8.0f,s.get(0,1),0);
    assertEquals(-3.2f,s.get(0,2),0);
    assertEquals(2.0f,s.get(1,0),0);
    assertEquals(-1.23f,s.get(1,1),0);
    assertEquals(5.0f,s.get(1,2),0);
    assertEquals(1.0f,s.get(2,0),0);
    assertEquals(-1.0f,s.get(2,1),0);
    assertEquals(2.0f,s.get(2,2),0);
  }

  @Test
  public void testSparseMatrixGet(){
    SparseMatrix s = new SparseMatrix(3);
    s.set(0,0,2.5f);
    s.set(0,1,8.0f);
    s.set(0,2,-3.2f);
    s.set(1,0,2.0f);
    s.set(1,1,-1.23f);
    s.set(1,2,5.0f);
    s.set(2,0,1.0f);
    s.set(2,1,-1.0f);
    s.set(2,2,2.0f);
    assertEquals(-3.2f,s.get(0,2),0);
    assertEquals(1.0f,s.get(2,0), 0);
  }

  @Test
  public void testSparseMatrixUpdate(){
    SparseMatrix s = new SparseMatrix(4);
    s.set(0,1,8f);
    s.set(0,3,1f);
    s.set(1,2,5f);
    s.set(2,0,1f);
    s.set(2,1,-1f);
    s.set(2,2,2f);
    s.set(3,0,1f);
    s.set(3,2,1f);
    assertEquals(1.0f,s.get(3,2), 0);
  }

  @Test
  public void testSparseMatrixSetIdentity(){
    SparseMatrix s = new SparseMatrix(4);
    s.setIdentity();
    assertEquals(0,s.get(0,1),0);
    assertEquals(0,s.get(0,3),0);
    assertEquals(0,s.get(3,1),0);
    assertEquals(0,s.get(3,2),0);
    assertEquals(1,s.get(0,0),0);
    assertEquals(1,s.get(1,1),0);
    assertEquals(1,s.get(2,2),0);
    assertEquals(1,s.get(3,3),0);
  }

  @Test
  public void testLargeIdentities() {
    int dim = 2000;
    SquareMatrix one = new SparseMatrix(dim);
    SquareMatrix two = new SparseMatrix(dim);
    one.setIdentity();
    two.setIdentity();
    SquareMatrix add = one.add(two);
    SquareMatrix premul = one.premul(two);
    SquareMatrix postmul = one.postmul(two);

    for (int i=0;i<dim;i+=1) {
      for (int j=0;j<dim;j+=1) {
        if (i==j) {
          assertEquals(2,add.get(i,j),0.001);
          assertEquals(1,premul.get(i,j),0.001);
          assertEquals(1,postmul.get(i,j),0.001);
        }
        else {
          assertEquals(0,add.get(i,j),0.001);
          assertEquals(0,premul.get(i,j),0.001);
          assertEquals(0,postmul.get(i,j),0.001);
        }
      }
    }
  }

  @Test
  public void testLargeIdentitiesMix() {
    int dim = 2000;
    SquareMatrix one = new SparseMatrix(dim);
    SquareMatrix two = new ArrayMatrix(dim);
    one.setIdentity();
    two.setIdentity();
    SquareMatrix add = one.add(two);
    SquareMatrix premul = one.premul(two);
    SquareMatrix postmul = one.postmul(two);

    for (int i=0;i<dim;i+=1) {
      for (int j=0;j<dim;j+=1) {
        if (i==j) {
          assertEquals(2,add.get(i,j),0.001);
          assertEquals(1,premul.get(i,j),0.001);
          assertEquals(1,postmul.get(i,j),0.001);
        }
        else {
          assertEquals(0,add.get(i,j),0.001);
          assertEquals(0,premul.get(i,j),0.001);
          assertEquals(0,postmul.get(i,j),0.001);
        }
      }
    }
  }

  private SparseMatrix matrixA;
  private SparseMatrix matrixB;

  @Before
  public void setUp() {
    matrixA = new SparseMatrix(2);
    matrixB = new SparseMatrix(2);
    matrixA.set(0, 0, 1.0f);
    matrixA.set(0, 1, 2.0f);
    matrixA.set(1, 0, 3.0f);
    matrixA.set(1, 1, 4.0f);

    matrixB.set(0, 0, 1.0f);
    matrixB.set(0, 1, 1.0f);
    matrixB.set(1, 0, 1.0f);
    matrixB.set(1, 1, 1.0f);
  }

  @Test
  public void testSetIdentity() {
    matrixA.setIdentity();
    assertEquals(1, matrixA.get(0,0), 0);
    assertEquals(0, matrixA.get(0,1), 0);
  }

  @Test
  public void testAdd() {
    SquareMatrix result = matrixA.add(matrixB);
    assertEquals(2.0f, result.get(0, 0), 0.001);
    assertEquals(3.0f, result.get(0, 1), 0.001);
    assertEquals(4.0f, result.get(1, 0), 0.001);
    assertEquals(5.0f, result.get(1, 1), 0.001);
  }

  @Test
  public void testPremul() {
    SquareMatrix result = matrixA.premul(matrixB);
    assertEquals(4.0f, result.get(0, 0), 0.001);
    assertEquals(6.0f, result.get(0, 1), 0.001);
    assertEquals(4.0f, result.get(1, 0), 0.001);
    assertEquals(6.0f, result.get(1, 1), 0.001);
  }

  @Test
  public void testPostmul() {
    SquareMatrix result = matrixA.postmul(matrixB);
    assertEquals(3.0f, result.get(0, 0), 0.001);
    assertEquals(3.0f, result.get(0, 1), 0.001);
    assertEquals(7.0f, result.get(1, 0), 0.001);
    assertEquals(7.0f, result.get(1, 1), 0.001);
  }

  @Test
  public void testPostmulMix() {
    ArrayMatrix arrayMatrixB = new ArrayMatrix(2);
    arrayMatrixB.set(0,0,5.0f);
    arrayMatrixB.set(0,1,6.0f);
    arrayMatrixB.set(1,0,7.0f);
    arrayMatrixB.set(1,1,8.0f);
    SquareMatrix result = matrixA.postmul(arrayMatrixB);
    assertEquals(19.0f, result.get(0, 0), 0.001);
    assertEquals(22.0f, result.get(0, 1), 0.001);
    assertEquals(43.0f, result.get(1, 0), 0.001);
    assertEquals(50.0f, result.get(1, 1), 0.001);
  }

  @Test
  public void testPremulMix() {
    ArrayMatrix arrayMatrixB = new ArrayMatrix(2);
    arrayMatrixB.set(0,0,5.0f);
    arrayMatrixB.set(0,1,6.0f);
    arrayMatrixB.set(1,0,7.0f);
    arrayMatrixB.set(1,1,8.0f);
    SquareMatrix result = matrixA.premul(arrayMatrixB);
    assertEquals(23.0f, result.get(0, 0), 0.001);
    assertEquals(34.0f, result.get(0, 1), 0.001);
    assertEquals(31.0f, result.get(1, 0), 0.001);
    assertEquals(46.0f, result.get(1, 1), 0.001);
  }

  @Test
  public void testPremulMix2() {
    // { {5, 0}, {7,0} } X { {1, 2}, {3, 4} }
    ArrayMatrix arrayMatrixB = new ArrayMatrix(2);
    arrayMatrixB.set(0,0,5.0f);
    arrayMatrixB.set(0,1,0.0f);
    arrayMatrixB.set(1,0,7.0f);
    arrayMatrixB.set(1,1,0.0f);
    SquareMatrix result = matrixA.premul(arrayMatrixB);
    assertEquals(5.0f, result.get(0, 0), 0.001);
    assertEquals(10.0f, result.get(0, 1), 0.001);
    assertEquals(7.0f, result.get(1, 0), 0.001);
    assertEquals(14.0f, result.get(1, 1), 0.001);
  }

  @Test
  public void testPostmulMix2() {
    //  { {1, 2}, {3, 4} }  { {5, 0}, {7,0} }
    ArrayMatrix arrayMatrixB = new ArrayMatrix(2);
    arrayMatrixB.set(0,0,5.0f);
    arrayMatrixB.set(0,1,0.0f);
    arrayMatrixB.set(1,0,7.0f);
    arrayMatrixB.set(1,1,0.0f);
    SquareMatrix result = matrixA.postmul(arrayMatrixB);

    assertEquals(19.0f, result.get(0, 0), 0.001);
    assertEquals(0.0f, result.get(0, 1), 0.001);
    assertEquals(43.0f, result.get(1, 0), 0.001);
    assertEquals(0.0f, result.get(1, 1), 0.001);
  }

  @Test
  public void testPremulMix3() {
    // { {5, 0}, {7,0} } X { {0, 2}, {0, 4} }
    matrixA = new SparseMatrix(2);
    matrixA.set(0, 0, 0.0f);
    matrixA.set(0, 1, 2.0f);
    matrixA.set(1, 0, 0.0f);
    matrixA.set(1, 1, 4.0f);
    ArrayMatrix arrayMatrixB = new ArrayMatrix(2);
    arrayMatrixB.set(0,0,5.0f);
    arrayMatrixB.set(0,1,0.0f);
    arrayMatrixB.set(1,0,7.0f);
    arrayMatrixB.set(1,1,0.0f);

    SquareMatrix result = matrixA.premul(arrayMatrixB);
    assertEquals(0.0f, result.get(0, 0), 0.001);
    assertEquals(10.0f, result.get(0, 1), 0.001);
    assertEquals(0.0f, result.get(1, 0), 0.001);
    assertEquals(14.0f, result.get(1, 1), 0.001);
  }

  @Test
  public void testPostmulMix3() {
    //  { {0, 2}, {0, 4} }   { {5, 0}, {7,0} }
    matrixA = new SparseMatrix(2);
    matrixA.set(0, 0, 0.0f);
    matrixA.set(0, 1, 2.0f);
    matrixA.set(1, 0, 0.0f);
    matrixA.set(1, 1, 4.0f);
    ArrayMatrix arrayMatrixB = new ArrayMatrix(2);
    arrayMatrixB.set(0,0,5.0f);
    arrayMatrixB.set(0,1,0.0f);
    arrayMatrixB.set(1,0,7.0f);
    arrayMatrixB.set(1,1,0.0f);
    SquareMatrix result = matrixA.postmul(arrayMatrixB);

    assertEquals(14.0f, result.get(0, 0), 0.001);
    assertEquals(0.0f, result.get(0, 1), 0.001);
    assertEquals(28.0f, result.get(1, 0), 0.001);
    assertEquals(0.0f, result.get(1, 1), 0.001);
  }


  @Test(timeout = 5)
  public void testMixedPostMul() {
    SquareMatrix one = new ArrayMatrix(4);
    SquareMatrix two = new SparseMatrix(4);
    float[][] oneValues = {
        {0, 2, 0, 3},
        {1, 0, 2, 4},
        {0, 1, 0, 5},
        {1, 2, 3, 4}
    };

    float[][] twoValues = {
        {1, 0, 0, 0},
        {0, 0, -1, 0},
        {-1, -3, 1, 0},
        {0, 0, 1, 0}
    };

    float[][] answerOne = {
        {0, 0, 1, 0},
        {-1, -6, 6, 0},
        {0, 0, 4, 0},
        {-2, -9, 5, 0}
    };

    float[][] answerTwo = {
        {0, 2, 0, 3},
        {0, -1, 0, -5},
        {-3, -1, -6, -10},
        {0, 1, 0, 5}

    };

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        one.set(i, j, oneValues[i][j]);
        two.set(i, j, twoValues[i][j]);
      }
    }

    SquareMatrix resultOne = one.postmul(two);
    SquareMatrix resultTwo = two.postmul(one);

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        assertEquals(answerOne[i][j], resultOne.get(i, j), 0.001);
        assertEquals(answerTwo[i][j], resultTwo.get(i, j), 0.001);
      }
    }
  }

  @Test(timeout = 5)
  public void testMixedPreMul() {
    SquareMatrix one = new ArrayMatrix(4);
    SquareMatrix two = new SparseMatrix(4);
    float[][] oneValues = {
        {0, 2, 0, 3},
        {1, 0, 2, 4},
        {0, 1, 0, 5},
        {1, 2, 3, 4}
    };

    float[][] twoValues = {
        {1, 0, 0, 0},
        {0, 0, -1, 0},
        {-1, -3, 1, 0},
        {0, 0, 1, 0}
    };

    float[][] answerOne = {
        {0, 2, 0, 3},
        {0, -1, 0, -5},
        {-3, -1, -6, -10},
        {0, 1, 0, 5}
    };

    float[][] answerTwo = {
        {0, 0, 1, 0},
        {-1, -6, 6, 0},
        {0, 0, 4, 0},
        {-2, -9, 5, 0}
    };

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        one.set(i, j, oneValues[i][j]);
        two.set(i, j, twoValues[i][j]);
      }
    }

    SquareMatrix resultOne = one.premul(two);
    SquareMatrix resultTwo = two.premul(one);

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        assertEquals(answerOne[i][j], resultOne.get(i, j), 0.001);
        assertEquals(answerTwo[i][j], resultTwo.get(i, j), 0.001);
      }
    }
  }

  @Override
  public SquareMatrix createMatrix(int size) {
    return new SparseMatrix(size);
  }
}