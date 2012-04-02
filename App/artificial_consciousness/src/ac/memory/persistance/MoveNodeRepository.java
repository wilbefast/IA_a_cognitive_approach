/**
 * 
 */
package ac.memory.persistance;

import java.util.Date;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.helpers.collection.IterableWrapper;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 2 avr. 2012
 * @version 0.1
 */
public class MoveNodeRepository extends
    AbstractEpisodicNodeRepository<MoveNode>
{
  private static final Logger logger = Logger
      .getLogger(MoveNodeRepository.class);

  /**
   * Default constructor
   * 
   * @param graphDb
   *          the database
   */
  public MoveNodeRepository(GraphDatabaseService graphDb)
  {
    super(graphDb);
  }

  /**
   * @param game
   * @return the new MoveNode
   */
  public MoveNode addMove(GameNode game)
  {
    // to guard against duplications we use the lock grabbed on ref node
    // when
    // creating a relationship and are optimistic about person not existing
    if (logger.isDebugEnabled())
      logger.debug("Opening transaction for adding move to the game "
          + game.getDate());
    Transaction tx = graphDb.beginTx();
    try
      {

        if (logger.isDebugEnabled())
          logger.debug("Move node creation");
        Node newMoveNode = graphDb.createNode();

        long date = (new Date()).getTime();
        if (logger.isDebugEnabled())
          logger.debug("Move node creation at date = " + date);
        newMoveNode.setProperty(DATE_FIELD, date);

        if (logger.isDebugEnabled())
          logger.debug("Creating relationship to the root node");
        refNode.createRelationshipTo(newMoveNode, RelTypes.MOVE);

        Relationship last_rel = game.underlyingNode.getSingleRelationship(
            RelTypes.LAST_MOVE, Direction.BOTH);

        if (last_rel != null)
          {
            Node gameLastMove = last_rel.getEndNode();

            if (logger.isDebugEnabled())
              logger.debug("Removing LAST_MOVE relationship of the game");

            last_rel.delete();

            if (logger.isDebugEnabled())
              logger
                  .debug("Insering new move at the first place of the game move sequence");

            newMoveNode.createRelationshipTo(gameLastMove, RelTypes.PREV_MOVE);
          }
        else
          {
            logger
                .warn("The game had not LAST_MOVE, what is maybe due to a new empty game");
          }
        game.underlyingNode.createRelationshipTo(newMoveNode,
            RelTypes.LAST_MOVE);

        tx.success();
        if (logger.isDebugEnabled())
          logger.debug("New Move added successfuly");
        return new MoveNode(newMoveNode);
      }
    finally
      {
        if (logger.isDebugEnabled())
          logger.debug("Finish transaction");
        tx.finish();
      }
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.persistance.AbstractEpisodicNodeRepository#getLast() */
  @Override
  @Deprecated
  public MoveNode getLast() throws NodeRepositoryException
  {
    // TODO do the method (but I think that it is useless)
    return null;
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.persistance.AbstractEpisodicNodeRepository#getAllNodes() */
  @Override
  public Iterable<MoveNode> getAllNodes()
  {
    if (logger.isDebugEnabled())
      logger.debug("Getting all the games nodes");
    return new IterableWrapper<MoveNode, Relationship>(
        refNode.getRelationships(RelTypes.MOVE))
    {
      @Override
      protected MoveNode underlyingObjectToObject(Relationship moveRel)
      {
        return new MoveNode(moveRel.getEndNode());
      }
    };
  }

}