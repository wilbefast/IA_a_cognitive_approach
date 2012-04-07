/**
 * 
 */
package ac.memory.semantic;

import ac.memory.persistence.neo4j.AttributeNode;
import ac.memory.persistence.neo4j.AttributeNodeRepository;
import ac.memory.persistence.neo4j.Neo4jService;
import ac.memory.persistence.neo4j.NodeException;
import ac.memory.persistence.neo4j.NodeRepositoryException;
import ac.memory.persistence.neo4j.ObjectNode;
import ac.memory.persistence.neo4j.ObjectNodeRepository;
import ac.memory.semantic.lattice.LatticeContext;
import ac.memory.semantic.lattice.Neo4jLatticeContext;
import ac.shared.CompleteBoardState;
import ac.shared.RelevantPartialBoardState;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 5 avr. 2012
 * @version 0.1
 */
public class Neo4jSemanticMemory implements SemanticMemory
{

  private Neo4jLatticeContext context;
  private ObjectNodeRepository obj_repo;
  private AttributeNodeRepository attr_repo;

  /**
   * Default constructor
   */
  public Neo4jSemanticMemory()
  {
    this.context = new Neo4jLatticeContext();
    this.obj_repo = new ObjectNodeRepository(Neo4jService.getInstance(),
        Neo4jService.getObjIndex());
    this.attr_repo = new AttributeNodeRepository(Neo4jService.getInstance(),
        Neo4jService.getAttrIndex());
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.semantic.SemanticMemory#getLatticeContext() */
  @Override
  public LatticeContext getLatticeContext()
  {
    return context;
  }

  /* (non-Javadoc)
   * 
   * @see
   * ac.memory.semantic.SemanticMemory#getMark(ac.shared.CompleteBoardState) */
  @Override
  public double getMark(CompleteBoardState cbs)
  {
    try
      {
        ObjectNode obj = obj_repo.getNodeById(cbs.getId());
        return obj.getMark();

      }
    catch (NodeRepositoryException | NodeException e)
      {
        return (double) 0.5;
      }
  }

  /* (non-Javadoc)
   * 
   * @see
   * ac.memory.semantic.SemanticMemory#getMark(ac.shared.CompleteBoardState) */
  @Override
  public double getMark(RelevantPartialBoardState cbs)
  {
    try
      {
        AttributeNode attr = attr_repo.getNodeById(cbs.getId());
        return attr.getMark();

      }
    catch (NodeRepositoryException | NodeException e)
      {
        return (double) 0.5;
      }
  }
}
