package mat;

import java.util.function.Consumer;

abstract class AbstractNode<T> {
  //each type of node has a reference to the next and previous nodes in the list
  protected AbstractNode<T> nextInRow;
  protected AbstractNode<T> previousInRow;
  protected AbstractNode<T> nextInColumn;
  protected AbstractNode<T> previousInColumn;

  public AbstractNode() {
    this.nextInRow = null;
    this.previousInRow = null;
    this.nextInColumn = null;
    this.previousInColumn = null;
  }

  /*AbstractNode(AbstractNode<T> prev,AbstractNode<T> next) {
    this.nextInRow = next;
    this.nextInColumn = next;
    this.prev
    this.prev = prev;

    //set the references
    this.next.prev = this;
    this.prev.next = this;
  }*/

  /**
   * Return the first data item in the list that starts here.
   * @return by default, this method throws an exception. This implementation is good for the Sentinel
   * @throws IllegalStateException if the list is empty
   */
  T getFirstStartingHere() throws IllegalStateException {
    throw new IllegalStateException("No data here");
  }


  /**
   * Add a new node containing the object right after this node in the list
   * @param object the object to be added to this list
   */
  /*void addAfter(T object) {
    AbstractNode<T> newNode = new DataNode<T>(object,this,this.next);
  }*/

  /**
   * Remove this node from the list
   */
  /*void remove() {
    //perform a nodec-tomy!
    this.prev.next = this.next;
    this.next.prev = this.prev;
  }*/

  /**
   * Apply the given sequence of steps to the content in this node
   * @param processor the sequence of steps to be applied to the content in this node
   */
  abstract void process(Consumer<T> processor);
}
