package mat;

public class DoublyLinkedList {
  private Node head;
  public DoublyLinkedList() {
    this.head = null;
  }

  public Node getHead(){
    return this.head;
  }

  public void setHead(Node tempHead){
    this.head = tempHead;
  }

  public void insertAtRowIndex(int row, int column, float data) {
    if (head == null) {
      head = new Node(row, column, data);
      return;
    }

    if (column < head.col) {
      Node newNode = new Node(row, column, data);
      newNode.nextInRow = head;
      head.previousInRow = newNode;
      head = newNode;
      return;
    }

    Node current = head;
    while (current.nextInRow != null && current.nextInRow.col < column) {
      current = current.nextInRow;
    }

    if (current.col == column) {
      current.data = data;
    } else {
      Node newNode = new Node(row, column, data);
      newNode.nextInRow = current.nextInRow;
      newNode.previousInRow = current;
      if (current.nextInRow != null) {
        current.nextInRow.previousInRow = newNode;
      }
      current.nextInRow = newNode;
    }
  }

  public void insertAtColumnIndex(int row, int column, float data) {
    if (head == null) {
      head = new Node(row, column, data);
      return;
    }

    if (row < head.row) {
      Node newNode = new Node(row, column, data);
      newNode.nextInColumn = head;
      head.previousInColumn = newNode;
      head = newNode;
      return;
    }

    Node current = head;
    while (current.nextInColumn != null && current.nextInColumn.row < row) {
      current = current.nextInColumn;
    }

    if (current.row == row) {
      current.data = data;
    } else {
      Node newNode = new Node(row, column, data);
      newNode.nextInColumn = current.nextInColumn;
      newNode.previousInColumn = current;
      if (current.nextInColumn != null) {
        current.nextInColumn.previousInColumn = newNode;
      }
      current.nextInColumn = newNode;
    }
  }

  public float get(int column) {
    Node current = head;
    while (current != null) {
      if (current.col == column) {
        return current.data;
      }
      current = current.nextInRow;
    }
    return 0.0f;
  }

  public void clear() {
    head = null;
  }

}