package mat;

public class Node {

  protected float data;
  protected int row;
  protected int col;
  protected Node nextInRow;
  protected Node previousInRow;
  protected Node nextInColumn;
  protected Node previousInColumn;

  public Node(int row, int column, float d) {
    this.row = row;
    this.col = column;
    data = d;
    nextInRow = null;
    previousInRow = null;
    nextInColumn = null;
    previousInColumn = null;
  }

}
