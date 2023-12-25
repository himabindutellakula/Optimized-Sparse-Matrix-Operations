package mat;

public class SparseMatrix extends AbstractMatrix {

  private final DoublyLinkedList[] listOfRowAndColumnDLL;

  public SparseMatrix(int size) throws IllegalArgumentException {
    super(size);
    listOfRowAndColumnDLL = new DoublyLinkedList[size * 2];
    for (int i = 0; i < size * 2; i++) {
      listOfRowAndColumnDLL[i] = new DoublyLinkedList();
    }
  }

  private int getColumnLinkedListIndex(int matrixColumn) {
    return size + matrixColumn;
  }

  @Override
  public void setIdentity() {
    for (int i = 0; i < size; i++) {
      listOfRowAndColumnDLL[i].clear();
      listOfRowAndColumnDLL[getColumnLinkedListIndex(i)].clear();
      DoublyLinkedList thisRow = listOfRowAndColumnDLL[i];
      DoublyLinkedList thisCol = listOfRowAndColumnDLL[getColumnLinkedListIndex(i)];
      Node newNode = new Node(i, i, 1.0f);
      thisRow.setHead(newNode);
      thisCol.setHead(newNode);
    }
  }

  @Override
  public void set(int i, int j, float value) throws IllegalArgumentException {
    checkRowAndColumnIndices(i,j);
    listOfRowAndColumnDLL[i].insertAtRowIndex(i, j, value);
    listOfRowAndColumnDLL[getColumnLinkedListIndex(j)].insertAtColumnIndex(i, j, value);
  }

  @Override
  public float get(int i, int j) throws IllegalArgumentException {
    checkRowAndColumnIndices(i,j);
    return listOfRowAndColumnDLL[i].get(j);
  }

  public SquareMatrix add(SquareMatrix other) throws IllegalArgumentException {
    checkValidityForAddOrMultiply(other);

    if (other instanceof SparseMatrix) {
      return addSparseWithSparse((SparseMatrix) other);
    } else {
      return ((ArrayMatrix) other).add(this);
    }
  }

  @Override
  public SquareMatrix premul(SquareMatrix other) throws IllegalArgumentException {
    checkValidityForAddOrMultiply(other);

    if (other instanceof SparseMatrix) {
      return premulSparseWithSparse((SparseMatrix) other);
    } else {
      return premulSparseWithArray((ArrayMatrix) other);
    }
  }

  @Override
  public SquareMatrix postmul(SquareMatrix other) throws IllegalArgumentException {
    checkValidityForAddOrMultiply(other);

    if (other instanceof SparseMatrix) {
      return postmulSparseWithSparse((SparseMatrix) other);
    } else {
      return postmulSparseWithArray((ArrayMatrix) other);
    }
  }

  private SparseMatrix addSparseWithSparse(SparseMatrix other) {
      SparseMatrix result = new SparseMatrix(size);
      for (int i = 0; i < size; i++) {
        Node node1 = listOfRowAndColumnDLL[i].getHead();
        Node node2 = other.listOfRowAndColumnDLL[i].getHead();

        while (node1 != null || node2 != null) {
          if (node1 != null && node2 != null) {
            if (node1.col == node2.col) {
              float val = node1.data + node2.data;
              if (val != 0) {
                result.set(i, node1.col, val);
              }
              node1 = node1.nextInRow;
              node2 = node2.nextInRow;
            } else if (node1.col > node2.col) {
              result.set(i, node2.col, node2.data);
              node2 = node2.nextInRow;
            } else {
              result.set(i, node1.col, node1.data);
              node1 = node1.nextInRow;
            }
          } else if (node2 != null) {
            result.set(i, node2.col, node2.data);
            node2 = node2.nextInRow;
          } else {
            result.set(i, node1.col, node1.data);
            node1 = node1.nextInRow;
          }
        }
      }
      return result;
  }


  private SparseMatrix postmulSparseWithSparse(SparseMatrix other) {
    SparseMatrix result = new SparseMatrix(size);

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        float sum = 0.0f;

        DoublyLinkedList thisRow = listOfRowAndColumnDLL[i];
        DoublyLinkedList otherCol = other.listOfRowAndColumnDLL[getColumnLinkedListIndex(j)];

        Node thisNode = thisRow.getHead();
        Node otherNode = otherCol.getHead();

        while (thisNode != null && otherNode != null) {
          if (thisNode.col < otherNode.row) {
            thisNode = thisNode.nextInRow;
          } else if (thisNode.col > otherNode.row) {
            otherNode = otherNode.nextInColumn;
          } else {
            sum += thisNode.data * otherNode.data;
            thisNode = thisNode.nextInRow;
            otherNode = otherNode.nextInColumn;
          }
        }

        if (sum != 0.0f) {
          result.set(i, j, sum);
        }
      }
    }
    return result;
  }

  private SparseMatrix postmulSparseWithArray(ArrayMatrix other) {
    SparseMatrix result = new SparseMatrix(size);

    for (int i = 0; i < size; i++) {
      DoublyLinkedList rowList = listOfRowAndColumnDLL[i];
      Node rowNode = rowList.getHead();

      while (rowNode != null) {
        float currentValue = rowNode.data;
        int column = rowNode.col;

        for (int j = 0; j < size; j++) {
          float otherValue = other.get(column, j);
          if (otherValue != 0) {
            float temp = result.get(i, j);
            float product = currentValue * otherValue;

            float finalValue = product + temp;
            result.set(i, j, finalValue);
          }
        }
        rowNode = rowNode.nextInRow;
      }
    }
    return result;
  }

  private SparseMatrix premulSparseWithSparse(SparseMatrix other) {
    SparseMatrix result = new SparseMatrix(size);

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        float sum = 0.0f;
        DoublyLinkedList thisCol = listOfRowAndColumnDLL[getColumnLinkedListIndex(j)];
        DoublyLinkedList otherRow = other.listOfRowAndColumnDLL[i];

        Node thisNode = thisCol.getHead();
        Node otherNode = otherRow.getHead();

        while (thisNode != null && otherNode != null) {
          if (thisNode.row < otherNode.col) {
            thisNode = thisNode.nextInColumn;
          } else if (thisNode.row > otherNode.col) {
            otherNode = otherNode.nextInRow;
          } else {
            sum += thisNode.data * otherNode.data;
            thisNode = thisNode.nextInColumn;
            otherNode = otherNode.nextInRow;
          }
        }

        if (sum != 0.0f) {
          result.set(i, j, sum);
        }
      }
    }

    return result;
  }

  private SparseMatrix premulSparseWithArray(ArrayMatrix other) {
    SparseMatrix result = new SparseMatrix(size);

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        float sum = 0.0f;

        DoublyLinkedList colList = this.listOfRowAndColumnDLL[getColumnLinkedListIndex(j)];
        Node colNode = colList.getHead();

        while (colNode != null) {
          int newRow = colNode.row;
          float rowValue = other.get(i, newRow);
          float colValue = colNode.data;

          sum += colValue * rowValue;
          colNode = colNode.nextInColumn;
        }

        if (sum != 0.0f) {
          result.set(i, j, sum);
        }
      }
    }
    return result;
  }

}
